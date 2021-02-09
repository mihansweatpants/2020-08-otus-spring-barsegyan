import React, { FC } from 'react';

import { Avatar, Typography, CircularProgress } from '@material-ui/core';

import { useSelector } from 'store';

import { stringToHexColor } from 'utils/colors';
import { formatToShortTime } from 'utils/date';

import { useStyles } from './styles';

const ChatMessagesList: FC = () => {
  const styles = useStyles();

  const { messagesList, isLoadingList } = useSelector(state => state.messages);

  return (
    <div className={styles.root}>
      {
        isLoadingList && (
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
        !isLoadingList && messagesList.totalItems > 0 && messagesList.items.map(message => (
          <div key={message.id} className={styles.message}>
            <Avatar className={styles.userAvatar} style={{ backgroundColor: stringToHexColor(message.sentBy.username) }}>
              {message.sentBy.username[0].toUpperCase()}
            </Avatar>

            <div className={styles.stretch}>
              <div className={styles.messageHeader}>
                <Typography className={styles.username} style={{ color: stringToHexColor(message.sentBy.username) }}>
                  {message.sentBy.username}
                </Typography>

                <Typography className={styles.sentAtTime}>
                  {formatToShortTime(message.sentAt)}
                </Typography>
              </div>

              <div className={styles.messageContent}>
                <Typography className={styles.messageText}>
                  {message.text}
                </Typography>
              </div>
            </div>
          </div>
        ))
      }
    </div>
  );
};

export default ChatMessagesList;
