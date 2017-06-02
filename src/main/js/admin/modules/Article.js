import React, { Component } from 'react'
import Axios from 'axios'
import MarkdownEditor from './markdown/MarkdownEditor'

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {article: {}};
        this.onMarkdownChange = this.onMarkdownChange.bind(this);
        this.handleSubmit  = this.handleSubmit .bind(this);
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

    onMarkdownChange(markdownText, renderedHtml) {
        this.setState((prevState, props) => {
            let article = prevState.article;
            article.mdBody = markdownText;
            article.htmlBody = renderedHtml;
            article: article
        })
    }

    handleSubmit (event) {
        Axios.post('/api/articles', this.state.article)
            .then(response => this.setState({article: response.data}))
            .catch(error => console.log(error));

        event.preventDefault()
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                {this.state.article.title}
                <h2>title:{this.state.article.title}</h2>
                <div>
                    keywords:{this.state.article.keywords}
                </div>
                <div>
                    published:{this.state.article.published}
                </div>

                <div>
                    <MarkdownEditor inputTitle="mdBody" mdBody={this.state.article.mdBody} previewTitle="预览" htmlBody={this.state.article.htmlBody} onMarkdownChange={this.onMarkdownChange}/>
                </div>

                <input type="submit" value="更新" />
            </form>
        )
    }
}

export default Article