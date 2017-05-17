import React from 'react'
import { render } from 'react-dom'
import { Router, Route, hashHistory } from 'react-router'
import App from './modules/App'
import Article from './modules/Article'

render((
    <Router history={hashHistory}>
        <Route path="/" component={App}></Route>
        <Route path="/article" component={Article}></Route>
        <Route path="/category" component={App}></Route>
    </Router>
), document.getElementById('app'));