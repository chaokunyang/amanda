import React, { Component } from 'react'
import { Link } from 'react-router'
import NavLink from './NavLink'
import Axios from 'axios'

class Articles extends Component {

    // state = { articles: null };

    componentDidMount() {
        Axios.get('/api/categories')
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });

    }

    render() {
        return(
            <div>
                <h2>文章列表</h2>
                <Link to="/articles/new"/>
                <ul>
                    <li><NavLink to="/articles/1"/>使用事件源在微服务中实现最终一致性</li>
                    <li><NavLink to="/articles/2"/>使用Elastic Stack搭建日志平台</li>
                </ul>
            </div>
        )
    }
}

export default Articles