import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GabblesComponent } from './gabbles/gabbles.component';
import { StartPageComponent } from './start-page/start-page.component';
import { GabblesListComponent } from './gabbles/gabbles-list/gabbles-list.component';

@NgModule({
  declarations: [
    AppComponent,
    GabblesComponent,
    StartPageComponent,
    GabblesListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
