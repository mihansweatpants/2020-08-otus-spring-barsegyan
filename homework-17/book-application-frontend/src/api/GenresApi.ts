import HttpApi from './HttpApi';
import { PaginationRequestParams } from 'api/types/base/request';
import { PaginationResponse } from './types/base/response';
import { GenreDto } from './types/books/genres';

class GenresApi extends HttpApi {

  getList = (params?: PaginationRequestParams) => {
    return this.get<PaginationResponse<GenreDto>>('/', { params })
  }

  getFullList = () => {
    return this.getAll<GenreDto>('/');
  }

}

export default new GenresApi('/api/genres');
