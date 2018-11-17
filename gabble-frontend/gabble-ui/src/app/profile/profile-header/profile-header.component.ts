import { Component, OnInit } from '@angular/core';
import {Profile} from "../../shared/profile/profile.model";
import {ProfileService} from "../../shared/profile/profile.service";

@Component({
  selector: 'app-profile-header',
  templateUrl: './profile-header.component.html',
  styleUrls: ['./profile-header.component.scss']
})
export class ProfileHeaderComponent implements OnInit {
  profile: Profile;

  constructor(private profileService: ProfileService) {
  }

  ngOnInit() {
    this.profile = this.profileService.findForLoggedInUser();
    this.profileService.loggedInUserProfileChanged.subscribe((profile) => {
      this.profile = profile;
    });
  }
}
