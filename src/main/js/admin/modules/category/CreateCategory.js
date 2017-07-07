import React, { Component } from 'react';

class CreateCategory extends Component{

    constructor(props) {
        super(props);

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleCreate = this.handleCreate.bind(this);
        this.handleCreateCancel = this.handleCreateCancel.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === "checkbox" ? target.checked : target.value;
        const name = target.name;

        let category = Object.assign(this.props.newCategory);
        category[name] = value;

        this.props.onCreateChange(category);
    }

    handleCreate(e) {
        e.preventDefault();
        this.props.onCreate();
    }

    handleCreateCancel(e) {
        e.preventDefault();
        this.props.onCreateCancel();
    }

    render() {
        return (
            <tr className="CreateCategory">
                <td><input type="text" name="name" className="form-control" value={this.props.newCategory.name} onChange={this.handleInputChange}/></td>
                <td><input type="text" name="zhName" className="form-control" value={this.props.newCategory.zhName} onChange={this.handleInputChange}/></td>
                <td>{this.props.newCategory.level}</td>
                <td><input type="number" name="orderNumber" min="0" className="form-control" value={this.props.newCategory.orderNumber} onChange={this.handleInputChange}/></td>
                <td>/</td>
                <td>/</td>
                <td>
                    /
                </td>
                <td className="categories-options">
                    <button className="btn btn-default btn-sm" onClick={this.handleCreate}>新建</button>
                    <button className="btn btn-warning btn-sm" onClick={this.handleCreateCancel}>清除</button>
                </td>
            </tr>
        );
    }

}

export default CreateCategory;