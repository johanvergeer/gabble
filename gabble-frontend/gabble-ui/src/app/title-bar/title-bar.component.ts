import {Component, OnInit} from '@angular/core';
import {OktaAuthService} from "@okta/okta-angular";
import {Router} from "@angular/router";
import {ProfileService} from "../shared/profile/profile.service";
import {Profile} from "../shared/profile/profile.model";

@Component({
  selector: 'app-title-bar',
  templateUrl: './title-bar.component.html',
  styleUrls: ['./title-bar.component.scss']
})
export class TitleBarComponent implements OnInit {
  loggedInUserProfile: Profile;
  isAuthenticated: boolean;

  constructor(
    public oktaAuth: OktaAuthService,
    private profileService: ProfileService) {
    // Subscribe to authentication state changes
    this.oktaAuth.$authenticationState.subscribe(
      (isAuthenticated: boolean) => this.isAuthenticated = isAuthenticated
    );
  }

  async ngOnInit() {
    // Get the authentication state for immediate use
    this.isAuthenticated = await this.oktaAuth.isAuthenticated();

    this.loggedInUserProfile = this.profileService.findForLoggedInUser();
    this.profileService.loggedInUserProfileChanged.subscribe(profile => {
      this.loggedInUserProfile = profile;
    });
  }

  login() {
    this.oktaAuth.loginRedirect('/profile');
  }

  async logout() {
    // Terminates the session with Okta and removes current tokens.
    await this.oktaAuth.logout();
    this.oktaAuth.loginRedirect();
  }
}
