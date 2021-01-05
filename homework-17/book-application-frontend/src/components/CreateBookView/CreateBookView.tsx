import React, { FC, useState } from 'react';
import { useHistory } from 'react-router-dom';

import { Typography, Grid, TextField, Button, Paper } from '@material-ui/core';

import { SelectGenres, SelectAuthor } from 'components/Selects';
import { BooksApi } from 'api';
import { GenreDto } from 'api/types/books/genres';
import { AuthorDto } from 'api/types/books/authors';

import { useStyles } from './styles';

const CreateBookView: FC = () => {
  const styles = useStyles();

  const history = useHistory();

  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState<AuthorDto | null>(null);
  const [genres, setGenres] = useState<GenreDto[]>([]);

  const isValid = title && author && genres.length;

  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async () => {
    setIsSubmitting(true);

    try {
      const { id } = await BooksApi.createBook({
        title,
        authorId: author!.id,
        genreIds: genres.map(({ id }) => id),
      });
  
      history.push(`/books/${id}`);
    }
    finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className={styles.root}>
      <Paper className={styles.paper}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Typography variant="h3" gutterBottom>Новая книга</Typography>
          </Grid>

          <Grid item xs={12}>
            <TextField
              value={title}
              onChange={({ target: { value } }) => setTitle(value)}
              label="Название"
              fullWidth
              variant="outlined"
            />
          </Grid>

          <Grid item xs={6}>
            <SelectAuthor
              value={author}
              onChange={({ target: { value } }) => setAuthor(value)}
            />
          </Grid>

          <Grid item xs={6}>
            <SelectGenres
              value={genres}
              onChange={({ target: { value } }) => setGenres(value)}
            />
          </Grid>

          <Grid item xs={12}>
            <Button
              variant="contained"
              color="primary"
              disabled={!isValid || isSubmitting}
              onClick={handleSubmit}
            >
              Добавить книгу
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </div>
  );
};

export default CreateBookView;
