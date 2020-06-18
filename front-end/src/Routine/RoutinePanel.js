import React from 'react'
import GenerateScriptInput from './GenerateScriptInput'
import { Button, Grid, Paper, TableContainer, Card, CardHeader, CardContent,
  LinearProgress, Dialog, DialogContent, DialogTitle, DialogActions,
  DialogContentText, Divider } from '@material-ui/core';

import SaveIcon from '@material-ui/icons/Save';
import RoutineExpansionPanel from './RoutineExpansionPanel'
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

    handleRadioClick = (selectedRoutineId) => {
      this.setState({selectedRoutineId: selectedRoutineId})
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
                  <RoutineExpansionPanel
                    routines={this.props.routines}
                    selectedRoutineId={this.state.selectedRoutineId}
                    onRadioClick={this.handleRadioClick} />
                </TableContainer>
              </CardContent>
          </Card>
        </Grid>
      );
    }
  }

  export default RoutinePanel
