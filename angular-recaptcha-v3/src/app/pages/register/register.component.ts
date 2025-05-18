import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs';
import { ApiResponse } from '../../model/api-response';
import { AccountService } from '../../services/account.service';
import { CommonModule } from '@angular/common';
import { ReCaptchaV3Service } from 'ng-recaptcha-2';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent implements OnInit {
  readonly formBuilder = inject(FormBuilder);
  readonly route = inject(ActivatedRoute);
  readonly router = inject(Router);
  readonly accountService = inject(AccountService);
  readonly recaptchaV3Service = inject(ReCaptchaV3Service);
  form!: FormGroup;

  loading = false;
  submitted = false;
  errorResponse: string;

  constructor() {
    // redirect to home if already registered
    if (this.accountService.isUserAuthenticated()) {
      this.router.navigate(['/home']);
    }
    this.errorResponse = '';
  }

  ngOnInit() {
    this.initForm();
  }

  initForm(): void {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      passwordConfirmation: [
        '',
        [Validators.required, Validators.minLength(6)],
      ],
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;

    // reset error message
    this.errorResponse = '';

    // check if form is valid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;

    this.recaptchaV3Service.execute('importantAction').subscribe((token) => {
      this.accountService
        .register(this.form.value, token)
        .pipe(first())
        .subscribe({
          next: (response) => {
            this.errorResponse = 'Registration successful';
            localStorage.setItem('user', JSON.stringify(response.data));
            this.router.navigate(['/home']);
          },
          error: (error: any) => {
            var response = <ApiResponse>error.error;
            this.errorResponse = response.message;
            this.loading = false;
          }
        });
    });
  }
}
