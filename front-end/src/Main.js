import React from 'react'
import LogUpload from './Log/upload/LogUpload'
import RoutinePanel from './Routine/RoutinePanel'
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';

import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  content: {
    backgroundColor: theme.palette.background.paper,
    padding: theme.spacing(6, 0, 4),
  },
});

class Main extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      routines: []
    };
  }

  handleRoutines = (routines) => {
    this.setState({routines: routines})
  }

  render () {
    const { classes } = this.props;
    return (
      <div className={classes.content}>
        <Container maxWidth="lg">
          <Grid container spacing={5} direction={ 'column' }>
            <Grid item xs>
            <Container maxWidth="lg">
              <LogUpload setRoutines={this.handleRoutines}/>
            </Container>
            </Grid>
            <Grid item xs>
            <Container maxWidth="lg">
              <RoutinePanel routines={this.state.routines}/>
            </Container>
            </Grid>
          </Grid>
        </Container>
      </div>
    )
  }
}

export default withStyles(styles)(Main);
