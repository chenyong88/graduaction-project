package co.cy.dao.base;

import java.beans.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import co.cy.util.TableEntityMapperUtil;


public class BaseDAO implements IBaseDAO {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;





/*	@Override
	public Serializable save(Object entity) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				entity);
		Class<?> entityClass = Class.forName(entity.getClass().getName());
		String insertSql = getInsertSql(entityClass);
		getP().update(insertSql, paramSource,
				keyHolder);
		if (keyHolder.getKey() != null) {
			setId(entity, keyHolder.getKey().longValue());
			return keyHolder.getKey().longValue();
		} else {
			return 0;
		}
	}*/

	protected String getInsertSql(Class<?> entity) {
		Field[] classFields = getEntityFields(entity);
		List<String> fields = new ArrayList<String>();
		List<String> properties = new ArrayList<String>();
		for (int i = 0; i < classFields.length; i++) {
			String column = TableEntityMapperUtil.mapperToDB(classFields[i]
					.getName());
			if (column.equals(getIdColumn())) {
				continue;
			}
			fields.add(column);
			properties.add(":" + classFields[i].getName());
		}

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append(getTabeName(entity));
		sql.append("(");
		sql.append(StringUtils.join(fields, ","));
		sql.append(")");
		sql.append(" VALUES(");
		sql.append(StringUtils.join(properties, ","));
		sql.append(")");

		return sql.toString();
	}

	private Field[] getEntityFields(Class<?> clz) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : clz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (field.getAnnotation(Transient.class) != null) {
				continue;
			}
			fields.add(field);
		}

		Field[] fieldArray = new Field[fields.size()];
		return fields.toArray(fieldArray);
	}

	protected Object getId(Object entity) throws Exception {
		String propertyName = StringUtils.capitalize(TableEntityMapperUtil
				.mapperToProperty(getIdColumn()));
		return BeanUtils.getPropertyDescriptor(entity.getClass(), propertyName)
				.getReadMethod().invoke(entity);
	}

	protected void setId(Object entity, Object id) throws Exception {
		String propertyName = StringUtils.capitalize(TableEntityMapperUtil
				.mapperToProperty(getIdColumn()));
		BeanUtils.getPropertyDescriptor(entity.getClass(), propertyName)
				.getWriteMethod().invoke(entity, id);
	}

	protected String getIdColumn() {
		return "id";
	}


	protected Object getTabeName(Class<?> entity) {
		return TableEntityMapperUtil.mapperToDB(entity.getSimpleName());
	}

	/* (non-Javadoc)
	 * @see co.jufeng.base.IBaseDAO#saveJdbc(java.lang.Object)
	 */
	@Override
	public Serializable saveJdbc(Object entity) throws Exception {
		return save(entity);
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public Serializable save(Object entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
