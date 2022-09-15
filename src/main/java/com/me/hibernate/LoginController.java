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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.JobseekerDao;
import com.me.dao.UserDao;
import com.me.pojo.JobseekerProfile;
import com.me.pojo.Users;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, UserDao userDao, ModelMap model, JobseekerProfile jobseekerProfile, JobseekerDao jobseekerDao) {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		Users user = userDao.getUser(email);
		if(user == null) {
			System.out.println("Email id is not registered");
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Email id is not registered.");
			return new ModelAndView("login-view");
		}
		if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
			session.setAttribute("user", user);
			if(user.getType().equals("Jobseeker")) {
				JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
				if(jobseeker == null) {
					JobseekerProfile newJobseeker = new JobseekerProfile();
					return new ModelAndView("jobseekerLandingPage","jobseeker",newJobseeker);
				}else {
					System.out.println("userrrrr in jobseeker landing="+ jobseeker.getUser().getFirstName());
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
//				model.addAttribute("jobseekerProfile", jobseekerProfile);
//				return new ModelAndView("jobseekerLandingPage");
			}else if(user.getType().equals("Recruiter")){
				if(user.getStatus().equals("Approved")) {
					return new ModelAndView("recruiterLandingPage");
				}else {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "You are not approved as a recruiter yet.");
					System.out.println("Email is not approved.");
					return new ModelAndView("login-view");
				}
			}else {
				return new ModelAndView("adminLandingPage");
			}
		}else {
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Incorrect credentials.");
			System.out.println("Incorrect credentials.");
			return new ModelAndView("login-view");
		}
	}
	
	@RequestMapping(value = "/loginBack.htm", method = RequestMethod.POST)
	public ModelAndView loginBack(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("home");
	}

}
