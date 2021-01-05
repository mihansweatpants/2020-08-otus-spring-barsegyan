import React, { FC } from 'react';
import { useHistory } from 'react-router-dom';

import { Table, TableHead, TableRow, TableBody, TableCell } from '@material-ui/core';

import { BookDto } from 'api/types/books/books';

import { useStyles } from './styles';

interface Props {
  books: BookDto[];
}

const BooksTable: FC<Props> = ({
  books,
}) => {
  const styles = useStyles();

  const history = useHistory();

  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Название</TableCell>
          <TableCell>Автор</TableCell>
          <TableCell>Жанры</TableCell>
        </TableRow>
      </TableHead>

      <TableBody>
        {
          books.map(({ id, title, author, genres }) => (
            <TableRow hover key={id} onClick={() => history.push(`/books/${id}`)} className={styles.row}>
              <TableCell>{id}</TableCell>
              <TableCell>{title}</TableCell>
              <TableCell>{author.name}</TableCell>
              <TableCell>{genres.map(({ name }) => name).join(', ')}</TableCell>
            </TableRow>
          ))
        }
      </TableBody>
    </Table>
  );
};

export default BooksTable;
