import {Injectable, OnInit} from '@angular/core';
import {Gabble} from "./gabble.model";

@Injectable()
export class GabblesService implements OnInit {
  gabbleObjects: Gabble[];
  gabbles = [
    {"text": "ridiculus mus. Aenean", "createdOn": "2018-06-26T10:41:54-07:00"},
    {"text": "eros nec", "createdOn": "2019-02-02T23:37:05-08:00"},
    {"text": "Quisque ornare tortor", "createdOn": "2019-02-20T12:51:47-08:00"},
    {"text": "tempor, est ac mattis semper,", "createdOn": "2019-01-15T07:08:55-08:00"},
    {"text": "enim.", "createdOn": "2019-07-18T14:40:10-07:00"},
    {"text": "aliquet libero. Integer", "createdOn": "2018-10-15T15:46:35-07:00"},
    {"text": "elementum purus, accumsan interdum libero", "createdOn": "2018-05-10T18:51:25-07:00"},
    {"text": "quis", "createdOn": "2019-06-14T03:35:34-07:00"},
    {"text": "ornare placerat, orci lacus vestibulum", "createdOn": "2018-04-09T12:09:58-07:00"},
    {"text": "Fusce dolor", "createdOn": "2018-06-24T10:30:28-07:00"},
    {"text": "elit. Etiam laoreet, libero", "createdOn": "2019-03-18T17:21:04-07:00"},
    {"text": "sem", "createdOn": "2018-06-26T11:59:40-07:00"},
    {"text": "Sed", "createdOn": "2018-05-07T17:17:06-07:00"},
    {"text": "Morbi metus. Vivamus", "createdOn": "2017-10-18T22:46:04-07:00"},
    {"text": "vitae odio sagittis semper.", "createdOn": "2019-01-12T13:48:31-08:00"},
    {"text": "ultricies", "createdOn": "2018-10-16T22:38:06-07:00"},
    {"text": "adipiscing", "createdOn": "2019-08-07T00:15:52-07:00"},
    {"text": "at, velit.", "createdOn": "2018-05-29T23:32:11-07:00"},
    {"text": "id, blandit at, nisi.", "createdOn": "2019-07-14T23:26:47-07:00"},
    {"text": "dis parturient montes,", "createdOn": "2019-01-25T07:34:59-08:00"},
    {"text": "malesuada", "createdOn": "2018-08-26T08:55:07-07:00"},
    {"text": "In ornare sagittis felis.", "createdOn": "2019-04-08T17:46:49-07:00"},
    {"text": "nisl sem, consequat nec,", "createdOn": "2018-03-09T19:59:45-08:00"},
    {"text": "eget, volutpat ornare, facilisis", "createdOn": "2019-02-16T22:14:44-08:00"}
  ];

  constructor() {
  }

  ngOnInit(): void {
    this.gabbleObjects.push(new Gabble("foo", new Date("2018-06-26T10:41:54-07:00"), "foo"))
  }

  all() {
    return this.gabbles;
  }
}
