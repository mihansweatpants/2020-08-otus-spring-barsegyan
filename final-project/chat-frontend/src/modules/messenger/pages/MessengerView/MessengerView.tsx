import React, { FC, useEffect } from 'react';

import { Paper } from '@material-ui/core';

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
        Chats list
      </div>

      <div className={styles.chatContents}>
        Chat contents will be displayed here
      </div>
    </Paper>
  );
};

export default MessengerView;
