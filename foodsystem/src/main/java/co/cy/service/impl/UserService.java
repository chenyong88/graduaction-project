package co.cy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import co.cy.dao.IUserDAO;
import co.cy.model.User;
import co.cy.service.IUserService;

@Service
public class UserService implements  IUserService{
	@Resource
	IUserDAO  userDAO;
	@Override
	public User find(String jsonString) {
		User  user  =  JSONObject.parseObject(jsonString, User.class);
		return userDAO.addUser(user);
	}

}
