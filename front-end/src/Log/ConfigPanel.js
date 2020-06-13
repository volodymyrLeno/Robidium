import React, { Component } from 'react'
import { TextField, Checkbox, FormControlLabel, Grid, Select, MenuItem,
  InputLabel, Button, Dialog, DialogTitle, DialogActions, DialogContent,
  FormControl, FormGroup } from '@material-ui/core'

  class ConfigPanel extends Component {
    constructor(props) {
      super(props)
      this.state = {
        algorithm: "CloFast",
        metric: "cohesion",
        openContextDialog: false,
        attributes: [],
      }
    }

    handleAlgorithm = (event) => {
      this.setState({algorithm: event.target.value})
      this.props.setAlgorithm(event.target.value)
    }

    handleMetric = (event) => {
      this.setState({metric: event.target.value})
      this.props.setMetric(event.target.value)
    }

    handleAttributeCheck = (event) => {
      var arr = []
      if (event.target.checked) {
        arr = this.state.attributes.concat(event.target.name)
      } else {
        arr = this.state.attributes.filter(attr => attr !== event.target.name)
      }
      this.setState({attributes: arr})
      this.props.handleContextAttributes(arr)
    }

    renderFormControlLabels = () => {
      return this.props.contextAttributes.map((attribute, index) => {
        return(
          <FormControlLabel
            control={
              <Checkbox
                checked={this.state.attributes.includes(attribute)}
                onChange={this.handleAttributeCheck}
                name={attribute} />
            }
            label={attribute}
            />
        )
      })
    }

    render() {
      return (
        <Grid container alignItems="center" justify="center" spacing={6}>
          <Grid
            container
            item
            spacing={10}
            xs={12}>
            <Grid item xs={4}>
              <TextField
                onChange={(event) => this.props.setMinSup(event.target.value)}
                id="min-sup"
                label="Minimum Support" />
            </Grid>
            <Grid item>
              <TextField
                onChange={(event) => this.props.setMinCov(event.target.value)}
                id="min-cov"
                label="Minimum Coverage" />
            </Grid>
          </Grid>
          <Grid
            container
            item
            spacing={10}
            xs={12}>
            <Grid item xs={4}>
              <FormControl>
                <InputLabel id="demo-simple-select-label">Algorithm</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={this.state.algorithm}
                  onChange={this.handleAlgorithm}
                  >
                  <MenuItem value="CloFast">CloFast</MenuItem>
                  <MenuItem value="BIDE+">BIDE+</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item>
              <FormControl>
                <InputLabel id="demo-simple-select-label">Metric</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={this.state.metric}
                  onChange={this.handleMetric}
                  >
                  <MenuItem value="cohesion">Cohesion</MenuItem>
                  <MenuItem value="frequency">Frequency</MenuItem>
                  <MenuItem value="coverage">Coverage</MenuItem>
                  <MenuItem value="length">Length</MenuItem>
                </Select>
              </FormControl>
            </Grid>
          </Grid>
          <Grid
            container
            item
            alignItems="center"
            spacing={10}
            xs={12}>
            <Grid item xs={4}>
              <FormControlLabel
                control={
                  <Checkbox
                    defaultChecked
                    onChange={(event) => this.props.setSegmented(event.target.checked)}
                    name="segmented"
                    color="primary"
                    />
                }
                label="Segmented"
                />
            </Grid>
            <Grid item>
              <Button
                style={{textAlign: 'left'}}
                aria-controls="simple-menu"
                aria-haspopup="true"
                color="primary"
                onClick={() => this.setState({openContextDialog: true})}>
                Select context attributes
              </Button>
              <Dialog
                disableBackdropClick
                disableEscapeKeyDown
                maxWidth="lg"
                aria-labelledby="confirmation-dialog-title"
                open={this.state.openContextDialog}
                >
                <DialogTitle id="confirmation-dialog-title">
                  Select context attributes
                </DialogTitle>
                <DialogContent dividers>
                  <FormControl component="fieldset" spacing={3}>
                    <FormGroup>
                      {this.renderFormControlLabels()}
                    </FormGroup>
                  </FormControl>
                </DialogContent>
                <DialogActions>
                  <Button
                    autoFocus
                    onClick={() => this.setState({openContextDialog: false})}
                    color="primary">
                    Ok
                  </Button>
                </DialogActions>
              </Dialog>
            </Grid>
          </Grid>
        </Grid>
      );
    }
  }

  export default ConfigPanel;
