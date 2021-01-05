import HttpApi from './HttpApi';
import { PaginationRequestParams } from 'api/types/base/request';
import { PaginationResponse } from './types/base/response';
import { BookReviewDto } from './types/books/bookReviews';

class BookReviewsApi extends HttpApi {

  getByBookId = (bookId: number, params?: PaginationRequestParams) => {
    return this.get<PaginationResponse<BookReviewDto>>(`/${bookId}/reviews`, { params })
  }

  addReview = (bookId: number, text: string) => {
    return this.post<BookReviewDto>(`/reviews`, { bookId, text });
  }

}

export default new BookReviewsApi('/api/books');
