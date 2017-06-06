import React, { Component } from 'react';
import Logout from '../logout/Logout'
import './Header.css'

const Header = () => (
    <div className="App-header ghost-center">
        <h1>Amanda管理后台</h1>
        <span><input placeholder="搜索..." className="search-box"/></span>
        <span><a href="/">网站首页</a></span>
        <span><a href="/about">关于我</a></span>
        <Logout/>
    </div>
);

export default Header