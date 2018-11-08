import {Component, OnInit} from '@angular/core';
import {GabblesService} from "../../shared/gabbles/gabbles.service";

@Component({
  selector: 'app-gabbles-list',
  templateUrl: './gabbles-list.component.html',
  styleUrls: ['./gabbles-list.component.scss']
})
export class GabblesListComponent implements OnInit {
  
  constructor(private gabblesService: GabblesService) {
  }

  ngOnInit() {
  }
}
