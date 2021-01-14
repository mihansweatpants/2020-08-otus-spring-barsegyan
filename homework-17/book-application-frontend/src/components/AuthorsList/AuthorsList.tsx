import React, { FC, useEffect, useState } from 'react';

import { TablePagination } from '@material-ui/core';

import { AuthorsApi } from 'api';
import { AuthorDto } from 'api/types/books/authors';
import { usePagination } from 'utils/usePagination';
import AuthorsTable from './AuthorsTable';

const AuthorsList: FC = () => {

  const [authors, setAuthors] = useState<AuthorDto[]>([]);
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
      AuthorsApi.getList({ limit, page }).then(({ items, totalItems }) => {
        setAuthors(items);
        setTotalItems(totalItems);
      });
    },
    [setTotalItems, limit, page],
  );

  return (
    <>
      <AuthorsTable authors={authors} />
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

export default AuthorsList;
