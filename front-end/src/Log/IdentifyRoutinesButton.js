import React from 'react'
import Button from '@material-ui/core/Button'
import axios from 'axios';

class IdentifyRoutinesButton extends React.Component {

  identifyRoutines = () => {
    this.props.onClick(true)

    let data = JSON.stringify({
      algorithm: this.props.params.algorithm,
      minSupport: this.props.params.minsup,
      logName: this.props.params.file.name,
      metric: this.props.params.metric,
      minCoverage: this.props.params.mincov,
      segmented: this.props.params.segmented,
      context: this.props.params.context,
    })

    let config = {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8'
      },
    }

    axios.post('http://localhost:8080/routines', data, config)
    .then(response => {
      this.props.onRoutinesReceived(response.data)
      this.props.onClick(false)
    })
    .catch(err => {
      this.props.onClick(false)
    })
  }

  render() {
    return(
      <Button
        color="primary"
        variant="contained"
        disabled={this.props.disabled}
        onClick={this.identifyRoutines}
        >
        Identify routines
      </Button>
    )
  }
}

export default IdentifyRoutinesButton;
