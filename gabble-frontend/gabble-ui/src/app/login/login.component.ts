import {Component, OnInit} from '@angular/core';
import * as OktaSignIn from '@okta/okta-signin-widget';
import {OktaAuthService} from "@okta/okta-angular";
import {NavigationStart, Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  signIn;
  widget = new OktaSignIn({
    baseUrl: 'https://dev-934296.oktapreview.com'
  });

  constructor(oktaAuth: OktaAuthService, router: Router) {
    this.signIn = oktaAuth;

    // Show the widget when prompted, otherwise remove it from the DOM.
    router.events.forEach(event => {
      if (event instanceof NavigationStart) {
        switch (event.url) {
          case '/login':
            break;
          case '/protected':
            break;
          default:
            this.widget.remove();
            break;
        }
      }
    });
  }

  ngOnInit() {
    this.widget.renderEl({
        el: '#okta-signin-container'
      },
      (res) => {
        if (res.status === 'SUCCESS') {
          this.signIn.loginRedirect('/', {sessionToken: res.session.token});
          // Hide the widget
          this.widget.hide();
        }
      },
      (err) => {
        throw err;
      }
    );
  }
}
