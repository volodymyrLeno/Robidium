import React from 'react'
import { TableContainer, Table, TableBody, TableRow, TableCell } from '@material-ui/core'
import Typography from '@material-ui/core/Typography';

class RoutineTable extends React.Component {

  render () {
    return (
      <TableContainer>
        <Table size="small">
          <TableBody>
            {this.props.routine.items.map((item, itemIdx) => {
              return (
                <TableRow>
                  <TableCell align="left">
                    {itemIdx + 1}
                  </TableCell>
                  {item.value.split("+").map(value => {
                    return (
                      <TableCell align="left">
                        <Typography
                          variant="body2"
                          noWrap
                          style={{maxWidth: 100,}}>
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
}

export default RoutineTable
