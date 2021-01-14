
import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  title: {
    flexGrow: 1,
  },

  accountIcon: {
    marginLeft: theme.spacing(0.5),
  },

  user: {
    display: 'flex',
    alignItems: 'center',

    '& > :last-child': {
      marginLeft: theme.spacing(4),
    },
  },
}));
