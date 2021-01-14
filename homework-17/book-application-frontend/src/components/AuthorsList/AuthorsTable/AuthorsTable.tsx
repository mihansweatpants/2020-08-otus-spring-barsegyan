import React, { FC } from 'react';

import { Table, TableHead, TableRow, TableBody, TableCell } from '@material-ui/core';

import { AuthorDto } from 'api/types/books/authors';

interface Props {
  authors: AuthorDto[];
}

const AuthorsTable: FC<Props> = ({
  authors,
}) => {
  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Имя</TableCell>
        </TableRow>
      </TableHead>

      <TableBody>
        {
          authors.map(({ id, name }) => (
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

export default AuthorsTable;
