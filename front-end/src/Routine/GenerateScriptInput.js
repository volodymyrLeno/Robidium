import React from 'react'
import {Fab, Box} from '@material-ui/core'
import CodeIcon from '@material-ui/icons/Code'
import green from '@material-ui/core/colors/green';
import axios from 'axios'

class GenerateScriptInput extends React.Component {

  handleClick = () => {
    this.props.onClick({scriptLoad: true})

    axios.post('http://localhost:8080/scripts', null, {params: {
      patternId: this.props.selectedRoutineId
    }})
    .then(response => {
      this.props.onClick({script: response, scriptLoad: false, openDialog: true})
    })
    .catch(err => {
      this.props.onClick({scriptLoad: false, openDialog: false})
    })
  }

  render () {
    return(
      <Box display="flex" p={1} alignItems="center">
        <label htmlFor="script-upload">
          <input
            style={{ display: 'none' }}
            id="script-upload"
            onClick={this.handleClick}
            disabled={this.props.selectedRoutineId == ""}
            />
          <Fab variant="extended" component="span" disabled={this.props.selectedRoutineId == ""}>
            <Box display="flex">
              <Box display="flex" mr={1}>
                <CodeIcon style={{ color: green[500] }} />
              </Box>
              Generate Script
            </Box>
          </Fab>
        </label>
      </Box>
    )
  }
}

export default GenerateScriptInput;
