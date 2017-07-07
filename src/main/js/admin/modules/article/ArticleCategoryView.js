import React, { Component } from 'react'
import Axios from 'axios'
import './Article.css'

class ArticleCategoryView extends Component {

    constructor(props) {
        super(props);
        this.state = {
            categoryTree: {
                children: []
            },
            path: []
        };
    }

    componentDidMount() {
        Axios.all([this.getPath()]).then(Axios.spread(function (path,) {
            this.setState({path: path.data});
        }.bind(this)));
    }

    getPath() {
        return Axios.get('/api/categories/path/' + this.props.categoryId);
    }

    render() {
        let parent = this.props.categoryTree;
        const categories = [];
        if(parent && parent.children && parent.children.length > 0
           && this.state.path && this.state.path.length > 0)
        {
            for(let i = 0; i < this.state.path.length - 1; i++) {
                let category = null;
                for(let i = 0; i < parent.children.length; i++) {
                    // parent从根节点开始，path、从根节点开始，根节点level=-1，第一级子目录在path中的index是1，故"+2"
                    if(this.state.path[parent.level + 2].id === parent.children[i].id) {
                        category = parent.children[i];
                        break;
                    }
                }

                categories.push(
                    <span key={category.level}>
                        <span>
                            {category.zhName}
                        </span>
                        <span className="separator">
                            {category.level + 2 !== this.state.path.length ? "/" : ""}
                        </span>
                    </span>
                );

                parent = category;
            }

        }

        return (
            <div className="ArticleCategoryView">
                {categories}
            </div>
        )
    }

}

export default ArticleCategoryView;