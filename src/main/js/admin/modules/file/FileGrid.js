import React, {Component} from 'react'
import FileService from './FileService'

class FileGrid extends Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(e) {
        e.preventDefault();
        const dirPath = FileService.extractPathFromUrl(e.currentTarget.href);
        this.props.goToDirPath(dirPath);
    };

    handleSelect(e, path) {
        this.props.onSelect(path);
    };

    handleDelete(e, path) {
        this.props.onDelete(path);
    };

    render() {
        const dirs = this.props.dirInfo.dirs.map(dir => {
            const path = FileService.extractPathFromUrl(dir.url);
            return (
                <li key={dir.url} className={"dir " + (this.props.selectedItems[path] ? "selected" : "")}>
                    <div className="option">
                        <i className={"fa " + (this.props.selectedItems[path] ? "fa-check-square-o" : "fa-check-circle")} aria-hidden="true" onClick={(e) => this.handleSelect(e, path)}/>&nbsp;
                        <i className="fa fa-trash-o" aria-hidden="true" onClick={(e) => this.handleDelete(e, path)}/>
                    </div>
                    <a href={dir.url} title={dir.name} onClick={this.handleClick}>
                        <div className="folder"><i className="fa fa-folder" aria-hidden="true"/></div>
                        <div className="name">{dir.name}</div>
                    </a>
                </li>
            );
        });
        const files = this.props.dirInfo.files.map(file => {
            const path = FileService.extractPathFromUrl(file.url);
            return (
                <li key={file.url} className={"file " + (this.props.selectedItems[path] ? "selected" : "")}>
                    <div className="option">
                        <i className={"fa " + (this.props.selectedItems[path] ? "fa-check-square-o" : "fa-check-circle")} aria-hidden="true" onClick={(e) => this.handleSelect(e, path)}/>&nbsp;
                        <i className="fa fa-trash-o" aria-hidden="true" onClick={(e) => this.handleDelete(e, path)}/>
                    </div>
                    <a href={file.url} title={file.name}>
                        <div className="img">
                            <img src={file.url}/>
                        </div>
                        <div className="name">{file.name}</div>
                    </a>
                </li>
            );
        });

        return (
            <div className="file-grid">
                <ul>
                    {dirs}
                    {files}
                </ul>
            </div>
        )
    }
}

export default FileGrid;