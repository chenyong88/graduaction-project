package co.cy.dao.base;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public interface IBaseDAO {
	
	JdbcTemplate getJdbcTemplate();

	Serializable save(Object entity) throws Exception;
	
	Serializable saveJdbc(Object entity) throws Exception;
	
	
}
