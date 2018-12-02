import {Component, OnInit} from '@angular/core';
import {GabblesService} from "../shared/gabbles/gabbles.service";
import {OktaAuthService} from "@okta/okta-angular";
import {Gabble} from "../shared/gabbles/gabble.model";

@Component({
  selector: 'app-mentions-page',
  templateUrl: './mentions-page.component.html',
  styleUrls: ['./mentions-page.component.scss']
})
export class MentionsPageComponent implements OnInit {
  gabbles: Gabble[];

  constructor(private gabblesService: GabblesService, private oktaAuth: OktaAuthService) {
  }

  ngOnInit() {
    this.oktaAuth.getUser().then(user => {
      this.gabbles = this.gabblesService.findByMentionedIn(user.sub);
      this.gabblesService.mentionsUpdated.subscribe(gabbles => {
        this.gabbles = gabbles;
      });
    });
  }

}
