import { Component, OnInit } from '@angular/core';
import {Profile} from "../../shared/profile/profile.model";
import {ProfileService} from "../../shared/profile/profile.service";

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.scss']
})
export class ProfileInfoComponent implements OnInit {
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
