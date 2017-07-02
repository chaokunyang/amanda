import React, { Component } from 'react';
import Logout from '../logout/Logout'
import './Header.css'

const Header = () => (
    <div className="App-header">
        <nav className="navbar navbar-inverse navbar-fixed-top">
            <div className="container-fluid">
                <div className="navbar-header">
                    <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span className="sr-only">Toggle navigation</span>
                        <span className="icon-bar"></span>
                        <span className="icon-bar"></span>
                        <span className="icon-bar"></span>
                    </button>
                    <a className="navbar-brand" href="#">Amanda后台管理</a>
                </div>
                <div id="navbar" className="navbar-collapse collapse">
                    <ul className="nav navbar-nav navbar-right">
                        <li><a href="/">网站首页</a></li>
                        <li><a href="/about">关于我</a></li>
                    </ul>
                    {/*<form className="navbar-form navbar-right">
                        <input type="text" className="form-control" placeholder="Search...">
                    </form>*/}
                    <Logout/>
                </div>
            </div>
        </nav>
    </div>
);

export default Header