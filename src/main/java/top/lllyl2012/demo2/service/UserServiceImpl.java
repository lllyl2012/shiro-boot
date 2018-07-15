package top.lllyl2012.demo2.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import top.lllyl2012.demo2.mapper.UserMapper;
import top.lllyl2012.demo2.model.User;

@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

}
