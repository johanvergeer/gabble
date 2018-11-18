import {Injectable, OnInit} from '@angular/core';
import {Gabble} from "./gabble.model";
import {Observable, Subject} from "rxjs";
import {Profile} from "../profile/profile.model";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable()
export class GabblesService implements OnInit {
  gabllesForLoggedInUser: Gabble[];
  gabllesForLoggedInUserChanged: Subject<Gabble[]>;

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit(): void {
  }

  getGabblesForLoggedInUser() {

  }

  findByUserId(userId: string) {
    return this.httpClient
      .get(`http://localhost:8080/${userId}`)
      .subscribe(res => {
        console.log(res)
      })
    // .get<Gabble[]>(`http://localhost:8080/gabbles/${userId}/`)
    // .pipe(map( res => res.map(gabble => new Gabble(gabble))))
  }
}
