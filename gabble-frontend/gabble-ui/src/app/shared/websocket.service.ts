import {Injectable} from '@angular/core';
import * as Rx from 'rxjs/Rx'

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private subject: Rx.Subject<MessageEvent>;

  constructor() {
  }

  public connect(url: string): Rx.Subject<MessageEvent> {
    if (!this.subject) {
      this.subject = this.createSubject(url);
      console.log(`Successfully connected ${url}`)
    }
    return this.subject
  }

  private createSubject(url: string): Rx.Subject<MessageEvent> {
    const ws = new WebSocket(url);
    return Rx.Subject.create(this.createObserver(ws), this.createObservable(ws))
  }

  /**
   * Create observer for the given WebSocket
   * @param ws WebSocket to create observer for
   */
  private createObserver(ws: WebSocket) {
    return {
      next: (data: Object) => {
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(JSON.stringify(data))
        }
      }
    };
  }

  /**
   * Create observable for the given WebSocket
   * @param ws WebSocket to create the observable for
   */
  private createObservable(ws: WebSocket) {
    return Rx.Observable.create(
      (obs: Rx.Observer<MessageEvent>) => {
        ws.onmessage = obs.next.bind(obs);
        ws.onerror = obs.error.bind(obs);
        ws.onclose = obs.complete.bind(obs);
        return ws.close.bind(ws)
      }
    );
  }
}
