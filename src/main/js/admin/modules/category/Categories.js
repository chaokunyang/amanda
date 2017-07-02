import React, { Component } from 'react';
import Axios from 'axios'
import moment from 'moment';
import Navbar from './NavBar';
import CategoryEdit from './CategoryEdit';
import './Category.css'

class Categories extends Component {

    constructor(props) {
        super(props);
        this.state = {
            category: {
                children: []
            },
            categoryHistory: [{}],
            editStatus: {}
        };
        this.handleClick = this.handleClick.bind(this);
        this.handleNavbarClick = this.handleNavbarClick.bind(this);
        this.handleEditClick = this.handleEditClick.bind(this);
        this.onEditSave = this.onEditSave.bind(this);
        this.onEditCancel = this.onEditCancel.bind(this);
        this.handleDeleteClick = this.handleDeleteClick.bind(this);
    }

    componentDidMount() {
        this.loadCategories();
    }

    loadCategories() {
        Axios.get('/api/categories')
            .then(response => {
                this.setState({
                    category: response.data,
                    categoryHistory: [response.data]
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleClick(event, category) {
        event.preventDefault();
        this.setState(prevState => {
            prevState.categoryHistory.push(category);
            return {
                categoryHistory: prevState.categoryHistory,
                category: category,
                editStatus: {}
            }
        })
    }

    onEditSave(category, index) {
        Axios.post(`/api/categories`, category)
            .then(response => {
                this.loadCategories();
                this.setState(prevState => {
                    prevState.editStatus[index] = false;
                    return {
                        editStatus: prevState.editStatus
                    }
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    onEditCancel(index) {
        this.setState(prevState => {
            prevState.editStatus[index] = false;
            return {
                editStatus: prevState.editStatus
            }
        })
    }

    handleEditClick(e, index) {
        e.preventDefault();
        this.setState(prevState => {
            prevState.editStatus[index] = true;
            return {
                editStatus: prevState.editStatus
            }
        })
    }

    handleDeleteClick(e, categoryId) {
        e.preventDefault();
        Axios.delete(`/api/categories/${categoryId}`)
            .then(response => {
                this.loadCategories();
                this.setState({
                    editStatus: {}
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleNavbarClick(event, category) {
        event.preventDefault();
        this.setState(prevState => {
            let categoryHistory = prevState.categoryHistory;
            for(let i = 0; i < categoryHistory.length; i++) {
                if(categoryHistory[i] === category) {
                    categoryHistory = categoryHistory.slice(0, i + 1);
                    break;
                }
            }
            return {
                categoryHistory: categoryHistory,
                category: category
            }
        })
    }

    render() {
        const trs = this.state.category.children.map((category, index) => {
            if(this.state.editStatus[index]) {
                return <CategoryEdit category={category} key={category.id} onEditSave={this.onEditSave} index={index} onEditCancel={this.onEditCancel}/>
            }else {
                return (
                    <tr key={category.id}>
                        <td>{category.name}</td>
                        <td>{category.zhName}</td>
                        <td>{category.level}</td>
                        <td>{category.orderNumber}</td>
                        <td>{moment(category.dateCreated).format('YYYY/MM/DD HH:mm:ss')}</td>
                        <td>{moment(category.dateModified).format('YYYY/MM/DD HH:mm:ss')}</td>
                        <td>
                            <button className="btn btn-info" onClick={(e) => this.handleClick(e, category)}>{category.children.length}</button>
                        </td>
                        <td className="categories-options">
                            <button className="btn btn-primary btn-sm" onClick={(e) => this.handleEditClick(e, index)}>编辑</button>
                            <button className="btn btn-danger btn-sm" onClick={(e) => this.handleDeleteClick(e, index)}>删除</button>
                        </td>
                    </tr>
                )
            }


        });
        return (
            <div className="Categories">
                <h2>分类</h2>
                <Navbar categoryHistory={this.state.categoryHistory} handleNavbarClick={this.handleNavbarClick} />
                <div className="table-responsive">
                    <table className="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th>分类名称</th>
                            <th>中文名称</th>
                            <th>类目层级</th>
                            <th>排序编号</th>
                            <th>修改时间</th>
                            <th>创建时间</th>
                            <th>子类目</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        {trs}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default Categories;