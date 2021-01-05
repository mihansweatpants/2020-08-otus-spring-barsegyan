import React, { FC } from 'react';

import { Table, TableHead, TableRow, TableBody, TableCell } from '@material-ui/core';

import { GenreDto } from 'api/types/books/genres';

interface Props {
  genres: GenreDto[];
}

const GenresTable: FC<Props> = ({
  genres,
}) => {
  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Название</TableCell>
        </TableRow>
      </TableHead>

      <TableBody>
        {
          genres.map(({ id, name }) => (
            <TableRow key={id}>
              <TableCell>{id}</TableCell>
              <TableCell>{name}</TableCell>
            </TableRow>
          ))
        }
      </TableBody>
    </Table>
  );
};

export default GenresTable;
