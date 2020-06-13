import React from 'react'
import LogPanel from './Log/LogPanel'
import RoutinePanel from './Routine/RoutinePanel'
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  content: {
    padding: theme.spacing(5, 0),
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
              <LogPanel onRoutinesReceived={this.handleRoutines}/>
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
