import {Injectable, OnInit} from '@angular/core';
import {Profile} from "./profile.model";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ProfileService implements OnInit {
  profiles: Profile[] = [
    new Profile('abcd1234', 'johan', 'Helmond', 'https://www.redgyro.com', 'something about Johan')
  ];

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit(): void {
    this.profiles.push()
  }

  // findById(id: string): Profile {
  //   response = this.httpClient
  //     .get("http://localhost:8090/user-profiles/00ugl9afjiwNub6yt0h7/")
  //     .subscribe(response => {
  //       response.toString() as Profile
  //     })
  // }

  findByName(name: string) {
    // return this.profiles.find(profile => profile.name === name)
  }
}
