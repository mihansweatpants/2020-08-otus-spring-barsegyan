import { Client } from '@stomp/stompjs';

import { StompMessage } from 'api/types/base/stomp';
import HttpApi from './HttpApi';

const WS_URL = process.env.REACT_APP_WS_URL;

export class StompService {
  private static instance: StompService | null;

  private client: Client;

  constructor() {
    this.client = new Client({
      brokerURL: WS_URL,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });
  }

  static getInstance() {
    if (!StompService.instance) {
      StompService.instance = new StompService();
    }

    return StompService.instance;
  }

  private getConnectHeaders = () => ({
    'X-Auth-Token': localStorage.getItem(HttpApi.AUTH_TOKEN_LOCAL_STORAGE_KEY) ?? '',
  });

  connect = (onMessageRecieved: (message: StompMessage) => void) => {
    this.client.connectHeaders = this.getConnectHeaders();

    this.client.onConnect = () => {
      this.client.subscribe('/user/queue/updates', (message) => {
        onMessageRecieved(JSON.parse(message.body));
      });
    };

    this.client.activate();
  };

  disconnect = () => {
    if (this.client.active) {
      this.client.deactivate();
      StompService.instance = null;
    }
  };
}

export default StompService;
