import {Injectable, OnInit} from '@angular/core';
import {Gabble} from "./gabble.model";
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {OktaAuthService} from "@okta/okta-angular";
import {WebSocketService} from "../websocket.service";

@Injectable()
export class GabblesService implements OnInit {
  gabblesMentionedIn: string;

  mentionsSubject: Subject<Gabble>;

  gabblesForLoggedInUser: Gabble[];
  gabblesForLoggedInUserChanged = new Subject<Gabble[]>();

  gabblesCountForLoggedInUser: number;
  gabblesCountForLoggedInUserChanged = new Subject<number>();

  allTags: string[];
  allTagsUpdated = new Subject<string[]>();

  mentions: Gabble[];
  mentionsUpdated = new Subject<Gabble[]>();

  constructor(
    private httpClient: HttpClient,
    private oktaAuth: OktaAuthService,
    private webSocketService: WebSocketService) {
    console.log(oktaAuth.getUser())
  }

  ngOnInit(): void {
  }

  findGabblesForLoggedInUser(userId: string) {
    if (this.gabblesForLoggedInUser) {
      this.gabblesCountForLoggedInUser = this.gabblesForLoggedInUser.length;
      return this.gabblesForLoggedInUser
    } else {
      this.updateGabblesForLoggedInUser(userId)
    }
  }

  startGabblesSession(userId: string) {
    this.httpClient.post(`http://localhost:8080/users/sessions/${userId}`, {})
      .subscribe(
        (val) => {
          console.log("Session cookie created");
        },
        response => {
          console.log("Session cookie not created", response);
        },
        () => {
          console.log("The POST observable is now completed.");
        });
  }

  findByUserId(userId: string): Observable<Gabble[]> {
    return this.httpClient
      .get<Gabble[]>(`http://localhost:8080/${userId}`)
      .pipe(map(res => res.map(gabble => new Gabble(gabble))))
  }

  updateGabblesForLoggedInUser(userId: string) {
    this.httpClient
      .get<Gabble[]>(`http://localhost:8080/${userId}`)
      .pipe(map(res => res.map(gabble => new Gabble(gabble))))
      .subscribe((response) => {
        this.gabblesForLoggedInUser = response;
        this.gabblesForLoggedInUserChanged.next(this.gabblesForLoggedInUser.slice());

        this.gabblesCountForLoggedInUser = this.gabblesForLoggedInUser.length;
        this.gabblesCountForLoggedInUserChanged.next(this.gabblesCountForLoggedInUser);
      })
  }

  findAllTags() {
    if (this.allTags) {
      return this.allTags
    } else {
      this.updateAllTags()
    }
  }

  updateAllTags() {
    this.httpClient
      .get<string[]>("http://localhost:8080/tags")
      .subscribe((response) => {
          this.allTags = response;
          this.allTagsUpdated.next(this.allTags.slice())
        }
      )
  }

  findByMentionedIn(userId: string) {
    if (this.mentions) {
      return this.mentions
    } else {
      this.updateMentionedIn(userId)
    }
  }

  subscribeToMentionedInSocket() {
    this.mentionsSubject = <Subject<Gabble>> this.webSocketService
      .connect(`ws://localhost:8080/mentions`)
      .map((response: MessageEvent) => {
        // console.log(`Raw data received => ${response.data}`);
        const data = JSON.parse(response.data);
        return <Gabble>data;
      });
  }

  updateMentionedIn(userId: string) {
    this.httpClient
      .get<Gabble[]>(`http://localhost:8080/${userId}/mentions`)
      .subscribe((response) => {
          this.mentions = response;
          this.mentionsUpdated.next(this.mentions.slice());
          console.log("mentions =>", this.mentions)
        }
      );
  }

  create(text: string, userId: string, username: string) {
    this.httpClient.post(`http://localhost:8080/`,
      {
        'text': text,
        'createdById': userId,
        'createdByUsername': username
      })
      .subscribe(
        (val) => {
          console.log("POST call successful value returned in body", val);
          this.updateGabblesForLoggedInUser(userId);
          this.updateAllTags();
        },
        response => {
          console.log("POST call in error", response);
        },
        () => {
          console.log("The POST observable is now completed.");
        });
  }
}
