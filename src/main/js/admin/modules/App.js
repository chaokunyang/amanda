import React, { Component } from 'react';
import Sidebar from './Sidebar';
import Header from './Header';
// import '../../../resources/static/assets/css/admin/Admin.css';


class App extends Component {
    render() {
        return (
            <div className="App">
                <Header/>
                <Sidebar/>

                {this.props.children}
            </div>
        )
    }
}
export default App