import React, { Component } from 'react'
import Remarkable from 'remarkable'
import './md.css'

class MarkdownEditor extends Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e) {
        const md = new Remarkable();
        this.props.onMarkdownChange(e.target.value,  md.render(e.target.value))
    }

    render() {
        return (
            <div className="MarkdownEditor clearfix">
                <div className="input">
                    <h3 className="input-title">{ this.props.inputTitle }</h3>
                    <textarea
                        onChange={this.handleChange} value={this.props.mdBody} />
                </div>

                <div className="preview">
                    <h3 className="preview-title">{ this.props.previewTitle }</h3>

                    <div className="content" dangerouslySetInnerHTML={{__html: this.props.htmlBody}}/>
                </div>

            </div>
        )
    }
}

export default MarkdownEditor;