import React, { Component } from 'react';
import Axios from 'axios'
import './FileService.css'
import Accept from './Accept'

class FileService extends Component {

    constructor(props) {
        super(props);
        this.state = { fileUrls: [] }
        this.getFileList = this.getFileList.bind(this);
    }

    componentDidMount() {
        this.getFileList();
    }

    getFileList() {
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
                <Accept getFileList={this.getFileList}/>
                <ul>
                    {fileList}
                </ul>
            </div>
        )
    }

}

export default FileService