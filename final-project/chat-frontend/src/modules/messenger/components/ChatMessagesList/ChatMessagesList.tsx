import React, { FC } from 'react';

import { Typography, CircularProgress } from '@material-ui/core';
import MessagesList from './MessagesList';

import { useSelector } from 'store';

import { useStyles } from './styles';

const ChatMessagesList: FC = () => {
  const styles = useStyles();

  const { messagesList, isLoadingList } = useSelector(state => state.messages);

  return (
    <div className={styles.root}>
      {
        isLoadingList && messagesList.totalItems === 0 && (
          <div className={styles.empty}>
            <CircularProgress />
          </div>
        )
      }

      {
        !isLoadingList && messagesList.totalItems === 0 && (
          <div className={styles.empty}>
            <Typography variant="h6" color="textSecondary">
              No messages yet
            </Typography>
          </div>
        )
      }

      {
        !isLoadingList && messagesList.totalItems > 0 && (
          <MessagesList />
        )
      }
    </div>
  );
};

export default ChatMessagesList;
