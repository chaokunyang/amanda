import React, {Component} from 'react'

const FileList = (props) => {
    const dirs = props.dirInfo.dirs.map(dir =>
        <li key={dir.url} className="dir">
            <a href={dir.url}>
                <div><i className="fa fa-folder fa-4" aria-hidden="true"></i></div>
                <div>{dir.name}</div>
                <div>{dir.dateModified}</div>
            </a>
        </li>
    );
    const files = props.dirInfo.files.map(file =>
        <li key={file.url} className="file">
            <a href={file.url}>
                <div>
                    <img src={file.url}/>
                </div>
                <div>{file.name}</div>
                <div>{file.size}</div>
                <div>{file.dateModified}</div>
            </a>
        </li>
    );

    return (
        <div className="file-list">
            <ul>
                {dirs}
                {files}
            </ul>
        </div>
    )
};

export default FileList;