import React, { Component } from 'react';
import Axios from 'axios'
import './Home.css'
import AuditEvents from './AuditEvents'
import AccessStatistics from './AccessStatistics'
import HttpTrace from './HttpTrace'


class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            events: [],
            metrics: {},
            trace: {}
        }
    }

    componentDidMount() {
        Axios.get('/auditevents')
            .then(response => {
                this.setState({
                    events: response.data.events
                })
            })
            .catch(error => {
                console.log(error);
            });
        Axios.get('/metrics')
            .then(response => {
                this.setState({
                    metrics: response.data
                })
            })
            .catch(error => {
                console.log(error);
            });
        Axios.get('/trace')
            .then(response => {
                this.setState({
                    trace: response.data
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {

        return (
            <div className="Home">
                <h2>首页</h2>

                <div>
                    <AuditEvents events={this.state.events}/>

                    <HttpTrace trace={this.state.trace}/>

                    <AccessStatistics metrics={this.state.metrics}/>
                </div>
            </div>
        )
    }
}

export default Home