import React, { FC } from 'react';

import { List, ListItem, ListItemAvatar, Avatar, ListItemText, Typography } from '@material-ui/core';

import { useSelector } from 'store';

import { formatToShortTime } from 'utils/date';
import { stringToHexColor } from 'utils/colors';

import { useStyles } from './styles';

const MessengerView: FC = () => {
  const styles = useStyles();

  const chatsList = useSelector(state => state.chats.chatsList);

  return (
    <List disablePadding>
      {
        chatsList.map(chat => (
          <ListItem key={chat.id} button className={styles.listItem}>
            <ListItemAvatar className={styles.listItemAvatar}>
              <Avatar className={styles.chatAvatar} style={{ backgroundColor: stringToHexColor(chat.name.slice(0, 5)) }}>
                {chat.name[0].toUpperCase()}
              </Avatar>
            </ListItemAvatar>

            <div className={styles.chatPreview}>
              <div className={styles.chatPreviewHeader}>
                <Typography className={styles.chatName}>
                  {chat.name}
                </Typography>

                {
                  chat.lastMessage?.sentAt != null && (
                    <Typography className={styles.lastUpdateTime}>
                      {formatToShortTime(chat.lastMessage.sentAt)}
                    </Typography>
                  )
                }
              </div>

              <div className={styles.chatPreviewContent}>
                <Typography display="inline" color="primary" className={styles.lastMessageSentBy}>
                  {chat.lastMessage?.sentBy.username}:
                </Typography>
                {' '}
                <Typography display="inline" className={styles.lastMessageTextPreview}>
                  {chat.lastMessage?.text}
                </Typography>
              </div>
            </div>
          </ListItem>
        ))
      }
    </List>
  );
};

export default MessengerView;
