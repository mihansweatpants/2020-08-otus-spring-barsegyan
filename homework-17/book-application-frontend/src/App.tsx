import React from 'react';
import { ThemeProvider, createMuiTheme, CssBaseline, Container } from '@material-ui/core';
import './index.css';

import { Router } from 'components';
import { AppContextProvider } from 'context/AppContext';

function App() {
  return (
    <AppContextProvider>
      <ThemeProvider theme={createMuiTheme()}>
        <CssBaseline />
        <Container maxWidth="lg">
          <Router />
        </Container>
      </ThemeProvider>
    </AppContextProvider>
  );
}

export default App;
