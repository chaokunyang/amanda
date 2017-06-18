import React, {Component} from 'react'

class NavBar extends Component {
    constructor(props) {
        super(props);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
    }

    handleClick(e, dirPath) {
        e.preventDefault();
        this.props.goToDirPath(dirPath);
    }

    handleInputChange(event) {
        this.props.onSelectAll(event.target.checked)
    }

    handleDelete(event) {
        this.props.onDeleteSelected();
    }

    render() {
        const currentDirPath = this.props.dirPath;
        const navItems = [];
        currentDirPath.split("/").forEach((dir, index) => {
            if (index === 0) {
                navItems.push({name: "全部文件", dirPath: dir});
                return;
            }
            const dirPath = navItems[navItems.length - 1].dirPath + "/" + dir;
            navItems.push({name: dir, dirPath: dirPath})
        });

        const navDirs = navItems.map((item, index) =>
            <li key={item.dirPath}>
                <a href={"/fs/dir/" + item.dirPath} onClick={(e) => this.handleClick(e, item.dirPath)}>{item.name}</a>
                <span className="separator">{index !== navItems.length -1 ? "/" : ""}</span>
            </li>
        );

        return (
            <div className="NavBar">
                <ol>
                    {navDirs}
                </ol>
                <div className="checkbox nav-option">
                    <label>
                        <input type="checkbox" checked={this.props.allChecked} onChange={this.handleInputChange}/>
                        <span> 全选</span>
                    </label>
                </div>
                <button type="button" className="nav-option btn btn-danger btn-xs" onClick={this.handleDelete}>删除所选项</button>
            </div>
        )
    }
}

export default NavBar;