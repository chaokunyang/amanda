import React, { Component } from 'react'
import moment from 'moment';
import MarkdownEditor from '../markdown/MarkdownEditor'
import './Article.css'
import ArticleCategoryEdit from './ArticleCategoryEdit'

function ArticleEdit(props) {
    return (
        <div className="ArticleEdit">
            <form onSubmit={props.handleSubmit} className="form-horizontal">
                <h2>{props.article.title}</h2>
                <div className="form-group">
                    <label className="control-label">标题</label>
                    <div className="col-sm-5">
                        <input type="text" value={props.article.title ? props.article.title : ''} onChange={props.handleInputChange} name="title" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="control-label">分类</label>
                    <div className="col-sm-8">
                        <ArticleCategoryEdit categoryId={props.article.category ? props.article.category.id : props.categoryTree.id} categoryTree={props.categoryTree} handleCategorySelect={props.handleCategorySelect}/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="control-label">摘要</label>
                    <div className="col-sm-8">
                        <textarea value={props.article.abstractContent ? props.article.abstractContent : ''} onChange={props.handleInputChange} name="abstractContent" rows="8" cols="20" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="control-label">关键字</label>
                    <div className="col-sm-8">
                        <input type="text" value={props.article.keywords ? props.article.keywords : ''} onChange={props.handleInputChange} name="keywords" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="control-label">图片URL</label>
                    <div className="col-sm-6">
                        <input type="text" value={props.article.pictureUrl ? props.article.pictureUrl : ''} onChange={props.handleInputChange} name="pictureUrl" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="control-label">发布状态</label>
                    <div className="col-sm-3">
                        <span className="publish">{props.article.published ? '发布于 ' + moment(props.article.publishedDate).format('YYYY/MM/DD HH:mm:ss') : '未发布'}</span>
                    </div>
                </div>

                <div className="form-group">
                    <label className="control-label">文章内容</label>
                    <div className="markdown-container">
                        <MarkdownEditor inputTitle="markdown" mdBody={props.article.mdBody} previewTitle="预览" htmlBody={props.article.htmlBody} onMarkdownChange={props.onMarkdownChange}/>
                    </div>
                </div>

                <div className="form-group buttons">
                    <button type="submit" className="btn btn-primary">更新</button>
                    <button type="button" className="btn btn-success" onClick={props.setView}>查看</button>
                </div>
            </form>
        </div>
    )
}

export default ArticleEdit