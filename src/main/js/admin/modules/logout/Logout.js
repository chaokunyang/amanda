import React, { Component } from 'react';
import Http from '../http/Http'
import './Logout.css'

class Logout extends Component {

    constructor(props){
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();


        Http.post('/logout')
            .then(response => document.location.href = "/")
            .catch(error => {
                console.log(error);
        });
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="Logout">
                <button type="submit">退出登录</button>
            </form>
        )
    }

}

export default Logout