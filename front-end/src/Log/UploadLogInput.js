import React from "react";
import { Fab, Box } from '@material-ui/core';
import green from '@material-ui/core/colors/green';
import BackupIcon from '@material-ui/icons/Backup';
import axios from 'axios';

class UploadLogInput extends React.Component {

  uploadLog = (event) => {
    if (this.props.disabled) return;

    let config = {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
    }

    this.props.onLoading({successfullUploaded: false, loading: true})
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append("file", file, file.name);

    axios.post('http://localhost:8080/logs', formData, config)
    .then(response => {
      this.props.onLoading({successfullUploaded: true, loading: false})
      this.props.onResponse({contextAttributes: response.data, file: file})
    })
    .catch(err => {
      this.props.onLoading({successfullUploaded: false, loading: false})
    })
  }

  render() {
    return (
      <Box display="flex" p={1} alignItems="center">
        <label htmlFor="log-upload">
          <input
            style={{ display: 'none' }}
            accept=".csv"
            id="log-upload"
            type="file"
            multiple
            onChange={this.uploadLog}
            disabled={this.props.disabled}
            />
          <Fab variant="extended" component="span">
            <Box display="flex">
              <Box display="flex" mr={1}>
                <BackupIcon style={{ color: green[500] }} />
              </Box>
              Upload Log
            </Box>
          </Fab>
        </label>
      </Box>
    );
  }
}

export default UploadLogInput;
