import {Injectable, OnInit} from '@angular/core';
import {Profile} from "./profile.model";

@Injectable()
export class ProfileService implements OnInit {
  profiles: Profile[] = [
    new Profile('abcd1234', 'johan', 'Helmond', 'https://www.redgyro.com', 'something about Johan')
  ];

  constructor() {
  }

  ngOnInit(): void {
    this.profiles.push()
  }

  findByName(name: string) {
    return this.profiles.find(profile => profile.name === name)
  }
}
