import {Injectable, OnInit} from '@angular/core';
import {Profile} from "./profile.model";
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {OktaAuthService} from "@okta/okta-angular";

@Injectable()
export class ProfileService implements OnInit {
  private loggedInUserProfile: Profile;
  loggedInUserProfileChanged = new Subject<Profile>();

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

  findByName(name: string) {
    // return this.profiles.find(profile => profile.name === name)
  }
}
