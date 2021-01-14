import React, { FC, useState } from 'react';

import { Paper, TextField, Button, Typography } from '@material-ui/core';

import { AuthApi } from 'api';

import { useStyles } from './styles';

const LoginView: FC = () => {
  const styles = useStyles();

  const [{ username, password }, setFormValues] = useState({
    username: '',
    password: '',
  });

  const [isLoginFailed, setIsLoginFailed] = useState(false);

  const handleChange = ({ target: { name, value } }: React.ChangeEvent<HTMLInputElement>) => {
    setFormValues(values => ({ ...values, [name]: value }));
    if (isLoginFailed) setIsLoginFailed(false);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      await AuthApi.login({ username, password });
      window.location.href = "/";
    }
    catch (err) {
      setIsLoginFailed(true);
    }
  };

  return (
    <div className={styles.root}>
      <form onSubmit={handleSubmit}>
        <Paper className={styles.paper}>
          <Typography variant="h5" align="center">
            Пожалуйста, авторизуйтесь
          </Typography>

          <TextField
            label="Логин"
            variant="outlined"
            value={username}
            name="username"
            onChange={handleChange}
          />

          <TextField
            label="Пароль"
            variant="outlined"
            type="password"
            value={password}
            name="password"
            onChange={handleChange}
          />

          <Button
            variant="contained"
            color="primary"
            type="submit"
            disabled={!username || !password}
          >
            Войти
          </Button>

          {
            isLoginFailed && (
              <Typography color="error" align="center">
                Неправильный логин или пароль. Попробуйте еще раз.
              </Typography>
            )
          }
        </Paper>
      </form>
    </div>
  );
};

export default LoginView;
