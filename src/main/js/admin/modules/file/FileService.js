import React, { Component } from 'react';
import Axios from 'axios'
import './FileService.css'
import FullScreen from './upload/FullScreen'
import FileGrid from './FileGrid'
import NavBar from './NavBar'

class FileService extends Component {

    static extractPathFromUrl(url) {
        if(url.lastIndexOf("fs/dir/") !== -1) { // 目录
            return url.substring(url.lastIndexOf("fs/dir/") + "fs/dir/".length);
        }else { // 文件
            return url.substring(url.lastIndexOf("fs/file/") + "fs/file/".length);
        }
    }

    static getParentPath(dirPath) {
        if("home" === dirPath) {
            return "home";
        }
        return dirPath.substring(0, dirPath.lastIndexOf("/"));
    }

    constructor(props) {
        super(props);
        this.state = {
            dirPath: "home",
            dirInfo: {
                dirs: [],
                files: []
            },
            selectedItems: {},
            uploadToDir: "home",
            allChecked: false
        };
        this.viewDir = this.viewDir.bind(this);
        this.goToDirPath = this.goToDirPath.bind(this);
        this.onSelect = this.onSelect.bind(this);
        this.onSelectAll = this.onSelectAll.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.onDeleteSelected = this.onDeleteSelected.bind(this);
        this.selectUploadDir = this.selectUploadDir.bind(this);
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
                selectedItems: {}, // 进入新的目录时清空旧的已选中文件
                uploadToDir: dirPath,
                allChecked: false
            }
        });
    }

    viewDir(dirPath) {
        Axios.get('/fs/dir/' + dirPath)
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

    onSelectAll(allChecked) {
        const selectedItems = {};
        this.state.dirInfo.dirs.forEach(dir => {
            const path = FileService.extractPathFromUrl(dir.url);
            selectedItems[path] = allChecked
        });
        this.state.dirInfo.files.forEach(file => {
            const path = FileService.extractPathFromUrl(file.url);
            selectedItems[path] = allChecked
        });
        this.setState(prevState => {
            prevState.selectedItems = selectedItems;
            prevState.allChecked = allChecked;
            return prevState;
        })
    }

    onDelete(path) {
        const data = [];
        data.push(path);
        Axios.post('/fs/delete', data)
            .then(response => {
                if(1 === this.state.dirInfo.dirs.length + this.state.dirInfo.files.length) {
                    this.goToDirPath(FileService.getParentPath(this.state.dirPath));
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
        let selectedItemsLength = 0;
        for(let prop in this.state.selectedItems) {
            if(this.state.selectedItems[prop] === true)
                data.push(prop);
            selectedItemsLength++;
        }
        Axios.post('/fs/delete', data)
            .then(response => {

                this.setState({
                    allChecked: false
                });

                if(selectedItemsLength === this.state.dirInfo.dirs.length + this.state.dirInfo.files.length) {
                    this.goToDirPath(FileService.getParentPath(this.state.dirPath));
                }else {
                    this.goToDirPath(this.state.dirPath);
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    selectUploadDir(path) {
        this.setState({
            uploadToDir: path
        })
    }

    render() {
        return (
            <div className="FileService">
                <h2>文件服务</h2>
                <FullScreen getFileList={this.viewDir} dirPath={this.state.dirPath} goToDirPath={this.goToDirPath} uploadToDir={this.state.uploadToDir} selectUploadDir={this.selectUploadDir}/>
                <NavBar dirPath={this.state.dirPath} goToDirPath={this.goToDirPath} allChecked={this.state.allChecked} onSelectAll={this.onSelectAll} onDeleteSelected={this.onDeleteSelected} />
                <FileGrid dirInfo={this.state.dirInfo} viewDir={this.viewDir} goToDirPath={this.goToDirPath} onSelect={this.onSelect} selectedItems={this.state.selectedItems} onDelete={this.onDelete} />
            </div>
        )
    }

}

export default FileService