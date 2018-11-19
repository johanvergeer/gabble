import {Component, OnInit} from '@angular/core';
import {Profile} from "../../shared/profile/profile.model";
import {ProfileService} from "../../shared/profile/profile.service";
import {ActivatedRoute} from "@angular/router";
import {MatDialog} from "@angular/material";
import {ProfileEditDialogComponent} from "../profile-edit-dialog/profile-edit-dialog.component";
import {GabblesService} from "../../shared/gabbles/gabbles.service";

@Component({
  selector: 'app-profile-header',
  templateUrl: './profile-header.component.html',
  styleUrls: ['./profile-header.component.scss']
})
export class ProfileHeaderComponent implements OnInit {
  profile: Profile;
  loggedInUserProfile: Profile;
  gabblesCount: number;

  constructor(private profileService: ProfileService,
              private gabblesService: GabblesService,
              private activatedRoute: ActivatedRoute,
              private dialog: MatDialog) {
  }

  public get canUpdate() {
    if (this.loggedInUserProfile && this.profile)
      return this.loggedInUserProfile.userId == this.profile.userId;
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      const userId = params["id"];
      this.profileService.findById(userId).subscribe(profile => {
        this.profile = profile;
      });

      this.gabblesService.findByUserId(userId)
        .subscribe(gabbles => {
          this.gabblesCount = gabbles.length;
        });
    });

    this.loggedInUserProfile = this.profileService.findForLoggedInUser();
    this.profileService.loggedInUserProfileChanged.subscribe((profile) => {
      this.loggedInUserProfile = profile;
    });

  }


  openDialog(): void {
    const dialogRef = this.dialog.open(ProfileEditDialogComponent, {
      width: '500px',
      data: this.profile
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.profileService.saveLoggedInUser(result);
    });
  }
}
