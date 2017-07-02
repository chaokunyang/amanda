import React, {Component} from 'react';

function ProfileView(props) {
    return (
        <div className="ProfileView">
            <div>
                <div className="row-title">头像地址</div>
                <div className="row-content">{props.profile.avatar}</div>
            </div>
            <div>
                <div className="row-title">姓名</div><div className="row-content">{props.profile.name}</div>
            </div>
            <div>
                <div className="row-title">邮箱</div><div className="row-content">{props.profile.email}</div>
            </div>
            <div>
                <div className="row-title">个人简介</div>
                <div className="row-content">{props.profile.biography}</div>
            </div>
            <div>
                <div className="row-title">个人网站</div>
                <div className="row-content">{props.profile.url}</div>
            </div>
            <div>
                <div className="row-title">github账号</div><div className="row-content">{props.profile.githubUrl}</div>
            </div>
            <div>
                <div className="row-title">twitter账号</div>
                <div className="row-content">{props.profile.twitterUrl}</div>
            </div>
            <div>
                <div className="row-title">所在公司</div>
                <div className="row-content">{props.profile.company}</div>
            </div>
            <div>
                <div className="row-title">所在位置</div>
                <div className="row-content">{props.profile.location}</div>
            </div>
            <div>
                <div className="row-title">详细简介</div>
                <div className="row-content" dangerouslySetInnerHTML={{__html: props.profile.htmlBody}}/>
            </div>

            <div className="buttons">
                <button className="btn btn-info" onClick={props.setView}>编辑</button>
            </div>
        </div>
    )
}

export default ProfileView;