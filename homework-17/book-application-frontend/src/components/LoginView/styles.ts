
import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  root: {
    height: '100vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },

  paper: {
    padding: theme.spacing(3),
    display: 'flex',
    flexDirection: 'column',
    minWidth: '400px',
    '& > :not(:first-child)': {
      marginTop: theme.spacing(3),
    },
  },
}));
