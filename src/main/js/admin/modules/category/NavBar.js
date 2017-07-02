import React, {Component} from 'react'

function Navbar(props) {
    const items = props.categoryHistory.map((item, index) =>
        <li key={item && item.id ? item.id : -1}>
            <span className="category-name" onClick={(e) => props.handleNavbarClick(e, item)}>{item.zhName}</span>
            <span className="separator">{index !== props.categoryHistory.length -1 ? "/" : ""}</span>
        </li>
    );
    return (
        <div className="Category-NavBar">
            <ol>
                {items}
            </ol>
        </div>
    )
}

export default Navbar;