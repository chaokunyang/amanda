import { Router, useRouterHistory } from 'react-router'
import { createHistory } from 'history'

const history = useRouterHistory(createHistory)({
    basename: '/admin'
});

export default history;
