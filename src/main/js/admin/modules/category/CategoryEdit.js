import React, { Component } from 'react';
import moment from 'moment';

class CategoryEdit extends Component{

    constructor(props) {
        super(props);
        const newCategory = Object.assign({}, props.category);
        this.state = {
            newCategory: newCategory
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === "checkbox" ? target.checked : target.value;
        const name = target.name;

        this.setState((prevState, props) => {
            let newCategory = prevState.newCategory;
            newCategory[name] = value;
            newCategory
        })
    }

    handleSave(e) {
        e.preventDefault();
        this.props.onEditSave(this.state.newCategory, this.props.index)
    }

    handleCancel(e) {
        e.preventDefault();
        this.props.onEditCancel(this.props.index)
    }

    render() {
        return (
            <tr key={this.state.newCategory.id} className="CategoryEdit">
                <td><input type="text" name="name" className="form-control" value={this.state.newCategory.name} onChange={this.handleInputChange}/></td>
                <td><input type="text" name="zhName" className="form-control" value={this.state.newCategory.zhName} onChange={this.handleInputChange}/></td>
                <td>{this.props.category.level}</td>
                <td><input type="number" name="orderNumber" min="0" className="form-control" value={this.state.newCategory.orderNumber} onChange={this.handleInputChange}/></td>
                <td>{moment(this.props.category.dateCreated).format('YYYY/MM/DD HH:mm:ss')}</td>
                <td>{moment(this.props.category.dateModified).format('YYYY/MM/DD HH:mm:ss')}</td>
                <td>
                    <button className="btn btn-info" onClick={(e) => this.handleClick(e, this.props.category)}>{this.props.category.children.length}</button>
                </td>
                <td className="categories-options">
                    <button className="btn btn-primary btn-sm" onClick={this.handleSave}>保存</button>
                    <button className="btn btn-warning btn-sm" onClick={this.handleCancel}>取消</button>
                </td>
            </tr>
        );
    }

}

export default CategoryEdit;