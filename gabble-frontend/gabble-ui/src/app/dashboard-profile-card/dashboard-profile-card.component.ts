import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../shared/profile/profile.service";
import {Profile} from "../shared/profile/profile.model";

@Component({
  selector: 'app-dashboard-profile-card',
  templateUrl: './dashboard-profile-card.component.html',
  styleUrls: ['./dashboard-profile-card.component.scss']
})
export class DashboardProfileCardComponent implements OnInit {
  profile: Profile = new Profile('', '', '', '', '', 0, 0);

  constructor(private profileService: ProfileService) {
  }

  ngOnInit() {
    this.profile = this.profileService.findForLoggedInUser();
    this.profileService.loggedInUserProfileChanged.subscribe((profile) => {
      this.profile = profile;
    });
  }
}
