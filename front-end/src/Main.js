import React from 'react'
import LogPanel from './Log/LogPanel'
import RoutinePanel from './Routine/RoutinePanel'
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  content: {
    padding: theme.spacing(5, 0),
  },
});

class Main extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      routines: [
    {
        "id": "f295390c-9eb7-4f23-8310-492c30434a4f",
        "items": [
            {
                "index": 0,
                "value": "copyCell+Excel+Sheet1+A",
                "automatable": true,
                "contextId": "A"
            },
            {
                "index": 1,
                "value": "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 2,
                "value": "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 3,
                "value": "copyCell+Excel+Sheet1+B",
                "automatable": true,
                "contextId": "B"
            },
            {
                "index": 4,
                "value": "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 5,
                "value": "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 6,
                "value": "copyCell+Excel+Sheet1+C",
                "automatable": true,
                "contextId": "C"
            },
            {
                "index": 7,
                "value": "paste+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "date"
            },
            {
                "index": 8,
                "value": "editField+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "date"
            },
            {
                "index": 9,
                "value": "copyCell+Excel+Sheet1+D",
                "automatable": true,
                "contextId": "D"
            },
            {
                "index": 10,
                "value": "paste+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "PhoneNumber"
            },
            {
                "index": 11,
                "value": "editField+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "PhoneNumber"
            },
            {
                "index": 12,
                "value": "copyCell+Excel+Sheet1+E",
                "automatable": true,
                "contextId": "E"
            },
            {
                "index": 13,
                "value": "paste+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "Email"
            },
            {
                "index": 14,
                "value": "editField+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "Email"
            },
            {
                "index": 15,
                "value": "copyCell+Excel+Sheet1+F",
                "automatable": true,
                "contextId": "F"
            },
            {
                "index": 16,
                "value": "paste+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine1"
            },
            {
                "index": 17,
                "value": "editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine1"
            },
            {
                "index": 18,
                "value": "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "Address_ZipCode"
            },
            {
                "index": 19,
                "value": "clickCheckbox+Chrome+DecisionBox+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "DecisionBox"
            },
            {
                "index": 20,
                "value": "editField+Chrome+DecisionBox+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "DecisionBox"
            },
            {
                "index": 21,
                "value": "clickButton+Chrome+https://forms.zoho.com/universityofmelbourne/form/NewRecord+Submit",
                "automatable": true,
                "contextId": ""
            }
        ],
        "absSupport": 40,
        "relSupport": 0.8,
        "length": 22,
        "cohesionScore": 0,
        "automatable": false,
        "transformations": {
            "(copyCell+Excel+Sheet1+C,editField+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "t = f_split(t, 0, '/')\nt = f_join_char(t, 1, '-')\nt = f_join_char(t, 0, '-')\n\n",
            "(copyCell+Excel+Sheet1+D,editField+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "t = f_split_first(t, 0, ' ')\nt = f_drop(t, 0)\nt = f_split_w(t, 0)\nt = f_join_char(t, 1, '-')\nt = f_join_char(t, 0, '-')\n\n",
            "(copyCell+Excel+Sheet1+F,editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "Equality",
            "(copyCell+Excel+Sheet1+F,editField+Chrome+DecisionBox+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "",
            "(copyCell+Excel+Sheet1+A,editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "Equality",
            "(copyCell+Excel+Sheet1+B,editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "t = f_join_char(t, 0, ' ')\n\n",
            "(copyCell+Excel+Sheet1+F,editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "",
            "(copyCell+Excel+Sheet1+E,editField+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "Equality"
        },
        "itemsDependencies": [
            {
                "depender": {
                    "index": 20,
                    "value": "editField+Chrome+DecisionBox+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                    "automatable": true,
                    "contextId": "DecisionBox"
                },
                "dependee": null,
                "dependerPerDependee": {
                    "[on]": "on"
                }
            },
            {
                "depender": {
                    "index": 18,
                    "value": "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                    "automatable": true,
                    "contextId": "Address_ZipCode"
                },
                "dependee": [
                    {
                        "index": 15,
                        "value": "copyCell+Excel+Sheet1+F",
                        "automatable": true,
                        "contextId": "F"
                    },
                    {
                        "index": 16,
                        "value": "paste+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                        "automatable": true,
                        "contextId": "SingleLine1"
                    },
                    {
                        "index": 17,
                        "value": "editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                        "automatable": true,
                        "contextId": "SingleLine1"
                    }
                ],
                "dependerPerDependee": {
                    "[United Kingdom, United Kingdom, United Kingdom]": "+44",
                    "[Italy, Italy, Italy]": "+39",
                    "[China, China, China]": "+86",
                    "[Spain, Spain, Spain]": "+34",
                    "[Canada, Canada, Canada]": "+11",
                    "[Netherlands, Netherlands, Netherlands]": "+31",
                    "[South Korea, South Korea, South Korea]": "+82",
                    "[Hungary, Hungary, Hungary]": "+36",
                    "[United States, United States, United States]": "+12",
                    "[Sweden, Sweden, Sweden]": "+46",
                    "[France, France, France]": "+33",
                    "[Ukraine, Ukraine, Ukraine]": "+380",
                    "[Norway, Norway, Norway]": "+47",
                    "[Czech Republic, Czech Republic, Czech Republic]": "+420",
                    "[Portugal, Portugal, Portugal]": "+351",
                    "[Croatia, Croatia, Croatia]": "+385",
                    "[Finland, Finland, Finland]": "+358",
                    "[New Zealand, New Zealand, New Zealand]": "+64",
                    "[Japan, Japan, Japan]": "+81",
                    "[Russia, Russia, Russia]": "+7",
                    "[Germany, Germany, Germany]": "+49",
                    "[Ireland, Ireland, Ireland]": "+353",
                    "[Kazakhstan, Kazakhstan, Kazakhstan]": "+7",
                    "[Poland, Poland, Poland]": "+48"
                }
            }
        ],
        "coverage": 0.8148148148148148,
        "emptyTransformationPairs": [
            {
                "copyCell+Excel+Sheet1+F": {
                    "index": 20,
                    "value": "editField+Chrome+DecisionBox+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                    "automatable": true,
                    "contextId": "DecisionBox"
                }
            },
            {
                "copyCell+Excel+Sheet1+F": {
                    "index": 18,
                    "value": "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                    "automatable": true,
                    "contextId": "Address_ZipCode"
                }
            }
        ],
        "absoluteSupport": 40,
        "relativeSupport": 0.8,
        "itemsValues": [
            "copyCell+Excel+Sheet1+A",
            "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+B",
            "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+C",
            "paste+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+D",
            "paste+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+E",
            "paste+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+F",
            "paste+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "clickCheckbox+Chrome+DecisionBox+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+DecisionBox+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "clickButton+Chrome+https://forms.zoho.com/universityofmelbourne/form/NewRecord+Submit"
        ],
        "rai": 0.0
    },
    {
        "id": "452c87bb-da36-4f82-b731-293dbeb99770",
        "items": [
            {
                "index": 0,
                "value": "copyCell+Excel+Sheet1+A",
                "automatable": true,
                "contextId": "A"
            },
            {
                "index": 1,
                "value": "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 2,
                "value": "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 3,
                "value": "copyCell+Excel+Sheet1+B",
                "automatable": true,
                "contextId": "B"
            },
            {
                "index": 4,
                "value": "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 5,
                "value": "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine"
            },
            {
                "index": 6,
                "value": "copyCell+Excel+Sheet1+C",
                "automatable": true,
                "contextId": "C"
            },
            {
                "index": 7,
                "value": "paste+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "date"
            },
            {
                "index": 8,
                "value": "editField+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "date"
            },
            {
                "index": 9,
                "value": "copyCell+Excel+Sheet1+D",
                "automatable": true,
                "contextId": "D"
            },
            {
                "index": 10,
                "value": "paste+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "PhoneNumber"
            },
            {
                "index": 11,
                "value": "editField+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "PhoneNumber"
            },
            {
                "index": 12,
                "value": "copyCell+Excel+Sheet1+E",
                "automatable": true,
                "contextId": "E"
            },
            {
                "index": 13,
                "value": "paste+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "Email"
            },
            {
                "index": 14,
                "value": "editField+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "Email"
            },
            {
                "index": 15,
                "value": "copyCell+Excel+Sheet1+F",
                "automatable": true,
                "contextId": "F"
            },
            {
                "index": 16,
                "value": "paste+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine1"
            },
            {
                "index": 17,
                "value": "editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "SingleLine1"
            },
            {
                "index": 18,
                "value": "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                "automatable": true,
                "contextId": "Address_ZipCode"
            },
            {
                "index": 19,
                "value": "clickButton+Chrome+https://forms.zoho.com/universityofmelbourne/form/NewRecord+Submit",
                "automatable": true,
                "contextId": ""
            }
        ],
        "absSupport": 10,
        "relSupport": 0.2,
        "length": 20,
        "cohesionScore": 0,
        "automatable": false,
        "transformations": {
            "(copyCell+Excel+Sheet1+C,editField+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "t = f_split(t, 0, '/')\nt = f_join_char(t, 1, '-')\nt = f_join_char(t, 0, '-')\n\n",
            "(copyCell+Excel+Sheet1+D,editField+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "t = f_split_first(t, 0, ' ')\nt = f_drop(t, 0)\nt = f_split_w(t, 0)\nt = f_join_char(t, 1, '-')\nt = f_join_char(t, 0, '-')\n\n",
            "(copyCell+Excel+Sheet1+F,editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "Equality",
            "(copyCell+Excel+Sheet1+A,editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "Equality",
            "(copyCell+Excel+Sheet1+B,editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "t = f_join_char(t, 0, ' ')\n\n",
            "(copyCell+Excel+Sheet1+F,editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "",
            "(copyCell+Excel+Sheet1+E,editField+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord)": "Equality"
        },
        "itemsDependencies": [
            {
                "depender": {
                    "index": 18,
                    "value": "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                    "automatable": true,
                    "contextId": "Address_ZipCode"
                },
                "dependee": [
                    {
                        "index": 15,
                        "value": "copyCell+Excel+Sheet1+F",
                        "automatable": true,
                        "contextId": "F"
                    },
                    {
                        "index": 16,
                        "value": "paste+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                        "automatable": true,
                        "contextId": "SingleLine1"
                    },
                    {
                        "index": 17,
                        "value": "editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                        "automatable": true,
                        "contextId": "SingleLine1"
                    }
                ],
                "dependerPerDependee": {
                    "[United Kingdom, United Kingdom, United Kingdom]": "+44",
                    "[Italy, Italy, Italy]": "+39",
                    "[Canada, Canada, Canada]": "+11",
                    "[Hungary, Hungary, Hungary]": "+36",
                    "[United States, United States, United States]": "+12",
                    "[Sweden, Sweden, Sweden]": "+46",
                    "[Norway, Norway, Norway]": "+47",
                    "[Australia, Australia, Australia]": "+61",
                    "[Czech Republic, Czech Republic, Czech Republic]": "+420",
                    "[Portugal, Portugal, Portugal]": "+351",
                    "[Croatia, Croatia, Croatia]": "+385",
                    "[New Zealand, New Zealand, New Zealand]": "+64",
                    "[Germany, Germany, Germany]": "+49",
                    "[China, China, China]": "+86",
                    "[Spain, Spain, Spain]": "+34",
                    "[Netherlands, Netherlands, Netherlands]": "+31",
                    "[South Korea, South Korea, South Korea]": "+82",
                    "[France, France, France]": "+33",
                    "[Ukraine, Ukraine, Ukraine]": "+380",
                    "[Finland, Finland, Finland]": "+358",
                    "[Japan, Japan, Japan]": "+81",
                    "[Russia, Russia, Russia]": "+7",
                    "[Ireland, Ireland, Ireland]": "+353",
                    "[Kazakhstan, Kazakhstan, Kazakhstan]": "+7",
                    "[Poland, Poland, Poland]": "+48"
                }
            }
        ],
        "coverage": 0.18518518518518517,
        "emptyTransformationPairs": [
            {
                "copyCell+Excel+Sheet1+F": {
                    "index": 18,
                    "value": "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
                    "automatable": true,
                    "contextId": "Address_ZipCode"
                }
            }
        ],
        "absoluteSupport": 10,
        "relativeSupport": 0.2,
        "itemsValues": [
            "copyCell+Excel+Sheet1+A",
            "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+B",
            "paste+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+SingleLine+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+C",
            "paste+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+date+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+D",
            "paste+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+PhoneNumber+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+E",
            "paste+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+Email+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "copyCell+Excel+Sheet1+F",
            "paste+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+SingleLine1+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "editField+Chrome+Address_ZipCode+https://forms.zoho.com/universityofmelbourne/form/NewRecord",
            "clickButton+Chrome+https://forms.zoho.com/universityofmelbourne/form/NewRecord+Submit"
        ],
        "rai": 0.0
    }
]
    };
  }

  handleRoutines = (routines) => {
    this.setState({routines: routines})
  }

  render () {
    const { classes } = this.props;
    return (
      <div className={classes.content}>
        <Container maxWidth="lg">
          <Grid container spacing={5} direction={ 'column' }>
            <Grid item xs>
            <Container maxWidth="lg">
              <LogPanel onRoutinesReceived={this.handleRoutines}/>
            </Container>
            </Grid>
            <Grid item xs>
            <Container maxWidth="lg">
              <RoutinePanel routines={this.state.routines}/>
            </Container>
            </Grid>
          </Grid>
        </Container>
      </div>
    )
  }
}

export default withStyles(styles)(Main);
