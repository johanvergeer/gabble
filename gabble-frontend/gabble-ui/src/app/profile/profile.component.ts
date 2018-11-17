import {Component, OnInit} from '@angular/core';
import {Profile} from "../shared/profile/profile.model";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profile: Profile = null;

  constructor() {
  }

  ngOnInit() {
  }
}
