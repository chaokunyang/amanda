import React, { Component } from 'react'
import Axios from 'axios'

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {article: {}};
    }

    componentDidMount() {
        Axios.get('/api/articles/' + this.props.params.articleId)
            .then(response => {
                this.setState({article: response.data})
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        return (
            <div>
                {this.state.article.title}
                <h2>title:{this.state.article.title}</h2>
                <div>
                    keywords:{this.state.article.keywords}
                </div>
                <div>
                    mdBody:{this.state.article.mdBody}
                </div>
                <div>
                    htmlBody:{this.state.article.htmlBody}
                </div>
                <div>
                    published:{this.state.article.published}
                </div>
            </div>
        )
    }
}

export default Article