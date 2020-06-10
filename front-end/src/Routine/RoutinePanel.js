import React from 'react'

import { Button, Grid, Paper, TableContainer} from '@material-ui/core';
import axios from 'axios';

import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';

import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Radio from '@material-ui/core/Radio';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import LinearProgress from '@material-ui/core/LinearProgress';

import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import Dialog from '@material-ui/core/Dialog';

import SaveIcon from '@material-ui/icons/Save';

const FileDownload = require('js-file-download');

class RoutinePanel extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      selectedRoutineId: "",
      load: false,
      openDialog: false,
      script: null,
    };
  }

  renderRoutineTable = (routine) => {
    return (
      <TableContainer
        component={Paper}
        >

        <Table size="small">

          <TableBody>

            {routine.items.map((item, itemIdx) => {
              return (
                <TableRow>

                  <TableCell align="right">

                    {itemIdx + 1}
                  </TableCell>

                  {item.value.split("+").map(value => {
                    return (
                      <TableCell align="right">

                        {value}
                      </TableCell>
                    )
                  })}
                </TableRow>
              )
            })}
          </TableBody>

        </Table>

      </TableContainer>
    )
  }

  renderExpansionPanels = () => {
    return this.props.routines.map((routine, index) => {
      return (
        <ExpansionPanel>

          <ExpansionPanelSummary
            expandIcon={
              <ExpandMoreIcon />
            }
            aria-label="Expand"
            >

            <FormControlLabel
              onClick={(event) => event.stopPropagation()}
              onFocus={(event) => event.stopPropagation()}
              control={
                <Radio
                  checked={this.state.selectedRoutineId === routine.id}
                  color="primary"
                  onChange={() => this.setState({selectedRoutineId: routine.id})}
                  />
              }
              label={"Routine " + (index + 1)}
              />

          </ExpansionPanelSummary>

          <ExpansionPanelDetails>

            {this.renderRoutineTable(routine)}
          </ExpansionPanelDetails>

        </ExpansionPanel>
      )
    })
  }

  sendGenerateScriptRequest = () => {
    this.setState({load: true})
    axios.post('http://localhost:8080/scripts', null, {params: {
      patternId: this.state.selectedRoutineId
    }})
    .then(response => {
      this.setState({script: response, load: false, openDialog: true})
    })
    .catch(err => {
      this.setState({load: false, openDialog: false})
    })
  }

  handleClose = () => {
    this.setState({openDialog: false})
  }

  render () {
    return (
      this.props.routines.length > 0 &&
      <Grid item justifyContent="center">

        {this.state.load &&
          <LinearProgress id="script-progress"/>
        }
        <Dialog
          onClose={this.handleClose}
          aria-labelledby="save-script-dialog-title"
          open={this.state.openDialog}>

          <DialogTitle id="save-script-dialog-title">
            Save Script
          </DialogTitle>

          <DialogContent>

            <DialogContentText>
              To save a UIPath script, please press "Save" button and select a directory.
            </DialogContentText>

            <DialogActions>

              <Button
                onClick={this.handleClose}
                color="primary">
                Cancel
              </Button>

              <Button
                onClick={() => {FileDownload(this.state.script.data, 'script.xaml');; this.handleClose();}}
                color="primary"
                startIcon={
                  <SaveIcon />
                }
                >
                Save
              </Button>

            </DialogActions>

          </DialogContent>

        </Dialog>

        <Card>

          <CardHeader
            action={
              <Button
                variant="outlined"
                color="primary"
                onClick={this.sendGenerateScriptRequest}>
                Generate Script
              </Button>
            }
            title="Routine selection">

          </CardHeader>

          <CardContent>

            <TableContainer component={Paper}>
              {this.renderExpansionPanels()}
            </TableContainer>

          </CardContent>

        </Card>

      </Grid>
    );
  }
}

export default RoutinePanel;
