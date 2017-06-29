import React, { Component } from 'react';
import moment from 'moment';

function HttpTrace(props) {
    let trs = [];
    if(props.trace.length > 0) {
        trs = props.trace.slice(0, 15).map(item =>
            <tr key={item.timestamp + item.info.path}>
                <td>{moment(item.timestamp).format('YYYY/MM/DD HH:mm:ss')}</td>
                <td>{item.info.method}</td>
                <td>{item.info.path}</td>
                <td>{item.info.headers.request.host}</td>
                <td>{item.info.headers.request.referer}</td>
                <td>{item.info.timeTaken}</td>
            </tr>
        );
    }
    return (
        <div className="trace">
            <h3>最近访问</h3>
            <div className="table-responsive">
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>method</th>
                        <th>path</th>
                        <th>Host</th>
                        <th>referer</th>
                        <th>timeTaken</th>
                    </tr>
                    </thead>
                    <tbody>
                    {trs}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default HttpTrace;