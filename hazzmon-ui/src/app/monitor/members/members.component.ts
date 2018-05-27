import { Component, OnInit } from '@angular/core';
import {MembersService} from './members.service';

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html'
})
export class MembersComponent implements OnInit {

  public members: any[] = [];

  constructor(private membersService: MembersService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.membersService.getMembers('all').subscribe(response => {
      this.members = response;
    });
  }

}
