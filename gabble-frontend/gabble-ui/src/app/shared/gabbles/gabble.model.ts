export class Gabble {
  public text: string;
  public createdOn: Date;
  public createdById: string;

  constructor(obj?: any) {
    Object.assign(this, obj)
  }
}
