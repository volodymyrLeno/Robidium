import React from 'react'
import GenerateScriptInput from './GenerateScriptInput'
import { Button, Grid, Paper, TableContainer, Card, CardHeader, CardContent,
  Table, TableBody, TableCell, TableRow, ExpansionPanel, ExpansionPanelSummary,
  ExpansionPanelDetails, Radio, FormControlLabel, LinearProgress, Dialog, Box,
  DialogContent, DialogTitle, DialogActions, DialogContentText, Typography,
  Divider, Fab} from '@material-ui/core';
  import axios from 'axios';
  import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
  import SaveIcon from '@material-ui/icons/Save';

  const FileDownload = require('js-file-download');

  class RoutinePanel extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        selectedRoutineId: "",
        scriptLoad: false,
        openDialog: false,
        script: null,
      };
    }

    renderRoutineTable = (routine) => {
      return (
        <TableContainer>
          <Table size="small">
            <TableBody>
              {routine.items.map((item, itemIdx) => {
                return (
                  <TableRow>
                    <TableCell align="left">
                      {itemIdx + 1}
                    </TableCell>
                    {item.value.split("+").map(value => {
                      return (
                        <TableCell align="left">
                          <Typography variant="body2" noWrap style={{maxWidth: 200,}}>
                            {value}
                          </Typography>
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

    handleClose = () => {
      this.setState({openDialog: false})
    }

    handleScriptGeneration = (data) => {
      this.setState({
        scriptLoad: data.scriptLoad,
        script: data.script,
        openDialog: data.openDialog
      })
    }

    render () {
      return (
        this.props.routines.length > 0 &&
        <Grid item justifyContent="center">
          <LinearProgress
            id="script-progress"
            style={{visibility: this.state.scriptLoad ? 'visible' : 'hidden'}}
            />
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
                  onClick={() => {
                    FileDownload(this.state.script.data, 'script_' +
                      this.state.script.config.params.patternId + '.xaml')
                    this.handleClose()
                  }}
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
                <GenerateScriptInput
                  onClick={this.handleScriptGeneration}
                  selectedRoutineId={this.state.selectedRoutineId}/>
              }
              title="Routine selection">
            </CardHeader>
            <Divider variant="middle" />
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

  export default RoutinePanel
