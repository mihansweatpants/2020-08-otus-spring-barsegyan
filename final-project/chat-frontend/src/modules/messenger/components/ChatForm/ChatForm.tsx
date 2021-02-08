import React, { FC, useState } from 'react';

import { TextField, DialogActions, Button } from '@material-ui/core';

import SelectUsers from './SelectUsers';

import { UserDto } from 'api/types/users';
import { ChatFormValues } from './types';

import { useStyles } from './styles';

interface Props {
  values: Partial<ChatFormValues>;
  onSubmit: (values: ChatFormValues) => void;
  onCancel?: () => void;
}

const ChatForm: FC<Props> = ({
  values,
  onSubmit,
  onCancel,
}) => {
  const styles = useStyles();
  
  const [formValues, setFormValues] = useState(values);

  const handleChangeName = ({ target: { value } }: React.ChangeEvent<HTMLInputElement>) => {
    setFormValues(values => ({ ...values, chatName: value }));
  };

  const handleChangeMembers = (selectedMembers: UserDto[]) => {
    setFormValues(values => ({ ...values, members: selectedMembers }));
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    onSubmit(formValues as ChatFormValues);
  };

  return (
    <form onSubmit={handleSubmit} className={styles.formRoot}>
      <TextField
        value={formValues.chatName}
        onChange={handleChangeName}
        fullWidth
        label="Chat name"
        variant="outlined"
      />

      <div className={styles.selectMembers}>
        <SelectUsers
          value={formValues.members ?? []}
          onChange={handleChangeMembers}
        />
      </div>

      <DialogActions className={styles.submitActions}>
        {
          onCancel != null && (
            <Button onClick={onCancel} type="button">
              Cancel
            </Button>
          )
        }

        <Button
          type="submit"
          color="primary"
          variant="contained"
        >
          Create
        </Button>
      </DialogActions>
    </form>
  );
};

export default ChatForm;
