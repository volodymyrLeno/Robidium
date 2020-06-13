import React from 'react'
import UploadLogInput from './UploadLogInput'
import ConfigPanel from './ConfigPanel'
import IndentifyRoutinesButton from './IdentifyRoutinesButton'
import {Card, CardHeader, CardContent, Box} from '@material-ui/core'
import {Button, LinearProgress, Grid, Collapse, Divider} from '@material-ui/core'

class LogPanel extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      file: null,
      minsup: null,
      mincov: null,
      algorithm: "CloFast",
      metric: "cohesion",
      segmented: true,
      successfullUploaded: false,
      loading: false,
      contextAttributes: [],
      selectedContextAttributes: [],
    };
  }

  handleLogFileLoading = (loadingStatus) => {
    this.setState({
      successfullUploaded: loadingStatus.successfullUploaded,
      loading: loadingStatus.loading,
    })
  }

  handleLogResponse = ({contextAttributes, file}) => {
    this.setState({
      contextAttributes: contextAttributes,
      file: file,
    })
  }

  render() {
    return (
      <div>
        <LinearProgress
          id="routine-progress"
          style={{visibility: this.state.loading ? 'visible' : 'hidden'}}
          />
        <Card>
          <CardHeader
            title="Log configuration"
            action={
              <UploadLogInput
                onLoading={this.handleLogFileLoading}
                onResponse={this.handleLogResponse}
                disabled={this.state.loading}
                />
            }>
          </CardHeader>
          <Divider variant="middle" />
          <Collapse in={this.state.successfullUploaded}>
            <CardContent>
              <ConfigPanel
                contextAttributes={this.state.contextAttributes}
                handleContextAttributes={(attributes) => this.setState({selectedContextAttributes: attributes})}
                setMinSup={(minSup) => this.setState({minsup: minSup})}
                setMinCov={(minCov) => this.setState({mincov: minCov})}
                setAlgorithm={(algorithmName) => this.setState({algorithm: algorithmName})}
                setMetric={(metric) => this.setState({metric: metric})}
                setSegmented={(isSegmented) => this.setState({segmented: isSegmented})}
                />
              <Grid
                container
                alignItems='flex-end'
                direction='column-reverse'>
                <IndentifyRoutinesButton
                  onRoutinesReceived={this.props.onRoutinesReceived}
                  params={{
                    algorithm: this.state.algorithm,
                    minSupport: this.state.minsup,
                    file: this.state.file,
                    metric: this.state.metric,
                    minCoverage: this.state.mincov,
                    segmented: this.state.segmented,
                    context: this.state.selectedContextAttributes,
                  }}
                  onClick={(status) => this.setState({loading: status})}
                  disabled={this.state.file == null || this.state.loading ||
                    this.state.minsup == null || this.state.mincov == null ||
                    this.state.selectedContextAttributes.length === 0}
                    />
                </Grid>
              </CardContent>
            </Collapse>
          </Card>
        </div>
      );
    }
  }

  export default LogPanel;
