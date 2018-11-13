import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../shared/profile/profile.service";
import {Profile} from "../shared/profile/profile.model";

@Component({
  selector: 'app-who-to-follow',
  templateUrl: './who-to-follow.component.html',
  styleUrls: ['./who-to-follow.component.scss']
})
export class WhoToFollowComponent implements OnInit {
  notFollowing: Profile[];

  constructor(private profileService: ProfileService) {
  }

  ngOnInit() {
    this.notFollowing = this.profileService.findNotFollowing();
    this.profileService.notFollowingChanged.subscribe((notFollowing) => {
      this.notFollowing = notFollowing;
      console.log(this.notFollowing);
    })
  }

}
