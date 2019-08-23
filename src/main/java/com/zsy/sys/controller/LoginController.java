package com.zsy.sys.controller;


import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.LogLogin;
import com.zsy.sys.service.ILogLoginService;
import com.zsy.sys.utils.ActiverUser;
import com.zsy.sys.utils.WebUtils;
import com.zsy.sys.vo.UserVo;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private ILogLoginService loginService;
	
	@RequestMapping("toLogin")
	public String toLogin() {
		return "index";
	}
	
	/**
	 * 登陆
	 */
	@RequestMapping("login")
	public String login(UserVo userVo,Model model) {
		// 封装token
		UsernamePasswordToken token = new UsernamePasswordToken(userVo.getLoginname(), userVo.getPwd());
		// 得到主体
		Subject subject = SecurityUtils.getSubject();
		// 调用主体的登陆方法
		try {
			subject.login(token);
			System.out.println("登陆成功");
			ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
			WebUtils.getHttpSession().setAttribute("user", activerUser.getUser());
			LogLogin logLogin = new LogLogin();
			logLogin.setLoginname(activerUser.getUser().getName()+"-"+activerUser.getUser().getLoginname());
			logLogin.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
			logLogin.setLogintime(new Date());
			loginService.save(logLogin);
			return "system/main/index";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			model.addAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
		} 
		return "system/main/login";
	}
	
	/**
	 * 得到登陆验证码
	 * @throws IOException 
	 */
	@RequestMapping("getCode")
	public void getCode(HttpServletResponse response,HttpSession session) throws IOException {
		// 定义图形验证码的长和宽
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,5);
		session.setAttribute("code", lineCaptcha.getCode());
		ServletOutputStream outputStream = response.getOutputStream();
		ImageIO.write(lineCaptcha.getImage(), "JPEG", outputStream);
	}
	
}
