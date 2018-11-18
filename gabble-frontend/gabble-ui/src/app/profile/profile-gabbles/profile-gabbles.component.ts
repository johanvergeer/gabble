import {Component, OnInit} from '@angular/core';
import {GabblesService} from "../../shared/gabbles/gabbles.service";
import {ActivatedRoute} from "@angular/router";
import {Gabble} from "../../shared/gabbles/gabble.model";
import {ProfileService} from "../../shared/profile/profile.service";
import {Profile} from "../../shared/profile/profile.model";

@Component({
  selector: 'app-profile-gabbles',
  templateUrl: './profile-gabbles.component.html',
  styleUrls: ['./profile-gabbles.component.scss']
})
export class ProfileGabblesComponent implements OnInit {
  gabbles: Gabble[];
  profile: Profile;


  constructor(
    private gabblesService: GabblesService,
    private profileService: ProfileService,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.parent.params.subscribe(params => {
      const userId = params["id"];
      this.profileService.findById(userId).subscribe(profile => {
        this.profile = profile;
      });

      this.gabblesService.findByUserId(userId)
        .subscribe(gabbles => {
          console.log(typeof gabbles[0].createdOn);
          this.gabbles = gabbles;
          // console.log(this.gabbles);
        });
    });
  }

}
