import HttpApi from './HttpApi';
import { AuthenticatationRequestDto } from 'api/types/user/auth';
import { UserDto } from 'api/types/user/user';

class AuthApi extends HttpApi {

  login = ({ username, password }: AuthenticatationRequestDto) => {
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    return this.post<UserDto>('/login', formData);
  }

  logout = () => {
    return this.get('/logout');
  };

  getCurrentUser = () => {
    return this.get<UserDto>('/users/me');
  }

}

export default new AuthApi('/api');
