import React, { Component } from 'react';
import Axios from 'axios'
import './FileService.css'
import NavLink from "../NavLink";

class FileService extends Component {

    constructor(props) {
        super(props);
        this.state = { fileUrls: [] }
    }

    componentDidMount() {
        Axios.get('/api/files')
            .then(response => {
                this.setState({fileUrls: response.data})
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        const fileList = this.state.fileUrls.map(fileUrl =>
            <li key={fileUrl}>
                <a href={fileUrl}>{fileUrl}</a>
            </li>
        );
        return (
            <div>
                <h2>文件服务</h2>
                <ul>
                    {fileList}
                </ul>
            </div>
        )
    }

}

export default FileService