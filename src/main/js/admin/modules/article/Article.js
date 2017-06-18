import React, { Component } from 'react'
import Axios from 'axios'
import MarkdownEditor from '../markdown/MarkdownEditor'
import './Article.css'

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {article: {}};
        this.onMarkdownChange = this.onMarkdownChange.bind(this);
        this.handleSubmit  = this.handleSubmit.bind(this);
        this.handleInputChange  = this.handleInputChange .bind(this);
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
            .then(response => this.setState({article: response.data}))
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

    render() {
        return (
            <div className="Article">
                <form onSubmit={this.handleSubmit} className="form-horizontal">
                    <h2>{this.state.article.title}</h2>
                    <div className="form-group">
                        <label className="col-sm-1 control-label">标题</label>
                        <div className="col-sm-5">
                            <input type="text" value={this.state.article.title ? this.state.article.title : ''} onChange={this.handleInputChange} name="title" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-1 control-label">关键字</label>
                        <div className="col-sm-8">
                            <input type="text" value={this.state.article.keywords ? this.state.article.keywords : ''} onChange={this.handleInputChange} name="keywords" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-1 control-label">摘要</label>
                        <div className="col-sm-8">
                            <textarea value={this.state.article.abstractContent ? this.state.article.abstractContent : ''} onChange={this.handleInputChange} name="abstractContent" rows="4" cols="20" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-1 control-label">图片URL</label>
                        <div className="col-sm-6">
                            <input type="text" value={this.state.article.pictureUrl ? this.state.article.pictureUrl : ''} onChange={this.handleInputChange} name="pictureUrl" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-1 control-label">分类</label>
                        <div className="col-sm-8">
                            <input type="text" value={this.state.article.categories ? this.state.article.categories : ''} onChange={this.handleInputChange} name="categories" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-1 control-label">发布</label>
                        <div className="col-sm-3">
                            <span className="form-control">{this.state.article.published ? '发布于' + this.state.article.publishedDate : '未发布'}</span>
                        </div>
                    </div>

                    <div>
                        <MarkdownEditor inputTitle="mdBody" mdBody={this.state.article.mdBody} previewTitle="预览" htmlBody={this.state.article.htmlBody} onMarkdownChange={this.onMarkdownChange}/>
                    </div>

                    <div className="form-group">
                        <div className="col-sm-offset-2 col-sm-10">
                            <button type="submit" className="btn btn-default">更新</button>
                        </div>
                    </div>
                </form>
            </div>
        )
    }
}

export default Article