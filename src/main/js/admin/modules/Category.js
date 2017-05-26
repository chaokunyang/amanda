import React, { Component } from 'react';
import Axios from 'axios'

class Category extends Component {

    constructor(props) {
        super(props);
        this.state = { categories: [] }
    }

    componentDidMount() {
        Axios.get('/api/categories')
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <div>
                <h2>分类</h2>
            </div>
        )
    }
}

export default Category;