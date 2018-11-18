import { Component, OnInit } from '@angular/core';
import {ProfileService} from "../../shared/profile/profile.service";
import {Profile} from "../../shared/profile/profile.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile-following',
  templateUrl: './profile-following.component.html',
  styleUrls: ['./profile-following.component.scss']
})
export class ProfileFollowingComponent implements OnInit {
  following: Profile[];

  constructor(private profileService: ProfileService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.parent.params.subscribe(params => {
      const userId = params["id"];
      this.profileService.findFollowingById(userId).subscribe(following => {
        this.following = following;
      });
    });
  }

}
