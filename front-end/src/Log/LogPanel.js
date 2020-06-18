import React from 'react'
import UploadLogInput from './UploadLogInput'
import ConfigPanel from './ConfigPanel'
import IndentifyRoutinesButton from './IdentifyRoutinesButton'
import {Card, CardHeader, CardContent, Box, Badge, Typography } from '@material-ui/core'
import { LinearProgress, Grid, Collapse, Divider} from '@material-ui/core'
import InsertDriveFileOutlinedIcon from '@material-ui/icons/InsertDriveFileOutlined'
import { withStyles } from '@material-ui/core/styles'

const StyledBadge = withStyles((theme) => ({
  badge: {
    backgroundColor: '#44b700',
    color: '#44b700',
    boxShadow: `0 0 0 2px ${theme.palette.background.paper}`,
    '&::after': {
      position: 'absolute',
      top: 0,
      left: 0,
      width: '100%',
      height: '100%',
      borderRadius: '50%',
      animation: '$ripple 1.2s infinite ease-in-out',
      border: '1px solid currentColor',
      content: '""',
    },
  },
  '@keyframes ripple': {
    '0%': {
      transform: 'scale(.8)',
      opacity: 1,
    },
    '100%': {
      transform: 'scale(2.4)',
      opacity: 0,
    },
  },
}))(Badge);


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
              <Box
                display="flex"
                flexDirection="column"
                alignItems="center">
                <UploadLogInput
                  onLoading={this.handleLogFileLoading}
                  onResponse={this.handleLogResponse}
                  disabled={this.state.loading}
                  />
                {this.state.file != null &&
                  <Box display="flex" alignItems="center">
                    <StyledBadge
                      overlap="circle"
                      anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'right',
                      }}
                      variant="dot"
                      >
                      <InsertDriveFileOutlinedIcon />
                    </StyledBadge>
                    <Box ml={1}>
                      <Typography variant="caption">
                        {this.state.file.name}
                      </Typography>
                    </Box>
                  </Box>
                }
              </Box>
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
