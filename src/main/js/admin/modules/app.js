import React from 'react'
import { Link } from 'react-router'

export default React.createClass({
    render() {
        return (
            <div>
                <h1>Amanda管理后台</h1>
                <ul role="nav">
                    <li><Link to="/admin">主页</Link></li>
                    <li><Link to="/report">报告</Link></li>
                    <li><Link to="/analyze">分析</Link></li>
                    <li><Link to="/article">文章</Link></li>
                    <li><Link to="/category">分类</Link></li>
                </ul>
            </div>
        )
    }
})
