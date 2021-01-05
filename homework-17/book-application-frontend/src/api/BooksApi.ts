import HttpApi from './HttpApi';
import { PaginationRequestParams } from 'api/types/base/request';
import { PaginationResponse } from './types/base/response';
import { BookDto, CreateBookDto } from './types/books/books';

class BooksApi extends HttpApi {

  getList = (params?: PaginationRequestParams) => {
    return this.get<PaginationResponse<BookDto>>('/', { params })
  }

  getById = (bookId: number) => {
    return this.get<BookDto>(`/${bookId}`);
  }

  createBook = (createBookDto: CreateBookDto) => {
    return this.post<BookDto>('/', createBookDto);
  }

}

export default new BooksApi('/api/books');
