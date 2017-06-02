import React, { Component } from 'react';
import Sidebar from './Sidebar';
import Header from './Header';
import '../../../resources/static/assets/css/admin/App.css';


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