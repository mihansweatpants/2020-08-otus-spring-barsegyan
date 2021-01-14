import HttpApi from './HttpApi';
import { PaginationRequestParams } from 'api/types/base/request';
import { PaginationResponse } from './types/base/response';
import { AuthorDto } from './types/books/authors';

class AuthorsApi extends HttpApi {

  getList = (params?: PaginationRequestParams) => {
    return this.get<PaginationResponse<AuthorDto>>('/', { params })
  }

  getFullList = () => {
    return this.getAll<AuthorDto>('/')
  }

}

export default new AuthorsApi('/api/authors');
