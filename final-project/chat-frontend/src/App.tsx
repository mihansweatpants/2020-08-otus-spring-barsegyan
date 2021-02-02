import React from 'react';
import { Provider } from 'react-redux';
import { ThemeProvider, createMuiTheme, CssBaseline, Container } from '@material-ui/core';

import { Router, SpinnerView } from 'containers';
import { createStore } from 'store';
import { preloadState } from 'store/preloadState';

import './index.css';

function App() {
  return (
    <ThemeProvider theme={createMuiTheme()}>
      <CssBaseline />
      <Container maxWidth="lg">
        <SpinnerView resolve={preloadState} delay={500}>
          {(preloadedState) => (
            <Provider store={createStore(preloadedState)}>
              <Router />
            </Provider>
          )}
        </SpinnerView>
      </Container>
    </ThemeProvider>
  );
}

export default App;
