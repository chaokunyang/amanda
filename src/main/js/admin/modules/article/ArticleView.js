import React, { Component } from 'react'
import moment from 'moment';

import './Article.css'

function ArticleView(props) {
    return (
        <div className="ArticleView container-fluid">
            <h2>{props.article.title}</h2>
            <div className="row">
                <div className="row-title">标题</div>
                <div className="col-sm-5 row-content">
                    {props.article.title}
                </div>
            </div>
            <div className="row">
                <div className="row-title">分类</div>
                <div className="col-sm-8 row-content">
                    {props.article.categories}
                </div>
            </div>
            <div className="row">
                <div className="row-title">关键字</div>
                <div className="col-sm-8 row-content">
                    {props.article.keywords}
                </div>
            </div>
            <div className="row">
                <div className="row-title">图片URL</div>
                <div className="col-sm-6 row-content">
                    {props.article.pictureUrl}
                </div>
            </div>
            <div className="row">
                <div className="row-title">摘要</div>
                <div className="col-sm-8 row-content">
                    {props.article.abstractContent}
                </div>
            </div>
            <div className="row">
                <div className="row-title">发布状态</div>
                <div className="col-sm-3 row-content">
                    {props.article.published ? '发布于 ' + moment(props.article.publishedDate).format('YYYY/MM/DD HH:mm:ss') : '未发布'}
                </div>
            </div>

            <div className="row">
                <div className="row-title">文章内容</div>
                <div className="col-sm-8 row-content" dangerouslySetInnerHTML={{__html: props.article.htmlBody}}/>
            </div>

            <div className="row buttons">
                <button className="btn btn-primary" onClick={props.setView}>编辑</button>
                <button className="btn btn-info" onClick={props.publish}>发布</button>
                <button className="btn btn-warning" onClick={props.cancelPublish}>取消发布</button>
            </div>
        </div>
    )
}

export default ArticleView