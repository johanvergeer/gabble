import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Profile} from "../../shared/profile/profile.model";

@Component({
  selector: 'app-profile-edit-dialog',
  templateUrl: './profile-edit-dialog.component.html',
  styleUrls: ['./profile-edit-dialog.component.scss']
})
export class ProfileEditDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ProfileEditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public profile: Profile) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
