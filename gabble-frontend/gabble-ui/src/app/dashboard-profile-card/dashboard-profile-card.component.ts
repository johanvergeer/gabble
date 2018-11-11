import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../shared/profile/profile.service";
import {Profile} from "../shared/profile/profile.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-dashboard-profile-card',
  templateUrl: './dashboard-profile-card.component.html',
  styleUrls: ['./dashboard-profile-card.component.scss']
})
export class DashboardProfileCardComponent implements OnInit {
  profile: Profile;
  profileObservable: Observable<Profile>;

  constructor(private profileService: ProfileService, private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.httpClient
      .get<Profile>("http://localhost:8090/user-profiles/00ugl9afjiwNub6yt0h7/")
      .subscribe(profile => console.log(profile));

    // console.log(this.profileObservable)
  }

}
