package com.mark.dockerproject.dao;

import com.mark.dockerproject.model.UserLike;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface UserLikeDao {

    @Insert("INSERT INTO user_like(liked_user_id, liked_post_id,status) VALUES(#{likedUserId}, #{likedPostId}, #{status})")
    void save(UserLike userLike);

    List<UserLike> saveAll(List<UserLike> list);

    Page<UserLike> findByLikedUserIdAndStatus(String likedUserId, Integer code, Pageable pageable);

    Page<UserLike> findByLikedPostIdAndStatus(String likedPostId, Integer code, Pageable pageable);

    @Select("select * from user_like  WHERE  liked_user_id = #{likedUserId} and liked_post_id = #{likedPostId} ")
    UserLike findByLikedUserIdAndLikedPostId(@Param("likedUserId")String likedUserId,@Param("likedPostId") String likedPostId);

    @Update("update user_like set liked_user_id=#{likedUserId},liked_post_id=#{likedPostId},status=#{status} WHERE id = #{id}")
    void update(UserLike userLike);
}
