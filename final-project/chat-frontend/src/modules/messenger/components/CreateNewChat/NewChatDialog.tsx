import React, { FC } from 'react';

import {
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent,
  Button,
} from '@material-ui/core';

import { ChatForm } from 'modules/messenger/components';

import { useStyles } from './styles';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

const NewChatDialog: FC<Props> = ({
  isOpen,
  onClose
}) => {
  return (
    <Dialog
      open={isOpen}
      onClose={onClose}
      maxWidth="md"
    >
      <DialogTitle>
        Create new chat
      </DialogTitle>

      <DialogContent>
        <ChatForm values={{}} onSubmit={() => {}} />
      </DialogContent>
    </Dialog>
  )
};

export default NewChatDialog;
