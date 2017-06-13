import React, {Component} from 'react'

class NavBar extends Component {
    constructor(props) {
        super(props);
    }

    handleClick(e, dirPath) {
        e.preventDefault();
        this.props.goToDirPath(dirPath);
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
            <ol className="NavBar">
                {navDirs}
            </ol>
        )
    }
}

export default NavBar;