import React, { FC, useEffect, useState, useCallback } from 'react';
import { useParams, useHistory } from 'react-router-dom';

import { Paper, Typography, Grid, Chip, Divider, TextField, Button } from '@material-ui/core';
import { PersonOutline as PersonIcon, ArrowBackIos as ArrowBackIcon } from '@material-ui/icons';

import { BooksApi, BookReviewsApi } from 'api';
import { BookDto } from 'api/types/books/books';
import { BookReviewDto } from 'api/types/books/bookReviews';

import { useStyles } from './styles';
import { usePagination } from 'utils/usePagination';
import dateFormatter from 'utils/dateFormatter';

const BookView: FC = () => {
  const styles = useStyles();

  const history = useHistory();
  const { bookId } = useParams<{ bookId: string }>();

  const [book, setBook] = useState<BookDto | null>(null);
  const [isBookLoading, setIsBookLoading] = useState(false);

  const fetchBook = useCallback(
    async () => {
      setIsBookLoading(true);
      const book = await BooksApi.getById(+bookId);
      setBook(book);
      setIsBookLoading(false);
    },
    [bookId],
  );

  useEffect(
    () => {
      fetchBook();
    },
    [fetchBook],
  );

  const [reviews, setReviews] = useState<BookReviewDto[]>([]);
  const [isReviewsLoading, setIsReviewsLoading] = useState(false);
  const {
    page: reviewsPage = 0,
    limit: reviewsLimit,
    setLimit: setReviewsLimit,
    totalItems: totalReviews,
    setTotalItems: setTotalReviews
  } = usePagination();

  const fetchReviews = useCallback(
    async () => {
      setIsReviewsLoading(true);

      const { items, totalItems } = await BookReviewsApi.getByBookId(+bookId, {
        limit: reviewsLimit,
        page: reviewsPage,
      });

      setReviews(items);
      setTotalReviews(totalItems);
      setIsReviewsLoading(false);
    },
    [bookId, reviewsLimit, reviewsPage, setTotalReviews],
  );

  useEffect(
    () => {
      fetchReviews();
    },
    [fetchReviews],
  );

  const [reviewText, setReviewText] = useState('');

  const handleAddReview = async () => {
    await BookReviewsApi.addReview(+bookId, reviewText);
    setReviewText('');
    fetchReviews();
  };

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Button onClick={() => history.push('/')}>
          <ArrowBackIcon fontSize="small" className={styles.arrowBackIcon} /> В список
        </Button>
      </Grid>

      <Grid item xs={6}>
        <Paper className={styles.paper}>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <Typography variant="h4">Книга "{book?.title}"</Typography>
            </Grid>

            <Grid item xs={12}>
              <Typography className={styles.author}>
                <PersonIcon className={styles.authorIcon} /> {book?.author.name}
              </Typography>
            </Grid>

            <Grid item xs={12}>
              {
                book?.genres.map(({ id, name }) => (
                  <Chip key={id} label={name} className={styles.chip} />
                ))
              }
            </Grid>
          </Grid>
        </Paper>
      </Grid>

      <Grid item xs={6}>
        <Paper className={styles.paper}>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <Typography variant="h4">
                Рецензии
              </Typography>
            </Grid>

            <Grid item xs={12}>
              <div className={styles.addReview}>
                <TextField
                  value={reviewText}
                  onChange={({ target: { value } }) => setReviewText(value)}
                  fullWidth
                  variant="outlined"
                  margin="dense"
                  placeholder="Что вы думаете об этой книге?"
                  className={styles.reviewInput}
                />

                <Button
                  disabled={!reviewText}
                  onClick={handleAddReview}
                  className={styles.addReviewButton}
                  variant="contained"
                  color="primary"
                >
                  Добавить
                </Button>
              </div>
            </Grid>

            <Grid item xs={12}>
              {
                reviews.map(({ id, text, createdAt }, index, array) => (
                  <>
                    <div key={id} className={styles.review}>
                      <Typography>
                        {text}
                      </Typography>

                      <Typography variant="caption">
                        {dateFormatter.format(new Date(createdAt))}
                      </Typography>
                    </div>

                    {
                      index !== array.length - 1 && (
                        <Divider />
                      )
                    }
                  </>
                ))
              }
            </Grid>

            {
              reviews.length !== totalReviews && (
                <Grid item xs={12}>
                  <Button
                    onClick={() => setReviewsLimit(limit => limit + 10)}
                    variant="contained"
                    fullWidth
                  >
                    Загрузить еще
                  </Button>
                </Grid>
              )
            }
          </Grid>
        </Paper>
      </Grid>
    </Grid>
  );
};

export default BookView;
