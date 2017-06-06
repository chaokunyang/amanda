import React, { Component } from 'react';
import Dropzone from 'react-dropzone'
import './Accept.css'
import Axios from 'axios'

class Accept extends Component {
    constructor() {
        super();
        this.state = {
            accepted: [],
            rejected: [],
            uploaded: []
        }
    }

    onDrop(accepted, rejected) {
        this.setState({ accepted, rejected });

    }

    onUpload() {
        let data = new FormData();

        this.state.accepted.forEach((file, i) => {
            data.append('files', file);
        });

        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        };

        return Axios.post('/api/files', data, config)
            .then(response => {
                this.setState({
                    uploaded: response.data,
                    accepted: [],
                    rejected: []
                });
                this.props.getFileList();
            })
            .catch(error => console.log(error))
    }

    onCancel() {

    }

    render() {
        const accepted = (
            <ul>
                {
                    this.state.accepted.map(file =>
                        <li key={file.name} className="file-item">
                            <div><img src={file.preview} /></div>
                            <div>{file.name}</div>
                            <div>{file.size / 1024} KB</div>
                        </li>
                    )
                }
            </ul>
        );
        const uploaded = (
            <ul>
                {
                    this.state.uploaded.map(file =>
                        <li key={file.name} className="file-item">
                            <div><a href={file.url}><img src={file.url} /></a></div>
                            <div>{file.name}</div>
                            <div>{file.size / 1024} KB</div>
                        </li>
                    )
                }
            </ul>
        );
        return (
            <section className="Accept">
                <div className="dropzone">
                    <Dropzone
                        accept="image/jpeg, image/png"
                        onDrop={ this.onDrop.bind(this) }
                    >
                        <p>Try dropping some files here, or click to select files to upload.</p>
                        <p>Only *.jpeg and *.png images will be accepted</p>
                    </Dropzone>
                </div>
                <button onClick={this.onUpload.bind(this)}>开始上传</button>
                <button onClick={this.onCancel.bind(this)}>取消上传</button>
                <div className="files-list">
                    {accepted}
                    {uploaded}
                </div>
            </section>
        );
    }
}

export default Accept