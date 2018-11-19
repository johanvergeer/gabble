import {Component, OnInit} from '@angular/core';
import {GabblesService} from "../../shared/gabbles/gabbles.service";
import {Gabble} from "../../shared/gabbles/gabble.model";
import {OktaAuthService} from "@okta/okta-angular";

@Component({
  selector: 'app-timeline-gabbles',
  templateUrl: './timeline-gabbles.component.html',
  styleUrls: ['./timeline-gabbles.component.scss']
})
export class TimelineGabblesComponent implements OnInit {
  gabbles: Gabble[];

  constructor(
    private gabblesService: GabblesService,
    private oktaAuth: OktaAuthService) {
  }

  ngOnInit() {
    this.oktaAuth.getUser().then(user =>
      this.gabblesService.findByUserId(user.sub)
        .subscribe(gabbles => {
          this.gabbles = gabbles;
        }))
  }
}
