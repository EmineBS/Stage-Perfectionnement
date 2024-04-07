export interface IEvent {
    eventName: String | null;
    region: String | null;
    registration: Boolean | null;
    gameName: String | null;
    startTime: number | null;
    minPlayers: Number | null;
    maxPlayers: Number | null;
    players: any[];
  }