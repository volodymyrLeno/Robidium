import React, { Component } from 'react'
import Dropzone from '../dropzone/Dropzone'
import ConfigParams from '../params/ConfigParams'

import Button from '@material-ui/core/Button'
import LinearProgress from '@material-ui/core/LinearProgress';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';

import Grow from '@material-ui/core/Grow';

import axios from 'axios';

class LogUpload extends Component {
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

  uploadLog = (file) => {
    axios.interceptors.request.use(config => {
      this.setState({loading: true})
      return config;
    }, error => {
      this.setState({loading: false})
      return Promise.reject(error);
    });

    const formData = new FormData();
    formData.append("file", file, file.name);

    let config = {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
    }

    axios.post('http://localhost:8080/logs', formData, config)
    .then(response => {
      this.setState({
        contextAttributes: response.data,
        file: file,
        successfullUploaded: true,
        loading: false
      })
    })
    .catch(err => {
      this.setState({successfullUploaded: false, loading: false})
    })
  }

  identifyRoutines = () => {
    axios.interceptors.request.use(config => {
      this.setState({loading: true})
      return config;
    }, error => {
      this.setState({loading: false})
      return Promise.reject(error);
    });

    let data = JSON.stringify({
      algorithm: this.state.algorithm,
      minSupport: Number(this.state.minsup),
      logName: this.state.file.name,
      metric: this.state.metric,
      minCoverage: this.state.mincov,
      segmented: this.state.segmented,
      context: this.state.selectedContextAttributes,
    })

    let config = {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8'
      },
    }

    axios.post('http://localhost:8080/routines', data, config)
    .then(response => {
      this.props.setRoutines(response.data)
      this.setState({loading: false})
    })
    .catch(err => {
      this.setState({loading: false})
    })
  }

  renderActions = () => {
    if (this.state.successfullUploaded) {
      return (
            <Button
              color="primary"
              variant="contained"
              disabled={
                this.state.file == null || this.state.loading ||
                this.state.minsup == null || this.state.mincov == null ||
                this.state.selectedContextAttributes.length == 0
              }
              onClick={this.identifyRoutines}
              >
              Identify routines
            </Button>
      );
    }
  }

  sendConfig = () => {
    return new Promise((resolve, reject) => {
      const req = new XMLHttpRequest();

      req.open("POST", "http://localhost:8080/routines");
      req.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

      req.onload = resolve;
      req.onerror = reject;

      req.send(JSON.stringify({
        algorithm: this.state.algorithm,
        minSupport: Number(this.state.minsup),
        logName: this.state.file.name,
        metric: this.state.metric,
        minCoverage: this.state.mincov,
        segmented: this.state.segmented,
      }));

      this.setState({loading: true})

      req.onreadystatechange = () => {
        if (req.readyState == XMLHttpRequest.DONE) {
          this.props.setRoutines(JSON.parse(req.response))
          this.setState({loading: false})
        }
      }
    })
  }

  render() {
    return (
      <div>
        {this.state.loading &&
          <LinearProgress id="routine-progress"/>
        }
        <Card minWidth="lg">

          <CardHeader title="Log configuration">

          </CardHeader>

          <CardContent>

            <Grid container spacing={1}>

              <Grid item lg={3}>

                <Dropzone
                  onFileAdded={this.uploadLog}
                  disabled={this.state.loading || this.state.successfullUploaded}
                  />

              </Grid>

              <Grow in={this.state.successfullUploaded}>

                <Grid item lg={9}>

                  <ConfigParams
                    contextAttributes={this.state.contextAttributes}
                    handleContextAttributes={(attributes) => this.setState({selectedContextAttributes: attributes})}
                    setMinSup={(minSup) => this.setState({minsup: minSup})}
                    setMinCov={(minCov) => this.setState({mincov: minCov})}
                    setAlgorithm={(algorithmName) => this.setState({algorithm: algorithmName})}
                    setMetric={(metric) => this.setState({metric: metric})}
                    setSegmented={(isSegmented) => this.setState({segmented: isSegmented})}
                    />

                </Grid>

              </Grow>

            </Grid>

            <Grid container alignItems='flex-end' direction='column-reverse'>
                {this.renderActions()}
            </Grid>

          </CardContent>

        </Card>

      </div>
    );
  }
}

export default LogUpload;
