import React, { Component } from 'react';
import Axios from 'axios'
import './Monitor.css'

class Monitor extends Component {

    constructor(props) {
        super(props);
        this.state = {
            health: {
                diskSpace: {
                    total: 0,
                    free: 0,
                    threshold: 0,
                },
                db: {

                }
            },
            metrics: {}
        }
    }

    componentDidMount() {
        Axios.get('/health')
            .then(response => {
                this.setState({
                    health: response.data
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
    }

    render() {
        const gc = [], datasource = [];
        for(let prop in this.state.metrics) {
            if (prop.startsWith("gc")) {
                gc.push((<div className="col-md-3" key={prop}>{prop}: {this.state.metrics[prop]}</div>))
            }else if(prop.startsWith('datasource')) {
                datasource.push((<div className="col-md-3" key={prop}>{prop}: {this.state.metrics[prop]}</div>))
            }
        }

        return (
            <div className="Home">
                <h2>首页</h2>
                <div className="container">
                    <h3>应用健康状态</h3>
                    <div className="row">
                        <div className="col-md-3">应用状态：{this.state.health.status}</div>
                        <div className="col-md-3">服务器磁盘空间：{ (this.state.health.diskSpace.total / (1024 * 1024 * 1024)).toFixed(2) + "GB"}</div>
                        <div className="col-md-3">服务器剩余磁盘空间：{ (this.state.health.diskSpace.free / (1024 * 1024 * 1024)).toFixed(2) + "GB"}</div>
                        <div className="col-md-3">threshold: { (this.state.health.diskSpace.threshold  / (1024 * 1024)).toFixed(2) + "MB"}</div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">数据库状态：{this.state.health.db.status}</div>
                        <div className="col-md-3">数据库：{this.state.health.db.database}</div>
                    </div>
                </div>

                <div className="container">
                    <h3>应用metrics</h3>
                    <div className="row">
                        <div className="col-md-3">应用总内存：{ (this.state.metrics.mem / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">应用剩余内存：{ (this.state.metrics['mem.free'] / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">处理器数量：{ this.state.metrics.processors }</div>
                        <div className="col-md-3">应用运行时间：{ (this.state.metrics.uptime / (1000 * 60 * 60 )).toFixed(2) + '小时' }</div>
                        <div className="col-md-3">上下文updatetime：{ (this.state.metrics['instance.uptime']/ (1000 * 60 * 60 )).toFixed(2) + '小时' }</div>
                        <div className="col-md-3">系统平均负载：{ this.state.metrics['systemload.average'] }</div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">heap：{ (this.state.metrics.heap / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">heap.committed：{ (this.state.metrics['heap.committed'] / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">heap.init：{ (this.state.metrics['heap.init'] / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">heap.used：{ (this.state.metrics['heap.used'] / 1024).toFixed(2) + 'MB' }</div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">nonheap：{ (this.state.metrics.nonheap / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">nonheap.used：{ (this.state.metrics['nonheap.used'] / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">nonheap.init：{ (this.state.metrics['nonheap.init'] / 1024).toFixed(2) + 'MB' }</div>
                        <div className="col-md-3">nonheap.committed：{ (this.state.metrics['nonheap.committed'] / 1024).toFixed(2) + 'MB' }</div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">threads：{ this.state.metrics.threads }</div>
                        <div className="col-md-3">threads.peak：{ this.state.metrics['threads.peak'] }</div>
                        <div className="col-md-3">threads.daemon：{ this.state.metrics['threads.daemon'] }</div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">classes：{ this.state.metrics.classes }</div>
                        <div className="col-md-3">classes.loaded：{ this.state.metrics['classes.loaded'] }</div>
                        <div className="col-md-3">classes.unloaded：{ this.state.metrics['classes.unloaded'] }</div>
                    </div>
                    <div className="row">
                        {gc}
                    </div>
                    <div className="row">
                        {datasource}
                    </div>
                    <div className="row">
                        <div className="col-md-3">httpsessions.active：{ this.state.metrics['httpsessions.active'] }</div>
                        <div className="col-md-3">httpsessions.max：{ this.state.metrics['httpsessions.max'] }</div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Monitor