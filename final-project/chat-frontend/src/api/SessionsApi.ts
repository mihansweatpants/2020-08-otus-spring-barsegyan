import HttpApi from './HttpApi';

import { SessionDto } from 'api/types/sessions';

class SessionsApi extends HttpApi {
  getUserSessions = () => {
    return this.get<SessionDto[]>('/');
  };

  logout = () => {
    return this.post<void>('/logout');
  };

  logoutAll = () => {
    return this.post<void>('/logout-all')
  };
}

export default new SessionsApi('/api/sessions');
