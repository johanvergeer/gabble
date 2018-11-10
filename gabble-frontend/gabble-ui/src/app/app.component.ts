import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {OktaAuthService} from "@okta/okta-angular";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  preserveWhitespaces: true
})
export class AppComponent implements OnInit {
  title = 'Gabble';

  constructor() {
  }

  async ngOnInit() {
  }
}
