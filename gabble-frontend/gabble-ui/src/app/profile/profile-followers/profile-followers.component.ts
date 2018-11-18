import { Component, OnInit } from '@angular/core';
import {Profile} from "../../shared/profile/profile.model";
import {ProfileService} from "../../shared/profile/profile.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile-followers',
  templateUrl: './profile-followers.component.html',
  styleUrls: ['./profile-followers.component.scss']
})
export class ProfileFollowersComponent implements OnInit {
  followers: Profile[];

  constructor(private profileService: ProfileService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.parent.params.subscribe(params => {
      const userId = params["id"];
      this.profileService.findFollowersById(userId).subscribe(followers => {
        this.followers = followers;
      });
    });
  }

}
