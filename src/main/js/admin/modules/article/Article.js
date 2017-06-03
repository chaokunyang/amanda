import React, { Component } from 'react'
import Axios from 'axios'
import MarkdownEditor from '../markdown/MarkdownEditor'
import './Article.css'

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
            <div className="Article">
                <form onSubmit={this.handleSubmit}>
                    <h2>{this.state.article.title}</h2>
                    <div>
                        <span>标题:</span>
                        <span>{this.state.article.title}</span>
                    </div>
                    <div>
                        <span>关键字:</span>
                        <span>{this.state.article.keywords}</span>
                    </div>
                    <div>
                        <span>分类:</span>
                        <span>{this.state.article.categories}</span>
                    </div>
                    <div>
                        <span>发布:</span>
                        <span>{this.state.article.published ? '发布于' + this.state.article.publishedDate : '未发布'}</span>
                    </div>

                    <div>
                        <MarkdownEditor inputTitle="mdBody" mdBody={this.state.article.mdBody} previewTitle="预览" htmlBody={this.state.article.htmlBody} onMarkdownChange={this.onMarkdownChange}/>
                    </div>

                    <input type="submit" value="更新" />
                </form>
            </div>
        )
    }
}

export default Article