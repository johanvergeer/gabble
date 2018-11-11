import {Injectable, OnInit} from '@angular/core';
import {Profile} from "./profile.model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class ProfileService implements OnInit {

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit(): void {
  }

  findById(id: string): Observable<Profile> {
    return this.httpClient
      .get<Profile>(`http://localhost:8090/user-profiles/${id}/`)
  }

  findForLoggedInUser(): Observable<Profile> {
    return this.httpClient
      .get<Profile>(`http://localhost:8090/user-profiles/profile/`)
  }

  findByName(name: string) {
    // return this.profiles.find(profile => profile.name === name)
  }
}
