import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  rootPaper: {
    height: '100%',
    display: 'flex',
    borderRadius: theme.shape.borderRadius * 2,
  },

  chatsList: {
    flexBasis: '30%',
    borderRight: `1px solid ${theme.palette.grey[200]}`
  },

  chatContents: {
    flexBasis: '70%',
  },
}));
