export class Profile {
  public userId: string;
  public username: string;
  public location: string;
  public website: string;
  public bio: string;
  public followersCount: number;
  public followingCount: number;

  public get website_name() {
    return this.website.replace(/(^\w+:|^)\/\//, '');
  }

  constructor(obj?: any) {
    Object.assign(this, obj)
  }
}
