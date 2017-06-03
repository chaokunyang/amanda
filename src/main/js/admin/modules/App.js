import React, { Component } from 'react';
import Sidebar from './sidebar/Sidebar';
import Header from './header/Header';
import './App.css';


class App extends Component {
    render() {
        return (
            <div className="App">
                <Header/>

                <div className="clearfix">
                    <Sidebar/>
                    <div className="App-main">
                        {this.props.children}
                    </div>
                </div>
            </div>
        )
    }
}
export default App