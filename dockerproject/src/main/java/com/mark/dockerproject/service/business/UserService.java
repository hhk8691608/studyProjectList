package com.mark.dockerproject.service.business;


import com.mark.dockerproject.dao.UserDao;
import com.mark.dockerproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据id查找用户
     */
    public User selectUserById(int id) {
        return userDao.findUserById(id);
    }


    /**
     * 根据名字查找用户
     */
    public User selectUserByName(String name) {
        return userDao.findUserByName(name);
    }

    /**
     * 查找所有用户
     */
    public List<User> selectAllUser() {
        return userDao.findAllUser();
    }


    public void insertService(User user) {
        userDao.insertUser(user.getName(),user.getAge(),user.getMoney());
    }


    /**
     * 根据id 删除用户
     */

    public void deleteService(int id) {
        userDao.deleteUser(id);
    }

    /**
     * 模拟事务。由于加上了 @Transactional注解，如果转账中途出了意外 SnailClimb 和 Daisy 的钱都不会改变。
     * @param sourId
     * @param sourMoney
     * @param targetId
     */
    @Transactional
    public void changemoney(int sourId, double sourMoney, int targetId) {
        User sourUser = userDao.findUserById(sourId);
        User targetUser = userDao.findUserById(targetId);
        userDao.updateUser( (sourUser.getMoney() - sourMoney), sourId);
        userDao.updateUser( (targetUser.getMoney() + sourMoney) , targetId);
    }



}
