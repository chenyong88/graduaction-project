package co.cy.action.base;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;

public class BaseAction {

	protected String viewTemplate;
	

	protected ServletContext application;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	protected Model model;

	@ModelAttribute
	public void setReqAndRes(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
//		this.viewTemplate = this.session.getAttribute("viewTemplate")
//				.toString();
		this.application = request.getServletContext();
		this.model = model;
	}

	public ServletContext getApplication() {
		return application;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpSession getSession() {
		return session;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getViewTemplate() {
		return viewTemplate;
	}

	protected String parBeanToJsonStr(Object object) {
		checkNullError(object);
		return JSONObject.toJSONString(object);
	}

	protected void checkNullError(Object object) {
		if (null == object) {
			throw new NullPointerException("空指针异常了");
		}
	}






	protected void outAjaxResult(boolean b, long code, String description) {
		outAjaxResult(b, Integer.valueOf(String.valueOf(code)), description);
	}


	protected ParMapToJsonStr getParMapToJsonStr() {
		return new ParMapToJsonStr();
	}

	public class ParMapToJsonStr {
		Map<String, Object> map = null;

		@SuppressWarnings("unchecked")
		public ParMapToJsonStr() {
			map = new HashedMap();
		}

		public ParMapToJsonStr put(String key, Object value) {
			if (null == map) {
				throw new NullPointerException();
			}
			map.put(key, value);
			return this;
		}

		public String toString() {
			if (null == map) {
				throw new NullPointerException();
			}
			String jsonStr = JSONObject.toJSONString(map);
			System.out.println("----par:" + jsonStr);
			return jsonStr;
		}
	}
}
