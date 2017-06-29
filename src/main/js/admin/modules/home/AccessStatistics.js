import React, { Component } from 'react';

function getStatistics(metrics) {
    const statistics = [];
    for(let prop in metrics) {
        if(metrics.hasOwnProperty(prop) && prop.startsWith('counter')) {
            const item = {};
            let path = prop.replace(/star-star/g, '**')
                .replace(/\./g, '/').substring('counter.status.200'.length);
            const responseStatus = prop.substr('counter.status.'.length, 3);
            const gaugeKey = prop.replace(/counter\.status\.\d{3}/, 'gauge.response');
            const lastResponseTime = metrics[gaugeKey] + 'ms';

            if(path === '/unmapped') {
                path = '未映射路径';
            }else if(path === '/root') {
                path = '/';
            }
            item['path'] = path;
            item['responseStatus'] = responseStatus;
            item['lastResponseTime'] = lastResponseTime;
            item['visits'] = metrics[prop];

            statistics.push(item);
        }
    }

    return statistics;
}

function AccessStatistics(props) {
    const statistics = getStatistics(props.metrics);
    const trs = statistics.map(item =>
        <tr key={item.path + item.responseStatus}>
            <td>{item.path}</td>
            <td>{item.responseStatus}</td>
            <td>{item.lastResponseTime}</td>
            <td>{item.visits}</td>
        </tr>
    );
    return (
        <div className="metrics">

            <div className="panel panel-info">
                <div className="panel-heading">访问统计</div>

                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th>访问路径</th>
                        <th>响应状态</th>
                        <th width={200}>上次响应时间<br/>(与状态码不完全对应)</th>
                        <th>访问次数</th>
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

export default AccessStatistics;