import React, { FC } from 'react';
import { useHistory } from 'react-router-dom';

import { Typography, Button } from '@material-ui/core';

import { useStyles } from './styles';

const NotFound: FC = () => {
  const styles = useStyles();
  const history = useHistory();

  return (
    <div className={styles.container}>
      <Typography variant="h3" gutterBottom align="center">
        Страница не найдена
      </Typography>

      <Button className={styles.button} onClick={() => history.push('/')} color="primary">
        На главную
      </Button>
    </div>
  );
};

export default NotFound;
