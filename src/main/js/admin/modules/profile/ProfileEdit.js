import React, {Component} from 'react';

function ProfileEdit(props) {

    return (
        <div className="ProfileEdit">
            <form onSubmit={props.handleSubmit} className="form-horizontal">
                <div className="form-group">
                    <label className="col-sm-2 control-label">头像地址</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.avatar} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入头像地址" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">姓名</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.name} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入姓名" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">邮箱</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.email} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入邮箱" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">个人简介</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.biography} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入个人简介" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">个人网站</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.url} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入个人网站" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">github账号</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.githubUrl} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入github账号" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">twitter账号</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.twitterUrl} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入twitter账号" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">所在公司</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.company} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入所在公司" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">所在位置</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.location} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入所在位置" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">详细简介markdown</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.mdBody} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入详细简介markdown" className="form-control"/>
                    </div>
                </div>
                <div className="form-group">
                    <label className="col-sm-2 control-label">详细简介预览</label>
                    <div className="col-sm-6">
                        <input name="currentPassword" type="text" value={props.profile.htmlBody} onChange={props.handleInputChange} autoComplete="off" placeholder="请输入详细简介预览" className="form-control"/>
                    </div>
                </div>

                <div className="form-group">
                    <div className="col-sm-offset-2 col-sm-6">
                        <button type="submit" className="btn btn-default">更新</button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default ProfileEdit;