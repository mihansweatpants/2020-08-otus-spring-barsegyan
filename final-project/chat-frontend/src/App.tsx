import React from 'react';
import { ThemeProvider, createMuiTheme, CssBaseline, Container } from '@material-ui/core';

import './index.css';

function App() {
  return (
    <ThemeProvider theme={createMuiTheme()}>
      <CssBaseline />
      <Container maxWidth="lg">
      </Container>
    </ThemeProvider>
  );
}

export default App;
