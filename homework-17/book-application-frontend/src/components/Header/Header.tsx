import React, { FC } from 'react';

import { AppBar, Toolbar, Typography, Button } from '@material-ui/core';
import { Person as PersonIcon } from '@material-ui/icons';

import { AuthApi } from 'api';

import { useAppContext } from 'context/AppContext';

import { useStyles } from './styles';

const Header: FC = () => {
  const styles = useStyles();

  const { user, setUser } = useAppContext();

  const onLogout = async () => {
    try {
      await AuthApi.logout();
    }
    finally {
      setUser(null);
    }
  };

  return (
    <AppBar position="fixed">
      <Toolbar>
        <Typography variant="h6" className={styles.title}>
          Book application
        </Typography>

        <div className={styles.user}>
          <Typography variant="h6">
            {user?.username}
          </Typography>

          <PersonIcon className={styles.accountIcon} />

          <Button color="inherit" onClick={onLogout}>Выйти</Button>
        </div>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
