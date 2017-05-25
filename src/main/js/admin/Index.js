import React from 'react'
import { render } from 'react-dom'
import { Router, useRouterHistory } from 'react-router'
import routes from './modules/routes'
import { createHistory } from 'history'

const history = useRouterHistory(createHistory)({
    basename: '/admin'
});


render((
    <Router routes={routes} history={history}/>
), document.getElementById('app'));
