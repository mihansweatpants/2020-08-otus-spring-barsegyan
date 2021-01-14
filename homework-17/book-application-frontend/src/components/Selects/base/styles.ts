import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  spinner: {
    backgroundColor: 'transparent !important',
    pointerEvents: 'none',
    display: 'flex',
    justifyContent: 'center',
  },

  menuItem: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'flex-start',
    justifyContent: 'center',
  },

  optionDescription: {
    fontSize: '13px',
    color: theme.palette.grey[500],
  },

  emptyMessage: {
    padding: theme.spacing(1),
  },
}));
