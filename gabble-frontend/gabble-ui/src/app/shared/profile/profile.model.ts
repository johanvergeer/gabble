export class Profile {
  constructor(
    public userId: string,
    public username: string,
    public location: string,
    public website: string,
    public website_name: string,
    public bio: string,
    public followersCount: number,
    public followingCount: number) {
  }
}
