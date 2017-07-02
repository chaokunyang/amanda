import React, { Component } from 'react'
import './Pagination.css'

/**
 * 分页组件
 * 最多同时展示7页，超过则用...代替
 * 页码从0开始，但在屏幕显示时从1开始显示
 * @param props
 * @returns {XML}
 * @constructor
 */
class Pagination extends Component{

    constructor(props) {
        super(props);
        this.onPageChange = this.onPageChange.bind(this);
    }

    onPageChange(event) {
        event.preventDefault(); // 也会阻止事件冒泡，所以应该放在内部，从而不会阻止点击span冒泡到a元素
        const index = event.target.href.indexOf("page=");
        const page = event.target.href.substring(index + "page=".length);
        this.props.onPageChange(page);
    }

    render() {
        const paging = [];
        const isFirst = this.props.pagination.first;
        const isLast = this.props.pagination.last;
        const currentPage = this.props.pagination.number;
        const totalPages = this.props.pagination.totalPages;

        // "<<"
        if(isFirst){
            paging.push(
                <li className="disabled" key={"prev"}>
                    <span>
                        <span aria-hidden="true">&laquo;</span>
                    </span>
                </li>
            );
        }else {
            paging.push(
                <li key={"prev"}>
                    <a onClick={this.onPageChange} href={`admin/articles?page=${currentPage - 1}`} aria-label="Previous">
                        &laquo;
                    </a>
                </li>
            );
        }

        if(totalPages <= 7) {
            for(let i = 0; i < totalPages; i++) {
                if(i === currentPage) {
                    paging.push(
                        <li className="active" key={i}>
                            <span>{i + 1} <span className="sr-only">(current)</span></span>
                        </li>
                    )
                }else {
                    paging.push(
                        <li key={i}><a onClick={this.onPageChange} href={`/admin/articles?page=${i}`}>{i + 1}</a></li>
                    )
                }
            }
        }else {
            if(currentPage < 5) {
                for(let i = 0; i < 7; i++) {
                    if(i === currentPage) {
                        paging.push(
                            <li className="active" key={i}>
                                <span>{i + 1} <span className="sr-only">(current)</span></span>
                            </li>
                        )
                    }else {
                        paging.push(
                            <li key={i}><a onClick={this.onPageChange} href={`/admin/articles?page=${i}`}>{i + 1}</a></li>
                        )
                    }
                }
                paging.push(
                    <li className="disabled" key={"next_ellipsis"}>
                        <span>
                            <span aria-hidden="true">...</span>
                        </span>
                    </li>
                );
                paging.push(
                    <li key={totalPages - 1}><a onClick={this.onPageChange} href={`/admin/articles?page=${totalPages - 1}`}>{totalPages}</a></li>
                );
            }else if(currentPage > (totalPages - 1) - 5) {
                paging.push(
                    <li key={0}><a onClick={this.onPageChange} href={`/admin/articles?page=0`}>1</a></li>
                );
                paging.push(
                    <li className="disabled" key="prev_ellipsis">
                    <span>
                        <span aria-hidden="true">...</span>
                    </span>
                    </li>
                );

                for(let i = totalPages - 7; i < totalPages; i++) {
                    if(i === currentPage) {
                        paging.push(
                            <li className="active" key={i}>
                                <span>{i + 1} <span className="sr-only">(current)</span></span>
                            </li>
                        )
                    }else {
                        paging.push(
                            <li key={i}><a onClick={this.onPageChange} href={`/admin/articles?page=${i}`}>{i + 1}</a></li>
                        )
                    }
                }
            }else {
                paging.push(
                    <li key={0}><a onClick={this.onPageChange} href={`/admin/articles?page=0`}>1</a></li>
                );
                paging.push(
                    <li className="disabled" key="prev_ellipsis">
                    <span>
                        <span aria-hidden="true">...</span>
                    </span>
                    </li>
                );

                for(let i = currentPage - 2; i < currentPage + 3; i++) {
                    if(i === currentPage) {
                        paging.push(
                            <li className="active" key={i}>
                                <span>{i + 1} <span className="sr-only">(current)</span></span>
                            </li>
                        )
                    }else {
                        paging.push(
                            <li key={i}><a onClick={this.onPageChange} href={`/admin/articles?page=${i}`}>{i + 1}</a></li>
                        )
                    }
                }

                paging.push(
                    <li className="disabled" key="next_ellipsis">
                    <span>
                        <span aria-hidden="true">...</span>
                    </span>
                    </li>
                );
                paging.push(
                    <li key={totalPages - 1}><a onClick={this.onPageChange} href={`/admin/articles?page=${totalPages - 1}`}>{totalPages}</a></li>
                );
            }
        }

        // ">>"
        if(isLast){
            paging.push(
                <li className="disabled" key={"next"}>
                    <span>
                        <span aria-hidden="true">&raquo;</span>
                    </span>
                </li>
            );
        }else {
            paging.push(
                <li key={"next"}>
                    <a onClickCapture={this.onPageChange} href={`/admin/articles?page=${currentPage + 1}`} aria-label="Next">
                        &raquo;
                    </a>
                </li>
            );
        }

        return (
            <div className="PaginationComponent">
                <nav aria-label="Page navigation">
                    <ul className="pagination">
                        {paging}
                    </ul>
                </nav>
            </div>
        );
    }

}

export default Pagination;