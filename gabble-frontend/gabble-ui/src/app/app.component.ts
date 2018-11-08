import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {OktaAuthService} from "@okta/okta-angular";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Gabble';
  isAuthenticated: boolean;

  constructor(public oktaAuth: OktaAuthService) {
  }

  async ngOnInit() {
    // Get the authentication state for immediate use
    this.isAuthenticated = await this.oktaAuth.isAuthenticated();

    // Subscribe to authentication state changes
    this.oktaAuth.$authenticationState.subscribe(
      (isAuthenticated: boolean) => this.isAuthenticated = isAuthenticated
    );
  }

  login() {
    this.oktaAuth.loginRedirect('/profile');
  }

  logout() {
    this.oktaAuth.logout('/');
  }

  // noinspection JSMethodCanBeStatic
  getUser(token) {
    return {
      name: token.claims.name,
      email: token.claims.email,
      username: token.claims.preferred_username
    };
  }
}
