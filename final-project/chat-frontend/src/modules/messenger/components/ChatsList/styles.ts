import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  listItem: {
    padding: theme.spacing(1.5),
    borderTopLeftRadius: theme.shape.borderRadius * 2,
  },

  chatAvatar: {
    width: '50px',
    height: '50px',
  },

  listItemAvatar: {
    minWidth: '65px',
  },

  chatPreview: {
    width: '100%',
  },

  chatPreviewHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    marginBottom: theme.spacing(0.5),
  },

  chatName: {
    fontWeight: theme.typography.fontWeightMedium,
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    maxWidth: '160px',
  },

  lastUpdateTime: {
    color: theme.palette.grey[400],
    fontSize: '14px'
  },

  lastMessageSentBy: {
    fontSize: '14px'
  },

  lastMessageTextPreview: {
    fontSize: '14px',
  },

  chatPreviewContent: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    maxWidth: '160px',
  },
}));
