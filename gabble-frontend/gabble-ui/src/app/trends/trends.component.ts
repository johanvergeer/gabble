import {Component, OnInit} from '@angular/core';
import {GabblesService} from "../shared/gabbles/gabbles.service";

@Component({
  selector: 'app-trends',
  templateUrl: './trends.component.html',
  styleUrls: ['./trends.component.scss']
})
export class TrendsComponent implements OnInit {
  tags: string[];

  constructor(private gabblesService: GabblesService) {
  }

  ngOnInit() {
    this.tags = this.gabblesService.findAllTags()
    this.gabblesService.allTagsUpdated.subscribe(tags => {
      this.tags = tags;
    })
  }

}
