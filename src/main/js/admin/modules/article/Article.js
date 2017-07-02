import React, { Component } from 'react'
import Axios from 'axios'
import ArticleView from './ArticleView'
import ArticleEdit from './ArticleEdit'
import './Article.css'

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            view: true
        };
        this.onMarkdownChange = this.onMarkdownChange.bind(this);
        this.handleSubmit  = this.handleSubmit.bind(this);
        this.handleInputChange  = this.handleInputChange .bind(this);
        this.setView = this.setView.bind(this);
        this.publish = this.publish.bind(this);
        this.cancelPublish = this.cancelPublish.bind(this);
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
        // document.querySelectorAll("pre code").forEach(function(i, block) {
        //     hljs.highlightBlock(block);
        // });
        this.setState((prevState, props) => {
            let article = prevState.article;
            article.mdBody = markdownText;
            article.htmlBody = renderedHtml;
            article
        })
    }

    handleSubmit(event) {
        event.preventDefault();

        Axios.post('/api/articles', this.state.article)
            .then(response => {
                this.setState({article: response.data});
                this.setView();
            })
            .catch(error => console.log(error));
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === "checkbox" ? target.checked : target.value;
        const name = target.name;

        this.setState((prevState, props) => {
            let article = prevState.article;
            article[name] = value;
            article
        })
    }

    setView() {
        this.setState((prevState, props) => ({
            view : !prevState.view
        }))
    }

    publish() {
        const id = this.state.article.id;
        Axios.put(`/api/articles/publish/${id}`)
            .then(response => {
                this.setState({article: response.data});
            })
            .catch(error => console.log(error));
    }

    cancelPublish() {
        const id = this.state.article.id;
        Axios.put(`/api/articles/publish/cancel/${id}`)
            .then(response => {
                this.setState({article: response.data});
            })
            .catch(error => console.log(error));
    }

    render() {
        return (
            <div className="Article">
                {
                    this.state.view ?
                        <ArticleView view={this.state.view} setView={this.setView} article={this.state.article} publish={this.publish} cancelPublish={this.cancelPublish}/>
                        : <ArticleEdit view={this.state.view} setView={this.setView} article={this.state.article} handleInputChange={this.handleInputChange} onMarkdownChange={this.onMarkdownChange} handleSubmit={this.handleSubmit} />
                }
            </div>
        )
    }
}

export default Article