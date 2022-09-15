package com.me.hibernate;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.UserDao;
import com.me.pojo.Users;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		String page = request.getParameter("page");
		if(page.equals("Login")) {
			return new ModelAndView("login-view");
		}
		else if(page.equals("Register")) {
			return new ModelAndView("register-view");
		}
		else if(page.equals("type")){
			String type = request.getParameter("radio1");
			if(type.equals("Jobseeker")) {
				return new ModelAndView("registerAsJobseeker");
			}else {
				return new ModelAndView("registerAsRecuiter");
			}
		}else if(page.equals("jobseeker")){
			Users user = userDao.getUser(request.getParameter("email"));
			if(user != null) {
				System.out.println("User is already registered.");
				session.setAttribute("flag", "Error");
				session.setAttribute("message", "You are already registered.");
				return new ModelAndView("registerAsJobseeker");
			}else {
				boolean emailFlag = request.getParameter("email").matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-zA-Z]+$");
				boolean contactFlag = request.getParameter("contactNo").matches("^[0-9]+$");
				boolean passwordFlag = request.getParameter("password").matches("^((?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@$!%*#?&]).{1,20})$");
				if(!emailFlag) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Email must be in format of example@domain.com.");
					return new ModelAndView("registerAsJobseeker");
				}
				if(!contactFlag || request.getParameter("contactNo").length()!=10) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Contact Number must be of 10 digits and should contain digits only.");
					return new ModelAndView("registerAsJobseeker");
				}
				if(!passwordFlag) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Password should contain atleast 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character.");
					return new ModelAndView("registerAsJobseeker");
				}
				Users user1 = new Users();
				user1.setType("Jobseeker");
				user1.setStatus("Approved");
				user1.setFirstName(request.getParameter("firstName"));
				user1.setLastName(request.getParameter("lastName"));
				user1.setEmail(request.getParameter("email"));
				user1.setContactNo((Long.parseLong(request.getParameter("contactNo"))));
				user1.setPassword(request.getParameter("password"));
				userDao.save(user1);
				System.out.println("Registered");
				session.setAttribute("flag", "Success");
				session.setAttribute("message", "Registered successfully.");
				return new ModelAndView("login-view");
			}
		}else {
			Users user = userDao.getUser(request.getParameter("email"));
			if(user != null) {
				System.out.println("User is already registered.");
				session.setAttribute("flag", "Error");
				session.setAttribute("message", "You are already registered.");
				return new ModelAndView("registerAsRecuiter");
			}else {
				boolean emailFlag = request.getParameter("email").matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-zA-Z]+$");
				boolean contactFlag = request.getParameter("contactNo").matches("^[0-9]+$");
				boolean passwordFlag = request.getParameter("password").matches("^((?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@$!%*#?&]).{1,20})$");
				if(!emailFlag) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Email must be in format of example@domain.com.");
					return new ModelAndView("registerAsRecuiter");
				}
				if(!contactFlag || request.getParameter("contactNo").length()!=10) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Contact Number must be of 10 digits and should contain digits only.");
					return new ModelAndView("registerAsRecuiter");
				}
				if(!passwordFlag) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Password should contain atleast 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character.");
					return new ModelAndView("registerAsRecuiter");
				}
				Users user1 = new Users();
				user1.setType("Recruiter");
				user1.setStatus("Pending");
				user1.setCompanyName(request.getParameter("companyName"));
				user1.setEmail(request.getParameter("email"));
				user1.setContactNo((Long.parseLong(request.getParameter("contactNo"))));
				user1.setPassword(request.getParameter("password"));
				userDao.save(user1);
				System.out.println("Registered");
				session.setAttribute("flag", "Success");
				session.setAttribute("message", "Registered successfully.");
				return new ModelAndView("login-view");
			}
		}
	}
	
	@RequestMapping(value = "/registerBack.htm", method = RequestMethod.POST)
	public ModelAndView registerBack(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/registerJobseekerBack.htm", method = RequestMethod.POST)
	public ModelAndView registerJobseekerBack(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("register-view");
	}
	
	@RequestMapping(value = "/registerRecruiterBack.htm", method = RequestMethod.POST)
	public ModelAndView registerRecruiterBack(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("register-view");
	}
	
}
