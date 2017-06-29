import React from 'react'
import { render } from 'react-dom'
// import { Router, useRouterHistory } from 'react-router'
import { Router } from 'react-router'
import routes from './modules/routes'
// import { createHistory } from 'history'
import history from './modules/History'

render((
    <Router routes={routes} history={history}/>
), document.getElementById('app'));
