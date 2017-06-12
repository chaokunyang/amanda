import React, { Component } from 'react';
import Axios from 'axios'
import './FileService.css'
import Accept from './Accept'
import FileGrid from './FileGrid'
import NavBar from './NavBar'

class FileService extends Component {

    static extractPathFromUrl(url) {
        if(url.lastIndexOf("api/fs/dir/") !== -1) { // 目录
            return url.substring(url.lastIndexOf("api/fs/dir/") + "api/fs/dir/".length);
        }else { // 文件
            return url.substring(url.lastIndexOf("api/fs/file/") + "api/fs/file/".length);
        }
    }

    constructor(props) {
        super(props);
        this.state = {
            dirPath: "home",
            dirInfo: {
                dirs: [],
                files: []
            },
            selectedItems: {}
        };
        this.viewDir = this.viewDir.bind(this);
        this.goToDirPath = this.goToDirPath.bind(this);
        this.onSelect = this.onSelect.bind(this);
        this.onSelectAll = this.onSelectAll.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onDeleteSelected = this.onDeleteSelected.bind(this);
    }

    componentDidMount() {
        this.viewDir(this.state.dirPath);
    }

    goToDirPath(dirPath) {
        // setState可能是异步执行的
        this.setState(prevState => {
            this.viewDir(dirPath);
            return {
                dirPath: dirPath,
                selectedItems: {} // 进入新的目录时清空旧的已选中文件
            }
        });
    }

    viewDir(dirPath) {
        Axios.get('/api/fs/dir/' + dirPath)
            .then(response => {
                this.setState({dirInfo: response.data})
            })
            .catch(error => {
                console.log(error);
            });
    }

    onSelect(path) {
        this.setState(prevState => {
            prevState.selectedItems[path] = !prevState.selectedItems[path];
            return prevState;
        })
    };

    onSelectAll() {
        const selectedItems = {};
        this.state.dirInfo.dirs.forEach(dir => {
            const path = FileService.extractPathFromUrl(dir.url);
            selectedItems[path] = !selectedItems[path]
        });
        this.state.dirInfo.files.forEach(file => {
            const path = FileService.extractPathFromUrl(file.url);
            selectedItems[path] = !selectedItems[path]
        });
    }

    onDelete(path) {
        const data = [];
        data.push(path);
        Axios.post('/api/fs/delete', data)
            .then(response => {
                if(this.state.selectedItems.length === this.state.dirInfo.dirs.length + this.state.dirInfo.files.length) {
                    this.goToDirPath("");
                }else {
                    this.goToDirPath(this.state.dirPath);
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    onDeleteSelected() {
        const data = [];
        this.state.selectedItems.forEach(path => {
            data.push(path);
        });
        Axios.post('/api/fs/delete', data)
            .then(response => {
                if(this.state.selectedItems.length === this.state.dirs.length + this.state.files.length) {
                    this.goToDirPath("/");
                }else {
                    this.goToDirPath(this.state.dirPath);
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        return (
            <div className="FileService">
                <h2>文件服务</h2>
                <Accept getFileList={this.viewDir}/>
                <NavBar dirPath={this.state.dirPath} goToDirPath={this.goToDirPath} onSelectAll={this.onSelectAll} />
                <FileGrid dirInfo={this.state.dirInfo} viewDir={this.viewDir} goToDirPath={this.goToDirPath} onSelect={this.onSelect} selectedItems={this.state.selectedItems} onDelete={this.onDelete} />
            </div>
        )
    }

}

export default FileService