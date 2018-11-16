export class Profile {
  constructor(
    public userId: string,
    public username: string,
    public location: string,
    public website: string,
    public bio: string,
    followersCount: number,
    followingCount: number) {
  }
}
