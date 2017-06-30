import React, {Component} from 'react';
import Axios from 'axios'
import ProfileView from './ProfileView'
import ProfileEdit from './ProfileEdit'
import './Profile.css'

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: {},
            view: true
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.setView = this.setView.bind(this);
    }

    componentDidMount() {
        Axios.get('/api/profile/me')
            .then(response => {
                this.setState({
                    profile: response.data
                })
            })
            .catch(error => {
                console.log(error);
            });
    }

    setView() {
        this.setState(prevState => ({
            view: !prevState.view
        }))
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState(prevState => {
            prevState.profile[name] = value;
            return {
                profile: prevState.profile
            };
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        Axios.put('/api/profile', this.state.profile)
            .then(response => {
                this.setState({
                    profile: response.data,
                    view: true
                })
            })
            .catch(error => console.log(error));
    }

    render() {
        return (
            <div className="Profile">
                <h2>简历</h2>
                {
                    this.state.view ?
                        <button className="btn btn-info" onClick={this.setView}>编辑</button> :
                        <button className="btn btn-info" onClick={this.setView}>查看</button>
                }
                {
                    this.state.view ?
                        <ProfileView profile={this.state.profile} setView={this.setView}/>
                        : <ProfileEdit profile={this.state.profile} setView={this.setView} handleSubmit={this.handleSubmit} handleInputChange={this.handleInputChange}/>
                }
            </div>
        );
    }
}

export default Profile
