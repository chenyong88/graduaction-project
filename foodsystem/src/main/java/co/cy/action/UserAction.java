package co.cy.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts.util.RequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import co.cy.action.base.BaseAction;
import co.cy.model.User;
import co.cy.service.IUserService;
import co.jufeng.core.util.RequestUtil;
@Controller
@RequestMapping("/user")
public class UserAction extends  BaseAction {
	@Resource
	IUserService  userService;
	@RequestMapping(value="/index")
	public ModelAndView   index(ModelAndView  modelAndView){
		Map<String,String>  map  = RequestUtil.toObject(request);
		modelAndView.setViewName("default/index.html");
		modelAndView.addObject("s", 1);
		return modelAndView;
	}
	
	@RequestMapping(value="/submit")
	public ModelAndView   submit(ModelAndView  modelAndView){
		Map<String,String>  map  = RequestUtil.toObject(request);
		String  jsonString  =  JSONObject.toJSONString(map);
		
		User user  =  userService.find(jsonString);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("default/user.html");
		return modelAndView;
	}

}
