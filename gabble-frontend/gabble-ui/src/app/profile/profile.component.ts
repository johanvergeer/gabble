import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../shared/profile/profile.service";
import {Profile} from "../shared/profile/profile.model";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profile: Profile = null;

  constructor(private profileService: ProfileService) {
  }

  ngOnInit() {
    this.profile = this.profileService.findByName('johan');
    console.log('foo');
    console.log(this.profile);
  }

}
