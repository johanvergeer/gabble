export class Gabble {
  public text: string;
  public createdOn: string;
  public createdById: string;
  public createdByUsername: string;

  constructor(obj?: any) {
    Object.assign(this, obj);
    console.log(this.createdOn.toString());
    // this.createdOn = new Date(obj.createdOn)
  }
}
