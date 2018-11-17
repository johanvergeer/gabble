import {Component, OnInit} from '@angular/core';
import {Profile} from "../../shared/profile/profile.model";
import {ProfileService} from "../../shared/profile/profile.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile-header',
  templateUrl: './profile-header.component.html',
  styleUrls: ['./profile-header.component.scss']
})
export class ProfileHeaderComponent implements OnInit {
  profile: Profile;
  loggedInUserProfile: Profile;

  constructor(private profileService: ProfileService, private activatedRoute: ActivatedRoute) {
  }

  public get canUpdate() {
    if (this.loggedInUserProfile && this.profile)
      return this.loggedInUserProfile.userId == this.profile.userId;
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      const userId = params["id"];
      this.profileService.findById(userId).subscribe(profile => {
        this.profile = profile;
      });
    });

    this.loggedInUserProfile = this.profileService.findForLoggedInUser();
    this.profileService.loggedInUserProfileChanged.subscribe((profile) => {
      this.loggedInUserProfile = profile;
    });
  }
}
