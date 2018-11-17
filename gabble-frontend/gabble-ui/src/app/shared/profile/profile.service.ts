import {Injectable, OnInit} from '@angular/core';
import {Profile} from "./profile.model";
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {map} from "rxjs/operators";

@Injectable()
export class ProfileService implements OnInit {
  private loggedInUserProfile: Profile;
  private notFollowing: Profile[];

  loggedInUserProfileChanged = new Subject<Profile>();
  notFollowingChanged = new Subject<Profile[]>();

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit(): void {
  }

  findById(userId: string): Observable<Profile> {
    return this.httpClient
      .get<Profile>(`http://localhost:8090/user-profiles/${userId}/`)
      .pipe(map(res => new Profile(res)))
  }

  findForLoggedInUser(): Profile {
    if (this.loggedInUserProfile) {
      return this.loggedInUserProfile;
    } else {
      this.updateLoggedInUser()
    }
  }

  updateLoggedInUser() {
    this.httpClient
      .get<Profile>(`http://localhost:8090/user-profiles/profile/`)
      .subscribe((response) => {
        this.loggedInUserProfile = new Profile(response);
        this.loggedInUserProfileChanged.next(this.loggedInUserProfile)
      })
  }

  findNotFollowing(): Profile[] {
    if (this.notFollowing) {
      return this.notFollowing.slice()
    } else {
      this.updateNotFollowing();
    }
  }

  updateNotFollowing() {
    this.httpClient
      .get<Profile[]>(`http://localhost:8090/user-profiles/profile/not-following/`)
      .subscribe((response) => {
        this.notFollowing = response;
        this.notFollowingChanged.next(this.notFollowing.slice())
      })
  }

  findByName(name: string) {
    // return this.profiles.find(profile => profile.name === name)
  }

  followUser(userProfile: Profile) {
    console.log(`userProfile.id = `, userProfile.userId);

    this.httpClient.post(`http://localhost:8090/user-profiles/profile/following/`, {'userId': userProfile.userId})
      .subscribe(
        (val) => {
          console.log("POST call successful value returned in body", val);
          this.updateLoggedInUser();
          this.updateNotFollowing()
        },
        response => {
          console.log("POST call in error", response);
        },
        () => {
          console.log("The POST observable is now completed.");
        });
  }
}
