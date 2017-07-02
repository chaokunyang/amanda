import React, { Component } from 'react';
import Axios from 'axios'

class Category extends Component {

    constructor(props) {
        super(props);
        this.state = { categories: [] }
    }

    componentDidMount() {
        Axios.get('/api/categories')
            .then(response => {
                this.setState({
                    categories: response.data
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        return (
            <div className="Category">
                <h2>分类</h2>
                <div>
                    <div></div>
                    {/*{JSON.stringify(this.state.categories)}*/}
                </div>
            </div>
        )
    }
}

export default Category;