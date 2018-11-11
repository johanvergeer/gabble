import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../shared/profile/profile.service";
import {Profile} from "../shared/profile/profile.model";
import {OktaAuthService} from "@okta/okta-angular";

@Component({
  selector: 'app-dashboard-profile-card',
  templateUrl: './dashboard-profile-card.component.html',
  styleUrls: ['./dashboard-profile-card.component.scss']
})
export class DashboardProfileCardComponent implements OnInit {
  profile: Profile = new Profile('', '', '', '', '');

  constructor(private profileService: ProfileService, private oktaAuthService: OktaAuthService) {
  }

  ngOnInit() {
    this.oktaAuthService.getUser().then((user) => {
      this.profileService.findById(user.sub).subscribe(profile => {
        this.profile = profile;
      });
    });
  }
}
