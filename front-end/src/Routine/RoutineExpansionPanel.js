import React from 'react'
import { ExpansionPanel, ExpansionPanelSummary, ExpansionPanelDetails } from '@material-ui/core'
import { FormControlLabel, Radio } from '@material-ui/core'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
import RoutineTable from './RoutineTable'

class RoutineExpansionPanel extends React.Component {

  render () {
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
                  checked={this.props.selectedRoutineId === routine.id}
                  color="primary"
                  onChange={() => this.props.onRadioClick(routine.id)}
                  />
              }
              label={"Routine " + (index + 1)}
              />
          </ExpansionPanelSummary>
          <ExpansionPanelDetails>
            <RoutineTable routine={routine} />
          </ExpansionPanelDetails>
        </ExpansionPanel>
      )
    })
  }
}

export default RoutineExpansionPanel
