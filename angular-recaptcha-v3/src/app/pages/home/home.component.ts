import { Component, inject, OnInit } from '@angular/core';
import { AccountService } from '../../services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  readonly accountService = inject(AccountService);
  private readonly router = inject(Router); 

  ngOnInit() {
    if(!this.accountService.isUserAuthenticated()){
      this.router.navigate(['']);
    }
  }

  logout() {
    this.accountService.logout();
  }
}
