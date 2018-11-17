import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthInterceptor} from "./shared/okta/auth.interceptor";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {GabblesComponent} from './gabbles/gabbles.component';
import {GabblesListComponent} from './gabbles/gabbles-list/gabbles-list.component';
import {GabblesService} from "./shared/gabbles/gabbles.service";
import {StartPageComponent} from './start-page/start-page.component';
import {
  MatButtonModule,
  MatCardModule, MatDividerModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule, MatInputModule, MatListModule, MatMenuModule,
  MatToolbarModule
} from "@angular/material";
import {
  OktaAuthModule,
  OktaCallbackComponent,
  OktaAuthGuard
} from '@okta/okta-angular';
import {FormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";
import {RouterModule, Routes} from "@angular/router";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {ProfileComponent} from './profile/profile.component';
import {ProfileService} from "./shared/profile/profile.service";
import {TitleBarComponent} from './title-bar/title-bar.component';
import {MentionsPageComponent} from './mentions-page/mentions-page.component';
import {DashboardProfileCardComponent} from './dashboard-profile-card/dashboard-profile-card.component';
import {TrendsComponent} from './trends/trends.component';
import {TimelineComponent} from './timeline/timeline.component';
import {TimelineGabbleBoxComponent} from './timeline/timeline-gabble-box/timeline-gabble-box.component';
import {TimelineGabblesComponent} from './timeline/timeline-gabbles/timeline-gabbles.component';
import {WhoToFollowComponent} from './who-to-follow/who-to-follow.component';
import {ProfileHeaderComponent} from './profile/profile-header/profile-header.component';
import {ProfileInfoComponent} from './profile/profile-info/profile-info.component';
import {NotFoundComponent} from './errors/not-found/not-found.component';

const appRoutes: Routes = [
  {path: '', component: StartPageComponent, canActivate: [OktaAuthGuard]},
  {path: 'mentions', component: MentionsPageComponent, canActivate: [OktaAuthGuard]},
  {path: 'implicit/callback', component: OktaCallbackComponent},
  {path: 'profile/:id', component: ProfileComponent, canActivate: [OktaAuthGuard]},
  {path: '404', component: NotFoundComponent},
  {path: '**', redirectTo: '/404'}
];

const oktaConfig = {
  issuer: 'https://dev-934296.oktapreview.com/oauth2/default',
  redirectUri: 'http://localhost:4200/implicit/callback',
  clientId: '0oah6orjpumpDqaql0h7'
};

export function onAuthRequired({oktaAuth, router}) {
  // Redirect the user to your custom login page
  router.navigate(['/login']);
}

@NgModule({
  declarations: [
    AppComponent,
    GabblesComponent,
    StartPageComponent,
    GabblesListComponent,
    ProfileComponent,
    TitleBarComponent,
    MentionsPageComponent,
    DashboardProfileCardComponent,
    TrendsComponent,
    TimelineComponent,
    TimelineGabbleBoxComponent,
    TimelineGabblesComponent,
    WhoToFollowComponent,
    ProfileHeaderComponent,
    ProfileInfoComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatToolbarModule,
    OktaAuthModule.initAuth(oktaConfig),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    GabblesService,
    ProfileService,
    HttpClientModule,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
