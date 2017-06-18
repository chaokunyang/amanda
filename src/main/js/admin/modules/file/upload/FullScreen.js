import React, { Component } from 'react';
import Dropzone from 'react-dropzone'
import './Upload.css'
import Axios from 'axios'

class FullScreen extends Component {

    constructor() {
        super();
        this.state = {
            accept: 'image/jpeg, image/png, image/gif',
            files: [],
            uploaded: [],
            dropzoneActive: false,
        };

        this.onChange = this.onChange.bind(this);
        this.onCancel = this.onCancel.bind(this);
        this.onClear = this.onClear.bind(this);
        this.onSelect = this.onSelect.bind(this);
    }

    onDragEnter() {
        this.setState({
            dropzoneActive: true
        });
    }

    onDragLeave() {
        this.setState({
            dropzoneActive: false
        });
    }

    onDrop(files) {
        this.setState({
            files,
            dropzoneActive: false
        });
    }

    onUpload() {
        let data = new FormData();

        this.state.files.forEach((file, i) => {
            data.append('files', file);
        });
        if(this.props.uploadToDir) {
            data.append('filepath', this.props.uploadToDir);
        }

        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        };

        return Axios.post('/fs/', data, config)
            .then(response => {
                this.setState({
                    uploaded: response.data,
                    files: [],
                    rejected: []
                });
                if(this.props.uploadToDir) {
                    this.props.goToDirPath(this.props.uploadToDir);
                }else {
                    this.props.goToDirPath(`home/${new Date().toLocaleDateString().replace(/-/g, "/")}`);
                }
            })
            .catch(error => console.log(error))
    }

    onChange(e) {
        this.props.selectUploadDir(e.target.value);
    }

    onSelect(e) {
        this.props.selectUploadDir(e.target.value);
    }

    onCancel() {
        this.setState({
            files: [],
            rejected: []
        });
    }

    onClear() {
        this.setState({
            files: [],
            rejected: [],
            uploaded: []
        });
    }

    render() {
        const files = (
            <ul>
                {
                    this.state.files.map(file =>
                        <li key={file.name} className="file-item">
                            <div><img src={file.preview} /></div>
                            <div>{file.name}</div>
                            <div>{file.size / 1024} KB</div>
                        </li>
                    )
                }
            </ul>
        );
        const { accept, dropzoneActive } = this.state;

        const overlayStyle = {
            position: 'absolute',
            top: 0,
            right: 0,
            bottom: 0,
            left: 0,
            padding: '2.5em 0',
            background: 'rgba(0,0,0,0.5)',
            color: '#fff',
            zIndex: 1000
        };

        const uploaded = (
            <ul>
                {
                    this.state.uploaded.map(file =>
                        <li key={file.name} className="file-item">
                            <div><a href={file.url}><img src={file.url} /></a></div>
                            <div><a href={file.url}>{file.name}</a></div>
                            <div>{(file.size / 1024).toFixed(2)} KB</div>
                        </li>
                    )
                }
            </ul>
        );
        return (
        <Dropzone
            disableClick
            style={{}}
            accept={accept}
            ref={(node) => { this.dropzoneRef = node; }}
            onDrop={this.onDrop.bind(this)}
            onDragEnter={this.onDragEnter.bind(this)}
            onDragLeave={this.onDragLeave.bind(this)}
        >
            <section className="Upload FullScreen">
                { dropzoneActive && <div style={overlayStyle}><div className="prompt">拖拽文件...</div></div> }
                <div className="upload-option">
                    <div>
                        <div className="upload-dir form-group">
                            <label className="sr-only" htmlFor="uploadToDir">上传目录</label>
                            <div className="input-group">
                                <div className="input-group-addon">上传目录</div>
                                <input type="text" className="form-control" id="uploadToDir" placeholder="例如: home/favorite" onChange={this.onChange} value={this.props.uploadToDir} />
                            </div>
                        </div>
                        <div className="selectUploadDir-wrapper">
                            <select value={this.props.uploadToDir} onChange={this.onSelect} className="form-control selectUploadDir">
                                <option value={this.props.dirPath}>当前路径</option>
                                <option value={`home/${new Date().toLocaleDateString().replace(/-/g, "/")}`}>时间路径</option>
                            </select>
                        </div>
                        <button className="btn btn-success upload" onClick={() => { this.dropzoneRef.open() }}>选择文件</button>
                        <button className="btn btn-primary upload" onClick={this.onUpload.bind(this)}>开始上传</button>
                        <button className="btn btn-warning cancel" onClick={this.onCancel.bind(this)}>取消上传</button>
                        <button className="btn btn-info clear" onClick={this.onClear}>清空列表</button>
                    </div>
                </div>
                <div className="files-list">
                    {files}
                    {uploaded}
                </div>
            </section>
        </Dropzone>
        );
    }
}

export default FullScreen