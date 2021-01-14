import React, { FC, useEffect, useState } from 'react';

import { TablePagination } from '@material-ui/core';

import { BooksApi } from 'api';
import { BookDto } from 'api/types/books/books';
import { usePagination } from 'utils/usePagination';
import BooksTable from './BooksTable';

const BooksList: FC = () => {

  const [books, setBooks] = useState<BookDto[]>([]);
  const {
    page,
    setPage,
    totalItems,
    setTotalItems,
    limit,
    setLimit,
  } = usePagination();

  useEffect(
    () => {
      BooksApi.getList({ limit, page }).then(({ items, totalItems }) => {
        setBooks(items);
        setTotalItems(totalItems);
      });
    },
    [setTotalItems, limit, page],
  );

  return (
    <>
      <BooksTable books={books} />
      <TablePagination
        component="div"
        page={page}
        onChangePage={(_, page) => setPage(page)}
        count={totalItems}
        rowsPerPage={limit}
        onChangeRowsPerPage={({ target: { value } }) => setLimit(+value)}
      />
    </>
  );
};

export default BooksList;
