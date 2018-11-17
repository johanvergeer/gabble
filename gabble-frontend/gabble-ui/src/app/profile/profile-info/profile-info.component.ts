import {Component, OnInit} from '@angular/core';
import {Profile} from "../../shared/profile/profile.model";
import {ProfileService} from "../../shared/profile/profile.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.scss']
})
export class ProfileInfoComponent implements OnInit {
  profile: Profile;

  constructor(private profileService: ProfileService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      const userId = params["id"];
      this.profileService.findById(userId).subscribe(profile => {
        this.profile = profile;
      });
    });
  }
}
