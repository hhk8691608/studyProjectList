package com.mark.dockerproject.model;

import com.mark.dockerproject.Const.LikedStatusEnum;
import lombok.Data;

@Data
public class UserLike {

    private Integer id;

    //被点赞的用户的id
    private String likedUserId;

    //点赞的用户的id
    private String likedPostId;

    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public UserLike() {
    }

    public UserLike(String likedUserId, String likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("className="+getClass().getName()+",");
        stringBuffer.append("likedUserId="+ getLikedUserId()+",");
        stringBuffer.append("likedPostId="+ getLikedPostId()+",");
        stringBuffer.append("status="+ getStatus()+",");
        return stringBuffer.toString();
    }

    public String getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(String likedUserId) {
        this.likedUserId = likedUserId;
    }

    public String getLikedPostId() {
        return likedPostId;
    }

    public void setLikedPostId(String likedPostId) {
        this.likedPostId = likedPostId;
    }
}
