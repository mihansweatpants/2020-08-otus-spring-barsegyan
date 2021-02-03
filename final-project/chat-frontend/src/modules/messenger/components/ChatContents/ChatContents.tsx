import React, { FC } from 'react';

import { Typography, CircularProgress } from '@material-ui/core';

import { ChatHeader, ChatMessagesList, PostMessageForm } from 'modules/messenger/components';

import { useSelector } from 'store';

import { useStyles } from './styles';

const ChatContents: FC = () => {
  const styles = useStyles();

  const { chatsList, selectedChat, isLoading } = useSelector(state => state.chats);

  if (!selectedChat) {
    return (
      <div className={styles.empty}>
        <Typography variant="h6" color="textSecondary">
          Select a chat to start messaging
        </Typography>
      </div>
    )
  }

  if (chatsList.length === 0 && !isLoading) {
    return (
      <div className={styles.empty}>
        <Typography variant="h6" color="textSecondary">
          No chats yeat. Create one (TODO)
        </Typography>
      </div>
    )
  }

  if (selectedChat && isLoading) {
    return (
      <div className={styles.empty}>
        <CircularProgress />
      </div>
    )
  }

  return (
    <div className={styles.root}>
      <div className={styles.chatHeader}><ChatHeader /></div>
      <div className={styles.messagesList}><ChatMessagesList /></div>
      <div className={styles.postMessage}><PostMessageForm /></div>
    </div>
  );
};

export default ChatContents;
