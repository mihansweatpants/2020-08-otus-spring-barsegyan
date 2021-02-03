import React, { FC, useEffect } from 'react';

import { Paper } from '@material-ui/core';

import { ChatsList } from 'modules/messenger/components';

import { useDispatch } from 'store';
import { fetchChats } from 'store/messenger/chatsSlice';

import { useStyles } from './styles';

const MessengerView: FC = () => {
  const styles = useStyles();
  const dispatch = useDispatch();

  useEffect(
    () => {
      dispatch(fetchChats());
    },
    [dispatch],
  );

  return (
    <Paper className={styles.rootPaper}>
      <div className={styles.chatsList}>
        <ChatsList />
      </div>

      <div className={styles.chatContents}>
      </div>
    </Paper>
  );
};

export default MessengerView;
