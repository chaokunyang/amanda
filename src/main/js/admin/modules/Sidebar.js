import React, { Component } from 'react';
import { IndexLink} from 'react-router'
import NavLink from './NavLink'
import '../../../resources/static/assets/css/admin/App.css';

const Sidebar = () => (
    <div className="Sidebar">
        <ul role="nav">
            <li><IndexLink to="/" activeClassName="active">主页</IndexLink></li>
            <li><NavLink to="/articles">文章</NavLink></li>
            <li><NavLink to="/categories">分类</NavLink></li>
            <li><NavLink to="/report">报告</NavLink></li>
            <li><NavLink to="/analyze">分析</NavLink></li>
            <li><NavLink to="/profile">简介</NavLink></li>
            <li><NavLink to="/settings">设置</NavLink></li>
        </ul>
    </div>
);

export default Sidebar