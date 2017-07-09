import React, { Component } from 'react';
import Axios from 'axios'
import moment from 'moment';
import Navbar from './NavBar';
import CategoryEdit from './CategoryEdit';
import CreateCategory from './CreateCategory';
import './Category.css'

class Categories extends Component {

    constructor(props) {
        super(props);
        this.state = {
            category: {
                children: []
            },
            categoryHistory: [{}],
            editStatus: {},
            newCategory: {
                name: "",
                zhName: "",
                level: 0,
                parentId: 0,
                orderNumber: 0
            }
        };
        this.handleClick = this.handleClick.bind(this);
        this.handleNavbarClick = this.handleNavbarClick.bind(this);
        this.handleEditClick = this.handleEditClick.bind(this);
        this.onEditSave = this.onEditSave.bind(this);
        this.onEditCancel = this.onEditCancel.bind(this);
        this.handleDeleteClick = this.handleDeleteClick.bind(this);
        this.onCreate = this.onCreate.bind(this);
        this.onCreateChange = this.onCreateChange.bind(this);
        this.onCreateCancel = this.onCreateCancel.bind(this);
    }

    componentDidMount() {
        this.loadCategories();
    }

    loadCategories() {
        Axios.get('/api/categories')
            .then(response => {
                this.setState({
                    category: response.data,
                    categoryHistory: [response.data],
                    newCategory: {
                        name: "",
                        zhName: "",
                        level: response.data.level + 1,
                        parentId: response.data.id,
                        orderNumber: response.data.children.length
                    }
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
                editStatus: {},
                newCategory: {
                    name: "",
                    zhName: "",
                    level: category.level + 1,
                    parentId: category.id,
                    orderNumber: category.children.length
                }
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

    onCreate() {
        Axios.post(`/api/categories`, this.state.newCategory)
            .then(response => {
                const c = response.data;
                let insertPoint = -1;

                for(let i = 0; i < this.state.category.children.length; i++) {
                    let item = this.state.category.children[i];
                    if(item.orderNumber >= c.orderNumber) {
                        insertPoint = i;
                    }
                }

                if(insertPoint === -1) {
                    insertPoint = this.state.category.children.length - 1;
                }

                this.setState(prevState => {
                    prevState.category.children.splice(insertPoint, 0, c);
                    return {
                        category: prevState.category,
                        newCategory: {
                            name: "",
                            zhName: "",
                            level: prevState.category.level + 1,
                            parentId: prevState.category.id,
                            orderNumber: prevState.category.children.length
                        }
                    }
                })

            })
            .catch(error => {
                console.log(error);
            });
    }

    onCreateChange(newCategory) {
        this.setState({newCategory});
    }

    onCreateCancel() {
        this.setState( {
            newCategory: {
                name: "",
                zhName: "",
                level: this.state.category.level + 1,
                parentId: this.state.category.id
            }
        })

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
                            <button className="btn btn-info" onClick={(e) => this.handleClick(e, category)}>{category.children ? category.children.length : 0}</button>
                        </td>
                        <td className="categories-options">
                            <button className="btn btn-primary btn-sm" onClick={(e) => this.handleEditClick(e, index)}>编辑</button>
                            <button className="btn btn-danger btn-sm" onClick={(e) => this.handleDeleteClick(e, category.id)}>删除</button>
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
                            <th className="category-level">类目层级</th>
                            <th className="order-number">排序编号</th>
                            <th className="create-time">创建时间</th>
                            <th className="update-time">修改时间</th>
                            <th className="children">子类目</th>
                            <th className="categories-options">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        {trs}
                        <CreateCategory key={'create'} newCategory={this.state.newCategory} onCreate={this.onCreate} onCreateChange={this.onCreateChange} onCreateCancel={this.onCreateCancel} />
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default Categories;