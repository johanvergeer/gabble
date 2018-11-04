import {ChangeDetectorRef, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'gabble-ui';
  user;
  signIn;

  constructor(private changeDetectorRef: ChangeDetectorRef) {
  }


  // noinspection JSMethodCanBeStatic
  getUser(token) {
    return {
      name: token.claims.name,
      email: token.claims.email,
      username: token.claims.preferred_username
    };
  }

  ngOnInit() {

  }
}
