import React, {Component} from 'react';
import Axios from 'axios'
import history from '../History'
import './Settings.css'

class Settings extends Component {
    constructor(props) {
        super(props);
        this.state = {
            passwordInconsistent: false,
            user: {},
            currentPassword: "",
            newPassword: "",
            newPassword2: ""
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        Axios.get('/api/user/me')
            .then(response => {
                this.setState({
                    user: response.data
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });

        if(name === 'newPassword' && this.state.newPassword2) {
            if(value !== this.state.newPassword2) {
                this.setState({
                    passwordInconsistent: true
                })
            }else {
                this.setState({
                    passwordInconsistent: false
                })
            }

        }else if(name === 'newPassword2' && this.state.newPassword) {
            if(value !== this.state.newPassword) {
                this.setState({
                    passwordInconsistent: true
                })
            }else {
                this.setState({
                    passwordInconsistent: false
                })
            }
        }
    }

    handleSubmit(event) {
        event.preventDefault();
        const userForm = {};
        userForm.currentPassword = this.state.currentPassword;
        userForm.newPassword = this.state.newPassword;

        Axios.put('/api/user/change/password', userForm)
            .then(response => {
                history.push("/");
            })
            .catch(error => console.log(error));
    }

    render() {
        const passwordInconsistentWarning = this.state.passwordInconsistent ? <span className="warning"> * 两次输入的密码不一致</span> : "";

        return (

            <div className="Settings">
                <h2>设置</h2>
                <form onSubmit={this.handleSubmit} className="form-horizontal">
                    <div className="form-group">
                        <label className="col-sm-2 control-label">用户名</label>
                        <div className="col-sm-10">
                            <div className="username">{this.state.user.username}</div>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-2 control-label">当前密码</label>
                        <div className="col-sm-10">
                            <input name="currentPassword" type="text" value={this.state.currentPassword} onChange={this.handleInputChange} autoComplete="off" placeholder="请输入当前密码" className="form-control"/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-2 control-label">新密码</label>
                        <div className="col-sm-10">
                            <input name="newPassword" type="text" value={this.state.newPassword} onChange={this.handleInputChange} autoComplete="off" placeholder="请输入新密码" className="form-control"/>
                            {passwordInconsistentWarning}
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-2 control-label">确认新密码</label>
                        <div className="col-sm-10">
                            <input name="newPassword2" type="text" value={this.state.newPassword2} onChange={this.handleInputChange} autoComplete="off" placeholder="请确认新密码" className="form-control"/>
                            {passwordInconsistentWarning}
                        </div>
                    </div>

                    <div className="form-group">
                        <div className="col-sm-offset-2 col-sm-10">
                            <button type="submit" className="btn btn-default">确认</button>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}
export default Settings