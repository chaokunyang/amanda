import React, { Component } from 'react'
import Axios from 'axios'
import './Article.css'

class ArticleCategoryEdit extends Component {

    constructor(props) {
        super(props);
        this.state = {
            categoryTree: {
                children: []
            },
            path: [],
            selects: []
        };

        this.handleSelect = this.handleSelect.bind(this);
    }

    componentDidMount() {
        Axios.all([this.getPath()]).then(Axios.spread(function (path, categoryTree) {
            this.setState({path: path.data});
            this.setState({categoryTree: categoryTree.data});
        }.bind(this)));
    }

    getPath() {
        return Axios.get('/api/categories/path/' + this.props.categoryId);
    }

    handleSelect(event) {
        event.preventDefault();
        const data = event.target.value.split('_');
        const level = Number(data[0]);
        const id = Number(data[1]);
        const index = level + 1; // level + 1表示当前元素

        this.setState(prevState => {
            let path = prevState.path;
            // index表示当前元素，在splice中加一才表示删除之后的元素
            path.splice(index + 1, prevState.path.length - index);

            // 上一次点击的splice可能会使path变短，下一次选择尚未选择的下拉框时path[index]就会不存在
            if(!path[index]) {
                path.push({
                    level: index + 1,
                });
            }
            path[index].id = id;

            return {path}
        });
        this.props.handleCategorySelect(id);
    }

    render() {
        let parent = this.props.categoryTree;
        const selects = [];
        if(parent && parent.children && parent.children.length > 0
           && this.state.path && this.state.path.length > 0)
        {
            for(let i = 0; i < this.state.path.length - 1; i++) {
                let selected = null;
                const options = parent.children.map(item => {
                    // parent从根节点开始，path、从根节点开始，根节点level=-1，第一级子目录在path中的index是1，故"+2"
                    if(this.state.path[parent.level + 2].id === item.id) {
                        selected = item;
                    }
                    return (
                        <option value={item.level + '_' + item.id} key={item.id}>{item.zhName}</option>
                    )
                });
                selects.push(
                    <select value={selected.level + '_' + selected.id} key={selected.id} onChange={this.handleSelect} className="form-control">
                        <option>选择分类</option>
                        {options}
                    </select>
                );

                parent = selected;
            }

            if(parent.children.length > 0) {
                const options = parent.children.map(item => {
                    return (
                        <option value={item.level + '_' + item.id} key={item.id}>{item.zhName}</option>
                    )
                });
                let value;
                if(this.state.path[parent.level + 2]) {
                    value = this.state.path[parent.level + 2].level + '_' + this.state.path[parent.level + 2].id
                }else {
                    value = -1
                }
                selects
                    .push(
                        <select value={value} key={parent.level + 2} onChange={this.handleSelect} className="form-control">
                            <option>选择分类</option>
                            {options}
                        </select>
                    )
            }
        }

        return (
            <div className="ArticleCategoryEdit">
                {selects}
            </div>
        )
    }

}

export default ArticleCategoryEdit;