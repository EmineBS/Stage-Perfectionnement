export interface IAmine {
    myName?: String;
    familyName?: String;
    age?: Number;
  }
  
  export class Amine implements IAmine {
    constructor(public myName: String, public familyName: String, public age: Number) {}
  }