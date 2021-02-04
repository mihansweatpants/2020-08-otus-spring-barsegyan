import { Client } from '@stomp/stompjs';

import { StompMessage } from 'api/types/base/stomp';
import HttpApi from './HttpApi';

const WS_URL = process.env.REACT_APP_WS_URL;

export class StompService {
  private client: Client;
  private static instance: StompService;
  private authToken: string = localStorage.getItem(HttpApi.AUTH_TOKEN_LOCAL_STORAGE_KEY) ?? '';

  constructor(wsUri) {
    this.client = new Client({
      brokerURL: wsUri,
      connectHeaders: {
        'X-Auth-Token': this.authToken,
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });
  }

  static getInstance(wsUri) {
    if (!StompService.instance) {
      StompService.instance = new StompService(wsUri);
    }
    return StompService.instance;
  }

  connect = (onMessageRecieved: (message: StompMessage) => void) => {
    const isAuthentiated = Boolean(this.authToken);

    this.client.onConnect = () => {
      this.client.subscribe('/user/queue/updates', (message) => {
        onMessageRecieved(JSON.parse(message.body));
      });
    };

    if (isAuthentiated) {
      this.client.activate();
    }
  }
}

export default StompService.getInstance(WS_URL);
