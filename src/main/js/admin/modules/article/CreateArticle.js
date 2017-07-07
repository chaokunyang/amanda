import React, { Component } from 'react'
import Axios from 'axios'
import history from '../History'
import MarkdownEditor from '../markdown/MarkdownEditor'
import './Article.css'
import ArticleCategoryEdit from './ArticleCategoryEdit'

class CreateArticle extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            view: true
        };
        this.onMarkdownChange = this.onMarkdownChange.bind(this);
        this.handleSubmit  = this.handleSubmit.bind(this);
        this.handleInputChange  = this.handleInputChange .bind(this);
        this.handleCategorySelect  = this.handleCategorySelect .bind(this);
    }

    componentDidMount() {
        Axios.all([this.getCategory()]).then(Axios.spread(function (categoryTree) {
            this.setState({categoryTree: categoryTree.data});
        }.bind(this)));
    }

    getCategory() {
        return Axios.get('/api/categories');
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
                history.push(`/articles/${response.data.id}/${response.data.title}`)
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

    handleCategorySelect(id) {
        this.setState(prevState => {
            const article = Object.assign({}, prevState.article);
            article.categoryId = id;
            return {article: article}
        });
    }
    
    render() {
        return (
            <div className="CreateArticle ArticleEdit">
                <form onSubmit={this.handleSubmit} className="form-horizontal">
                    <h2>新建文章</h2>
                    <div className="form-group">
                        <label className="control-label">标题</label>
                        <div className="col-sm-5">
                            <input type="text" value={this.state.article.title ? this.state.article.title : ''} onChange={this.handleInputChange} name="title" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="control-label">关键字</label>
                        <div className="col-sm-8">
                            <input type="text" value={this.state.article.keywords ? this.state.article.keywords : ''} onChange={this.handleInputChange} name="keywords" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="control-label">摘要</label>
                        <div className="col-sm-8">
                            <textarea value={this.state.article.abstractContent ? this.state.article.abstractContent : ''} onChange={this.handleInputChange} name="abstractContent" rows="8" cols="20" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="control-label">图片URL</label>
                        <div className="col-sm-6">
                            <input type="text" value={this.state.article.pictureUrl ? this.state.article.pictureUrl : ''} onChange={this.handleInputChange} name="pictureUrl" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="control-label">分类</label>
                        <div className="col-sm-8">
                            {this.state.categoryTree ?
                                <ArticleCategoryEdit categoryId={this.state.categoryTree.id} categoryTree={this.state.categoryTree}  handleCategorySelect={this.handleCategorySelect}/> : ''}
                        </div>
                    </div>

                    <div className="form-group">
                        <label className="control-label">文章内容</label>
                        <div className="markdown-container">
                            <MarkdownEditor inputTitle="markdown" mdBody={this.state.article.mdBody} previewTitle="预览" htmlBody={this.state.article.htmlBody} onMarkdownChange={this.onMarkdownChange}/>
                        </div>
                    </div>

                    <div className="form-group buttons">
                        <button type="submit" className="btn btn-primary">创建</button>
                    </div>
                </form>
            </div>
        )
    }
}

export default CreateArticle