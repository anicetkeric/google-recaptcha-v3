import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../services/account.service';
import { tap, first } from 'rxjs/operators';
import { ReCaptchaV3Service } from 'ng-recaptcha';
import { ApiResponse } from '../model/api-response';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form!: UntypedFormGroup;
  loading = false;
  submitted = false;
  errorResponse: string;
  
  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private recaptchaV3Service: ReCaptchaV3Service
) {
    // redirect to home if already registered
    if (this.accountService.userValue) {
        this.router.navigate(['/home']);
    } 
    this.errorResponse = "";
}

ngOnInit() {
  this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      passwordConfirmation: ['', [Validators.required, Validators.minLength(6)]]
  });
}

    // convenience getter for easy access to form fields
    get f() { return this.form.controls; }


    onSubmit() {
        this.submitted = true;

        // reset error message
        this.errorResponse = "";

        // check if form is valid
        if (this.form.invalid) {
            return;
        }

        this.loading = true;

        this.recaptchaV3Service.execute('importantAction')
        .subscribe((token) =>  {

          this.accountService.register(this.form.value, token)
          .pipe(first())
          .subscribe(
              data => {
                this.errorResponse = "Registration successful"; 
                this.router.navigate(['/home'], { relativeTo: this.route });
              },
              error => {
                var response = <ApiResponse> error.error; 
                this.errorResponse = response.message; 
                  this.loading = false;
              });
        });

    }


}
