import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  loading: {
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
  },

  list: {
  },

  session: {
    display: 'flex',
    margin: theme.spacing(2, 0),
  },

  sessionDetails: {},

  otherSessionsTitle: {
    marginTop: theme.spacing(4),
  },

  revoke: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
    alignItems: 'flex-end',
  },

  revokeButton: {
    marginRight: -theme.spacing(1),
  },

  lastAccessTime: {
    color: theme.palette.grey[400],
    fontSize: '14px'
  },

  userAgent: {
    fontWeight: theme.typography.fontWeightMedium,
  },

  remoteAddr: {},
}));
