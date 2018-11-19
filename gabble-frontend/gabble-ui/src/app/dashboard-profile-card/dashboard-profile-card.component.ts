import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../shared/profile/profile.service";
import {Profile} from "../shared/profile/profile.model";
import {GabblesService} from "../shared/gabbles/gabbles.service";

@Component({
  selector: 'app-dashboard-profile-card',
  templateUrl: './dashboard-profile-card.component.html',
  styleUrls: ['./dashboard-profile-card.component.scss']
})
export class DashboardProfileCardComponent implements OnInit {
  profile: Profile;
  gabblesCount: number;

  constructor(
    private profileService: ProfileService,
    private gabblesService: GabblesService) {
  }

  ngOnInit() {
    this.profile = this.profileService.findForLoggedInUser();
    this.profileService.loggedInUserProfileChanged.subscribe((profile) => {
      this.profile = profile;

      this.gabblesService.findByUserId(this.profile.userId).subscribe(gabbles =>
        this.gabblesCount = gabbles.length)
    });
  }
}
