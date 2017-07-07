import React, { Component } from 'react'
import { Link } from 'react-router'
import NavLink from '../NavLink'
import Pagination from '../pagination/Pagination'
import Axios from 'axios'
import moment from 'moment';
import './Article.css'
import ArticleCategoryView from './ArticleCategoryView';

class Articles extends Component {

    constructor(props) {
        super(props);
        this.state = {
            articles: [],
            pagination: {},
            pageNumber: 0,
            pageSize: 3
        };

        this.onPageChange = this.onPageChange.bind(this);
        this.onDelete = this.onDelete.bind(this);
    }

    // componentDidMount() {
    //     this.loadArticles(this.state.pageNumber, this.state.pageSize);
    // }

    componentDidMount() {
        Axios.all([this.getArticles(this.state.pageNumber, this.state.pageSize), this.getCategory()]).then(Axios.spread(function (articlesResp, categoryTree) {

            const articles = articlesResp.data.content;
            delete articlesResp.data.content;
            const pagination = articlesResp.data;

            this.setState({
                articles: articles,
                categoryTree: categoryTree.data,
                pagination: pagination
            });

        }.bind(this)));
    }

    getArticles(pageNumber, pageSize) {
        return Axios.get(`/api/articles?page=${pageNumber}&size=${pageSize}`)
    }

    getCategory() {
        return Axios.get('/api/categories');
    }

    loadArticles(pageNumber, pageSize) {
        Axios.get( `/api/articles?page=${pageNumber}&size=${pageSize}`)
            .then(response => {
                const articles = response.data.content;
                delete response.data.content;
                const pagination = response.data;

                this.setState({
                    articles: articles,
                    pagination: pagination
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    onPageChange(page) {
        this.setState({
            pageNumber: page
        });
        this.loadArticles(page, 3);
    }

    onDelete(event, id) {
        Axios.delete(`/api/articles/${id}`)
            .then(response => this.loadArticles(this.state.pageNumber, this.state.pageSize))
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        const trs = this.state.articles.map(article =>
            <tr key={article.id}>
                <td className="title" title={article.title}>
                    {
                        article.title && article.title.length > 30 ?
                        article.title.substr(0, 30) + '...' :
                        article.title
                    }
                    </td>
                <td className="categories">
                    {/*{article.category ? article.category.zhName : ""}*/}
                    <ArticleCategoryView categoryId={article.category ? article.category.id : this.state.categoryTree.id} categoryTree={this.state.categoryTree} />

                </td>
                <td className="keywords" title={article.keywords}>
                    {
                        article.keywords && article.keywords.length > 30 ?
                            article.keywords.substr(0, 30) + '...' :
                            article.keywords
                    }
                </td>
                <td className="picture">
                    {article.pictureUrl ? <img src={article.pictureUrl}/> : ""}
                </td>
                <td className="abstract" title={article.abstractContent}>
                    {
                        article.abstractContent && article.abstractContent.length > 60 ?
                            article.abstractContent.substr(0, 60) + '...' :
                            article.abstractContent
                    }
                </td>
                <td className="publish-status">{article.published ? '发布于 ' + moment(article.publishedDate).format('YYYY/MM/DD HH:mm:ss') : '未发布'}</td>
                <td className="views">{article.views}</td>
                <td className="option">
                    <NavLink to={ "articles/" + article.id + "/" + article.title}>
                        <button className="btn btn-default">查看</button>
                    </NavLink>
                    <button className="btn btn-danger" onClick={(e) => this.onDelete(e, article.id)}>删除</button>
                </td>
            </tr>
        );

        return(
            <div className="Articles">
                <h2>文章列表</h2>

                <Link to="/articles/new" className="createArticle">新建文章</Link>

                <div className="table-responsive">
                    <table className="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th className="title">标题</th>
                            <th className="categories">分类</th>
                            <th className="keywords">关键字</th>
                            <th className="picture">图片</th>
                            <th className="abstract">摘要</th>
                            <th className="publish-status">发布状态</th>
                            <th className="views">浏览次数</th>
                            <th className="option">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        {trs}
                        </tbody>
                    </table>
                </div>

                <div className="pagination-container">
                    <Pagination pagination={this.state.pagination} onPageChange={this.onPageChange}/>
                </div>

                {/*{this.props.children}*/}
            </div>
        )
    }
}

export default Articles