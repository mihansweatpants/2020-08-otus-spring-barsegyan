import React, { FC } from 'react';

import { IconButton } from '@material-ui/core';
import { ChatBubbleRounded as ChatBubbleRoundedIcon, SettingsRounded as SettingsRoundedIcon } from '@material-ui/icons';

import { MessengerTab } from './types';

import { useStyles } from './styles';

interface Props {
  selectedTab: MessengerTab;
  onChange: (value: MessengerTab) => void;
}

const MessengerTabs: FC<Props> = ({
  selectedTab,
  onChange
}) => {
  const styles = useStyles();

  return (
    <div className={styles.root}>
      <IconButton
        color={selectedTab === MessengerTab.Chats ? 'primary' : 'default'}
        onClick={() => onChange(MessengerTab.Chats)}
        className={styles.button}
      >
        <ChatBubbleRoundedIcon />
      </IconButton>

      <IconButton
        color={selectedTab === MessengerTab.Settings ? 'primary' : 'default'}
        onClick={() => onChange(MessengerTab.Settings)}
        className={styles.button}
      >
        <SettingsRoundedIcon />
      </IconButton>
    </div>
  );
};

export default MessengerTabs;
