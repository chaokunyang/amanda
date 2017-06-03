import React, { Component } from 'react'
import { Link } from 'react-router'
import NavLink from '../NavLink'
import Axios from 'axios'

class Articles extends Component {

    constructor(props) {
        super(props);
        this.state = { articles: [] }
    }

    componentDidMount() {
        Axios.get('/api/articles')
            .then(response => {
                this.setState({articles: response.data.content})
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        let articleList = this.state.articles.map(article =>
            <li key={article.id}>
                <NavLink to={ "articles/" + article.id + "/" + article.title}>{article.title}</NavLink>
            </li>
        );

        return(
            <div>
                <h2>文章列表</h2>
                <Link to="/articles/new">新建文章</Link>
                <ul>
                    { articleList }
                </ul>

                {this.props.children}
            </div>
        )
    }
}

export default Articles