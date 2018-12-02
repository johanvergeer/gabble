import {Component, Input, OnInit} from '@angular/core';
import {Gabble} from "../shared/gabbles/gabble.model";

@Component({
  selector: 'app-gabble',
  templateUrl: './gabble.component.html',
  styleUrls: ['./gabble.component.scss']
})
export class GabbleComponent implements OnInit {
  @Input() gabble: Gabble;

  constructor() { }

  ngOnInit() {
  }

}
