import React, { FC, useEffect } from 'react';
import { first } from 'lodash';

import { Typography, CircularProgress } from '@material-ui/core';
import MessagesList from './MessagesList';

import { useSelector, useDispatch } from 'store';
import { markLastReadMessage } from 'store/messenger/readMarksSlice';

import { useStyles } from './styles';

const ChatMessagesList: FC = () => {
  const styles = useStyles();
  const dispatch = useDispatch();

  const { messagesList, isLoadingList } = useSelector(state => state.messages);

  useEffect(
    () => {
      if (messagesList.items.length > 0) {
        const lastReadMessage = first(messagesList.items)!;

        setTimeout(() => dispatch(markLastReadMessage(lastReadMessage.chatId, lastReadMessage.id)), 200);
      }
    },
    [messagesList, dispatch],
  );

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
