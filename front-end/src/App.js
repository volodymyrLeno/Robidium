import React from 'react'
import Main from './Main'

import 'fontsource-roboto'
import { CssBaseline, AppBar, Toolbar, Avatar, Typography}  from '@material-ui/core';

const App = () => {
  return (
    <div>
      <CssBaseline />
      <AppBar color="primary" position="relative">
        <Toolbar>
          <Avatar alt="Logo" src="/logo.png"/>
          <Typography variant="h6">
            Robidium
          </Typography>
        </Toolbar>
      </AppBar>
      <Main />
    </div>
  )
}

export default App;
