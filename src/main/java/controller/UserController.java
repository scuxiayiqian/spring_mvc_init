package controller;


import entity.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;


    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseInfo login(@RequestParam("userName") String userName,
                      @RequestParam("password") String password) throws Exception{
        ResponseInfo info = new ResponseInfo();

        List<User> userList = userRepository.findByUsername(userName);
        User currentUser = userList.size()>0? userList.get(0) : null;
        if(null == currentUser){
            info.setFailWithInfo("不存在此用户");
            return info;
        }

        if(!currentUser.password.equals(password)){
            info.setFailWithInfo("密码错误");
            return info;
        } else {
            info.setSuccessWithInfo(String.valueOf(currentUser.id));
            return info;
        }
    }

    /**
     * 用户注册
     * @param userName
     * @param password
     * @param name
     * @return
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseInfo register(@RequestParam("userName") String userName,
                         @RequestParam("password") String password,
                         @RequestParam(value="name", required = false) String name) throws Exception{
        ResponseInfo info = new ResponseInfo();

        if(userRepository.findByUsername(userName).size() > 0){
            info.setFailWithInfo("用户名已经被注册, 请换别的用户名");
            return  info;
        }

        User user = new User();
        user.username = userName;
        user.password = password;
        if (name != null){
            user.name = name;
        }

        User savedUser = userRepository.save(user);
        if ( savedUser != null){
            info.setSuccessWithInfo(String.valueOf(savedUser.id));
            return info;
        }else{
            info.setFailWithInfo("注册失败");
            return info;
        }

    }

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/user/password/update", method = RequestMethod.POST)
    public ResponseInfo changePassword(@RequestParam("userId") Long userId,
                                       @RequestParam("oldPassword") String oldPwd,
                                       @RequestParam("newPassword") String newPwd) throws Exception{
        ResponseInfo info = new ResponseInfo();

        User user = userRepository.findOne(userId);
        if (user == null){
            info.setFailWithInfo("不存在此用户");
        }else{
            if (!user.password.equals(oldPwd)){
                info.setFailWithInfo("旧密码不正确");
            }else {
                user.password = newPwd;
                if (userRepository.save(user) != null) {
                    info.setSuccessWithInfo("修改密码成功");
                } else {
                    info.setFailWithInfo("修改密码失败");
                }
            }
        }

        return info;
    }

    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUser() throws Exception{
        List<User> users = userRepository.findAll();
        for (int i=0; i<users.size(); i++){
            users.get(i).password = "";
        }
        return users;
    }

    /**
     * 根据用户id，获取用户名
     * TODO: 这里返回的其实是name这个字段  /user/{uid}/userName这里的userName其实名字取得并不好
     * @param uid
     * @return
     */
    @RequestMapping(value = "/user/{uid}/userName", method = RequestMethod.GET)
    public ResponseInfo fetchUserNickName(@PathVariable Long uid) throws Exception{
        ResponseInfo info = new ResponseInfo();

        User user = userRepository.findOne(uid);
        if (user == null){
            info.setFailWithInfo("不存在该用户");
        }else{
            if (user.name == null || user.name.equals("")){
                info.setSuccessWithInfo("用户" + String.valueOf(user.id));
            }else {
                info.setSuccessWithInfo(user.name);
            }
        }
        return info;
    }


}
