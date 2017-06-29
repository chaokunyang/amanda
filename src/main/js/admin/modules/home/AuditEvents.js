import React, { Component } from 'react';
import moment from 'moment';

function AuditEvents(props) {
    if(props.events.length >= 2) {
        let lastLogged;
        let lastDenied;
        for(let i = props.events.length - 1; i >=0; i--) {
            if(lastDenied && lastLogged) {
                break;
            }
            const item = props.events[i];
            if(item.type === 'AUTHORIZATION_FAILURE') {
                lastDenied = item;
            }else {
                lastLogged = item;
            }
        }


        return (
            <div className="audit">
                <div className="panel panel-info">
                    <div className="panel-heading">
                        <h3 className="panel-title">认证授权信息</h3>
                    </div>
                    <div className="panel-body">
                        <div className="lastLogged">
                            <h4>上次登录</h4>
                            <div>
                                {lastLogged.principal}
                                于{moment(lastLogged.timestamp).format('YYYY/MM/DD HH:mm:ss')}
                                在{lastLogged.data.details.remoteAddress}登录成功
                            </div>
                        </div>

                        <div className="lastDenied">
                            <h4>上次访问失败</h4>
                            <div>
                                {lastDenied.principal}
                                于{moment(lastDenied.timestamp).format('YYYY/MM/DD HH:mm:ss')}
                                在{lastDenied.data.details.remoteAddress}访问失败
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }else {
        return (
            <div className="audit">
                <div className="panel panel-info">
                    <div className="panel-heading">
                        <h3 className="panel-title">认证授权信息</h3>
                    </div>
                </div>
            </div>
        )
    }
}

export default AuditEvents;