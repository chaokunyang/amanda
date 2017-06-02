import React, { Component } from 'react';
import '../../../resources/static/assets/css/admin/App.css';
import Logout from './Logout'

const Header = () => (
    <div className="App-header">
        <h1>Amanda管理后台</h1>
        <span><input placeholder="搜索"/></span>
        <span><a href="/">网站首页</a></span>
        <span><a href="/about">关于我</a></span>
        <Logout/>
    </div>
);

export default Header