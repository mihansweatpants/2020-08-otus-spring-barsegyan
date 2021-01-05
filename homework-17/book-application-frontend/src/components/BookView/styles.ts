import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  arrowBackIcon: {
    marginRight: theme.spacing(1),
  },

  paper: {
    padding: theme.spacing(3),
  },

  authorIcon: {
    marginRight: theme.spacing(1),
  },

  author: {
    display: 'flex',
    alignItems: 'center',
  },

  chip: {
    marginRight: theme.spacing(1.5),
    borderRadius: theme.shape.borderRadius,
  },

  addReview: {
    display: 'flex',
    alignItems: 'stretch',
  },

  reviewInput: {
    margin: 0,
  },

  addReviewButton: {
    marginLeft: theme.spacing(1),
  },

  review: {
    padding: theme.spacing(2, 0),
  },
}));
