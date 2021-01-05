import React, { FC } from 'react';

import { Container } from '@material-ui/core';

import { Header } from 'components';

import { useStyles } from './styles';

const Layout: FC = ({ children }) => {
  const styles = useStyles();

  return (
    <>
      <Header />

      <Container maxWidth="lg" className={styles.container}>
        <>
          {children}
        </>
      </Container>
    </>
  );
};

export default Layout;
