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
            uploaded: [],
        };

        this.onChange = this.onChange.bind(this);
        this.onCancel = this.onCancel.bind(this);
    }

    onDrop(accepted, rejected) {
        this.setState({ accepted, rejected });
    }

    onChange(e) {
        this.setState({
            uploadToDir: e.target.value
        })
    }

    onUpload() {
        let data = new FormData();

        this.state.accepted.forEach((file, i) => {
            data.append('files', file);
        });
        if(this.state.uploadToDir) {
            data.append('filepath', this.state.uploadToDir);
        }

        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        };

        return Axios.post('/api/fs', data, config)
            .then(response => {
                this.setState({
                    uploaded: response.data,
                    accepted: [],
                    rejected: []
                });
                this.props.viewDir();
            })
            .catch(error => console.log(error))
    }

    onCancel() {
        this.setState({
            accepted: [],
            rejected: []
        });
    }

    onClear() {
        this.setState({
            accepted: [],
            rejected: [],
            uploaded: []
        });
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
        const style = {
            width: '200px',
            height: '200px',
            borderWidth: '2px',
            borderColor: 'rgb(102, 102, 102)',
            borderStyle: 'dashed',
            borderRadius: '5px',
            padding: '0.3em'
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
            <section className="Accept">
                <div className="dropzone">
                    <Dropzone
                        ref={(node) => { this.dropzoneRef = node; }}
                        accept="image/jpeg, image/png"
                        onDrop={ this.onDrop.bind(this)}
                        style={style}
                    >
                        <p>拖动文件到这里或者点击选择文件上传</p>
                        <p>只接受*.jpeg 和 *.png格式图片</p>
                    </Dropzone>
                </div>
                <div className="upload-option">
                    <button className="btn btn-success upload" onClick={() => { this.dropzoneRef.open() }}>选择文件</button>

                    <div className="upload-dir form-group">
                        <label className="sr-only" htmlFor="uploadToDir">上传目录</label>
                        <div className="input-group">
                            <div className="input-group-addon">上传目录</div>
                            <input type="text" className="form-control" id="uploadToDir" placeholder="Ex: home/favorite" onChange={this.onChange} value={this.state.uploadToDir} />
                        </div>
                    </div>

                    <button className="btn btn-primary upload" onClick={this.onUpload.bind(this)}>开始上传</button>
                    <button className="btn btn-warning cancel" onClick={this.onCancel.bind(this)}>取消上传</button>
                    <button className="btn btn-info clear" onClick={this.onClear}>清空列表</button>
                </div>
                <div className="files-list">
                    {accepted}
                    {uploaded}
                </div>
            </section>
        );
    }
}

export default Accept