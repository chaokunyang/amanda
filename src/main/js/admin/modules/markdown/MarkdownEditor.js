import React, { Component } from 'react'
import Remarkable from 'remarkable'
import './md.css'

class MarkdownEditor extends Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleTextAreaKeyDown = this.handleTextAreaKeyDown.bind(this);
    }

    handleChange(e) {
        const md = new Remarkable();
        this.props.onMarkdownChange(e.target.value,  md.render(e.target.value))
    }

    handleTextAreaKeyDown(e) {
        if(e.keyCode === 9) { // tab was pressed
            e.preventDefault();

            let value = e.target.value;

            // get caret position/selection
            const start = e.target.selectionStart;
            const end = e.target.selectionEnd;

            value = value.substring(0, start)
                + "\t"
                + value.substring(end);

            // put caret at right position again (add one for the tab)
            e.target.selectionStart = e.target.selectionEnd = start + 1;

            const md = new Remarkable();
            this.props.onMarkdownChange(value,  md.render(value))
        }
    }

    render() {
        return (
            <div className="MarkdownEditor clearfix">
                <div className="input">
                    <h3 className="input-title">{ this.props.inputTitle }</h3>
                    <textarea
                        onChange={this.handleChange} value={this.props.mdBody} onKeyDown={this.handleTextAreaKeyDown}  />
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