import React, { FC, useEffect, useState } from 'react';

import { TablePagination } from '@material-ui/core';

import { GenresApi } from 'api';
import { GenreDto } from 'api/types/books/genres';
import { usePagination } from 'utils/usePagination';
import GenresTable from './GenresTable';

const GenresList: FC = () => {

  const [genres, setGenres] = useState<GenreDto[]>([]);
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
      GenresApi.getList({ limit, page }).then(({ items, totalItems }) => {
        setGenres(items);
        setTotalItems(totalItems);
      });
    },
    [setTotalItems, limit, page],
  );

  return (
    <>
      <GenresTable genres={genres} />
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

export default GenresList;
