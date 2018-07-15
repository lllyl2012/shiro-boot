package top.lllyl2012.demo2.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.lllyl2012.demo2.model.User;
import top.lllyl2012.demo2.service.UserService;

@Controller
public class TestController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@RequestMapping("/unauthorized")
	@ResponseBody
	public String unauthorized() {
		return "unauthorized";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public String edit() {
		return "edit";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			subject.logout();
		}
		return "login";
	}
	
	@RequestMapping("/loginUser")
	public String loginUser(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session) {
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			User user = (User) subject.getPrincipal();
			session.setAttribute("user",user);
			return "index";
		}catch(Exception e) {
			return "login";
		}
	}
}
