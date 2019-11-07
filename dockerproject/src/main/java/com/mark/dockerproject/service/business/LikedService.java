package com.mark.dockerproject.service.business;

import com.mark.dockerproject.Const.LikedStatusEnum;
import com.mark.dockerproject.DTO.LikedCountDTO;
import com.mark.dockerproject.dao.UserDao;
import com.mark.dockerproject.dao.UserLikeDao;
import com.mark.dockerproject.model.User;
import com.mark.dockerproject.model.UserLike;
import com.mark.dockerproject.service.technology.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikedService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserLikeDao userLikeDao;

    @Autowired
    RedisService redisService;

    public void save(UserLike userLike) {
         userLikeDao.save(userLike);
    }

    public List<UserLike> saveAll(List<UserLike> list) {
        return userLikeDao.saveAll(list);
    }

    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return userLikeDao.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return userLikeDao.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode(), pageable);
    }


    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return userLikeDao.findByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
    }

    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis(1);
        for (UserLike like : list) {
            UserLike ul = getByLikedUserIdAndLikedPostId(like.getLikedUserId(), like.getLikedPostId());
            if (ul == null){
                //没有记录，直接存入
                save(like);
            }else{
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                update(ul);
            }
        }
    }

    private void update(UserLike ul) {
        userLikeDao.update(ul);

//        UserLike userLike = new UserLike();
//        userLike.setId(2);
//        userLike.setLikedUserId("100");
//        userLike.setLikedPostId("500");
//        userLike.setStatus(0);
//        userLikeDao.update(userLike);
    }


    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis(1);
        for (LikedCountDTO dto : list) {
            User user = userDao.findUserById(dto.getId());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (user != null){
                Integer likeNum = user.getLikeNum() + dto.getCount();
                user.setLikeNum(likeNum);
                //更新点赞数量
                userDao.updateInfo(user);
            }
        }
    }


    public void syncDB() {
        transLikedFromRedis2DB();
        transLikedCountFromRedis2DB();
    }



}
