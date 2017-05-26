import React from 'react'
import { Route, IndexRoute } from 'react-router'
import App from './App'
import Home from './Home'
import Articles from './Articles'
import Article from './Article'
import Category from './Category'
import Report from './Report'
import Analyze from './Analyze'
import Profile from './Profile'
import Settings from './Settings'

const routes = (
    <Route path="/" component={App}>
        <IndexRoute component={Home}/>
        <Route path="/articles" component={Articles}>
            <Route path="/articles/:articleId/:articleName" component={Article}/>
        </Route>
        <Route path="/categories" component={Category}/>
        <Route path="/report" component={Report}/>
        <Route path="/analyze" component={Analyze}/>
        <Route path="/profile" component={Profile}/>
        <Route path="/settings" component={Settings}/>
    </Route>
);

export default routes;


