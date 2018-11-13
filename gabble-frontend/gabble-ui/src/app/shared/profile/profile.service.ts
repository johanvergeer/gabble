import {Injectable, OnInit} from '@angular/core';
import {Profile} from "./profile.model";
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {OktaAuthService} from "@okta/okta-angular";

@Injectable()
export class ProfileService implements OnInit {
  private loggedInUserProfile: Profile;
  private notFollowing: Profile[];

  loggedInUserProfileChanged = new Subject<Profile>();
  notFollowingChanged = new Subject<Profile[]>();

  constructor(private httpClient: HttpClient, private oktaAuthService: OktaAuthService) {
  }

  ngOnInit(): void {
  }

  findById(id: string): Observable<Profile> {
    return this.httpClient
      .get<Profile>(`http://localhost:8090/user-profiles/${id}/`)
  }

  findForLoggedInUser(): Profile {
    if (this.loggedInUserProfile) {
      return this.loggedInUserProfile;
    } else {
      this.httpClient
        .get<Profile>(`http://localhost:8090/user-profiles/profile/`)
        .subscribe((response) => {
          this.loggedInUserProfile = response;
          this.loggedInUserProfileChanged.next(this.loggedInUserProfile)
        })
    }
  }

  findNotFollowing(): Profile[] {
    if (this.notFollowing) {
      return this.notFollowing.slice()
    } else {
      this.httpClient
        .get<Profile[]>(`http://localhost:8090/user-profiles/profile/not-following/`)
        .subscribe((response) => {
          this.notFollowing = response;
          this.notFollowingChanged.next(this.notFollowing.slice())
        })
    }
  }

  findByName(name: string) {
    // return this.profiles.find(profile => profile.name === name)
  }
}
