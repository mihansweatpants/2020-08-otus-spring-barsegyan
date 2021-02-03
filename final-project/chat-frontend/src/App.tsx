import React from 'react';
import { Provider } from 'react-redux';
import { ThemeProvider, CssBaseline } from '@material-ui/core';

import { Router, SpinnerView } from 'containers';
import { createStore } from 'store';
import { preloadState } from 'store/preloadState';

import { theme } from './theme';
import './index.css';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
        <SpinnerView resolve={preloadState} delay={500}>
          {(preloadedState) => (
            <Provider store={createStore(preloadedState)}>
              <Router />
            </Provider>
          )}
        </SpinnerView>
    </ThemeProvider>
  );
}

export default App;
