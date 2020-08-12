import { MsalService, BroadcastService } from '@azure/msal-angular';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private subscription1: Subscription; 
  private subscription2: Subscription;



  constructor(private broadcastService: BroadcastService, private authService: MsalService,   private router: Router) { 

    this.subscription1 = broadcastService.subscribe("msal:loginFailure", payload => {
      // do something here
      console.log("Login Failure"); 
      
    });

    this.subscription2 = this.broadcastService.subscribe("msal:loginSuccess", payload => {
      // do something here
      console.log("Login Success");
      this.router.navigate(['/employees']);
  });
  
  }

  

  ngOnInit() {
  }

  ngOnDestroy() {
    if(this.subscription1) {
      this.subscription1.unsubscribe();
    }
    if(this.subscription2) {
      this.subscription2.unsubscribe();
    }
  }
  login() {
    const isIE = window.navigator.userAgent.indexOf('MSIE ') > -1 || window.navigator.userAgent.indexOf('Trident/') > -1;

    if (isIE) {
      this.authService.loginRedirect({
        extraScopesToConsent: ["user.read", "openid", "profile"]
      });
    } else {
      this.authService.loginPopup({
        extraScopesToConsent: ["user.read", "openid", "profile"]
      });
    }
}

}
