import React, {Component} from 'react';

function ProfileView(props) {
    return (
        <div className="ProfileView">
            <div>
                <span>头像地址：</span><span>{props.profile.avatar}</span>
            </div>
            <div>
                <span>姓名：</span><span>{props.profile.name}</span>
            </div>
            <div>
                <span>邮箱：</span><span>{props.profile.email}</span>
            </div>
            <div>
                <span>个人简介：</span><span>{props.profile.biography}</span>
            </div>
            <div>
                <span>个人网站：</span><span>{props.profile.url}</span>
            </div>
            <div>
                <span>github账号：</span><span>{props.profile.githubUrl}</span>
            </div>
            <div>
                <span>twitter账号：</span><span>{props.profile.twitterUrl}</span>
            </div>
            <div>
                <span>所在公司：</span><span>{props.profile.company}</span>
            </div>
            <div>
                <span>所在位置：</span><span>{props.profile.location}</span>
            </div>
            <div>
                <span>详细简介预览：</span><span dangerouslySetInnerHTML={{__html: props.htmlBody}}/>
            </div>
        </div>
    )
}

export default ProfileView;