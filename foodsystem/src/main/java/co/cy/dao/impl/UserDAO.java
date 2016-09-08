package co.cy.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import co.cy.dao.IUserDAO;
import co.cy.dao.base.BaseDAO;
import co.cy.model.User;

@Repository
public class UserDAO  extends  BaseDAO  implements  IUserDAO{
	@Override
	public User addUser(User user) {
		StringBuilder  sb  =  new  StringBuilder();
		sb.append("select * from  user  where  id = 1 ");
		System.out.println(sb.toString());
		User  u  =  this.getJdbcTemplate().queryForObject(sb.toString(),
				 new  BeanPropertyRowMapper<User>(User.class));
		return u;
	}
	

}
