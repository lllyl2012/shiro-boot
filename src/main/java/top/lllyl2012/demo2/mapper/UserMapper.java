package top.lllyl2012.demo2.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.lllyl2012.demo2.model.User;
public interface UserMapper {
	User findByUsername(@Param("username") String username);
}
