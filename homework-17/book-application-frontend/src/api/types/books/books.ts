import { AuthorDto } from './authors';
import { GenreDto } from './genres';

export interface BookDto {
  id: number;
  title: string;
  author: AuthorDto;
  genres: GenreDto[];
}

export interface CreateBookDto {
  title: string;
  genreIds: number[];
  authorId: number;
}
