import {Component, OnInit} from '@angular/core';
import {GabblesService} from "../../shared/gabbles/gabbles.service";
import {OktaAuthService} from "@okta/okta-angular";
import {ProfileService} from "../../shared/profile/profile.service";
import {Profile} from "../../shared/profile/profile.model";

@Component({
  selector: 'app-timeline-gabble-box',
  templateUrl: './timeline-gabble-box.component.html',
  styleUrls: ['./timeline-gabble-box.component.scss']
})
export class TimelineGabbleBoxComponent implements OnInit {
  gabbleText: string;
  profile: Profile;

  constructor(
    private gabblesService: GabblesService,
    private oktaAuth: OktaAuthService,
    private profileService: ProfileService) {
  }

  ngOnInit() {
    this.profile = this.profileService.findForLoggedInUser();
    this.profileService.loggedInUserProfileChanged.subscribe((profile) => {
      this.profile = profile;
    });
  }

  onSaveGabble() {
    this.gabblesService.create(this.gabbleText, this.profile.userId, this.profile.username)
  }
}
