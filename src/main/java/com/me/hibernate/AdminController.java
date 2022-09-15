package com.me.hibernate;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
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
public class AdminController {

	@RequestMapping(value = "/adminHome.htm", method = RequestMethod.POST)
	public ModelAndView adminAction(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		if(action.equals("View Jobseekers")) {
			List<Users> users = userDao.getUsers("Jobseeker");
			return new ModelAndView("allJobseekersView","users",users);
		}else {
			List<Users> users = userDao.getUsers("Recruiter");
			return new ModelAndView("allRecruitersView","users",users);
		}
	}
	
	@RequestMapping(value = "/viewJobseeker.htm", method = RequestMethod.POST)
	public ModelAndView viewJobseeker(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		if(action.equals("Add")){
			return new ModelAndView("addJobseeker");
		}else {
			String[] user = request.getParameterValues("user");
			if(user != null) {
				if(user.length > 1) {
					System.out.println("Select only one");
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Select only one jobseeker to view details.");
					List<Users> users = userDao.getUsers("Jobseeker");
					return new ModelAndView("allJobseekersView","users",users);
				}else if(user.length == 0){
					System.out.println("Select one");
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Select atleast one jobseeker to view details.");
					List<Users> users = userDao.getUsers("Jobseeker");
					return new ModelAndView("allJobseekersView","users",users);
				}else {
					Users user1 = userDao.getUser(user[0]);
					return new ModelAndView("updateJobseekerView","user",user1);
				}
			}else {
				System.out.println("Select one");
				session.setAttribute("flag", "Error");
				session.setAttribute("message", "Select atleast one jobseeker to view details.");
				List<Users> users = userDao.getUsers("Jobseeker");
				return new ModelAndView("allJobseekersView","users",users);
			}
		}
	}
	
	@RequestMapping(value = "/updateJobseeker.htm", method = RequestMethod.POST)
	public ModelAndView updateJobseeker(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		if(action.equals("Update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Users user = userDao.get(id);
			if(user != null) {
				boolean contactFlag = request.getParameter("contactNo").matches("^[0-9]+$");
				if(!contactFlag || request.getParameter("contactNo").length()!=10) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Contact Number must be of 10 digits and should contain digits only.");
					List<Users> users = userDao.getUsers("Jobseeker");
					return new ModelAndView("allJobseekersView","users",users);
				}
				user.setFirstName(request.getParameter("firstName"));
				user.setLastName(request.getParameter("lastName"));
				user.setContactNo(Long.parseLong(request.getParameter("contactNo")));
				userDao.save(user);
				System.out.println("Updated.");
				session.setAttribute("flag", "Success");
				session.setAttribute("message", "User data updated successfully.");
				List<Users> users = userDao.getUsers("Jobseeker");
				return new ModelAndView("allJobseekersView","users",users);
			}else {
				System.out.println("invalid user");
				session.setAttribute("flag", "Error");
				session.setAttribute("message", "User data not updated.");
				List<Users> users = userDao.getUsers("Jobseeker");
				return new ModelAndView("allJobseekersView","users",users);
			}
		}else {
			int id = Integer.parseInt(request.getParameter("id"));
			Users user = userDao.get(id);
			
			if(user != null) {
				userDao.delete(user);
				System.out.println("Deleted.");
				session.setAttribute("flag", "Success");
				session.setAttribute("message", "User deleted successfully.");
				List<Users> users = userDao.getUsers("Jobseeker");
				return new ModelAndView("allJobseekersView","users",users);
			}else {
				System.out.println("invalid user");
				session.setAttribute("flag", "Error");
				session.setAttribute("message", "User does not deleted.");
				List<Users> users = userDao.getUsers("Jobseeker");
				return new ModelAndView("allJobseekersView","users",users);
			}
		}
	}
	
	@RequestMapping(value = "/addJobseeker.htm", method = RequestMethod.POST)
	public ModelAndView addJobseeker(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {

		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		boolean emailFlag = request.getParameter("email").matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-zA-Z]+$");
		boolean contactFlag = request.getParameter("contactNo").matches("^[0-9]+$");
		boolean passwordFlag = request.getParameter("password").matches("^((?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@$!%*#?&]).{1,20})$");
		if(!emailFlag) {
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Email must be in format of example@domain.com.");
			List<Users> users = userDao.getUsers("Jobseeker");
			return new ModelAndView("allJobseekersView","users",users);
		}
		if(!contactFlag || request.getParameter("contactNo").length()!=10) {
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Contact Number must be of 10 digits and should contain digits only.");
			List<Users> users = userDao.getUsers("Jobseeker");
			return new ModelAndView("allJobseekersView","users",users);
		}
		if(!passwordFlag) {
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Password should contain atleast 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character.");
			List<Users> users = userDao.getUsers("Jobseeker");
			return new ModelAndView("allJobseekersView","users",users);
		}
		Users user = new Users();
		user.setType("Jobseeker");
		user.setStatus("Approved");
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setContactNo((Long.parseLong(request.getParameter("contactNo"))));
		user.setPassword(request.getParameter("password"));
		userDao.save(user);
		System.out.println("Added");
		session.setAttribute("flag", "Success");
		session.setAttribute("message", "User added successfully.");
		List<Users> users = userDao.getUsers("Jobseeker");
		return new ModelAndView("allJobseekersView","users",users);
	}
	
	@RequestMapping(value = "/viewRecruiter.htm", method = RequestMethod.POST)
	public ModelAndView viewRecruiter(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		if(action.equals("Add")){
			return new ModelAndView("addRecruiter");
		}else {
			if(action.equals("Approve")){
				String[] user = request.getParameterValues("user");
				if(user != null) {
					for (String val : user) {
						Users user1 = userDao.getUser(val);
						user1.setStatus("Approved");
						userDao.save(user1);
						System.out.println("Approved");
					}
				}else {
					System.out.println("select one.");
				}
				List<Users> users = userDao.getUsers("Recruiter");
				return new ModelAndView("allRecruitersView","users",users);
			}else {
				String[] user = request.getParameterValues("user");
				if(user != null) {
					if(user.length > 1) {
						System.out.println("Select only one");
						session.setAttribute("flag", "Error");
						session.setAttribute("message", "Select only one recruiter to view details.");
						List<Users> users = userDao.getUsers("Recruiter");
						return new ModelAndView("allRecruitersView","users",users);
					}else if(user.length == 0){
						System.out.println("Select one");
						session.setAttribute("flag", "Error");
						session.setAttribute("message", "Select atleast one recruiter to view details.");
						List<Users> users = userDao.getUsers("Recruiter");
						return new ModelAndView("allRecruitersView","users",users);
					}else {
						Users user1 = userDao.getUser(user[0]);
						return new ModelAndView("updateRecruiterView","user",user1);
					}
				}else {
					System.out.println("Select one");
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Select atleast one recruiter to view details.");
					List<Users> users = userDao.getUsers("Recruiter");
					return new ModelAndView("allRecruitersView","users",users);
				}
			}
		}
	}
	
	@RequestMapping(value = "/updateRecruiter.htm", method = RequestMethod.POST)
	public ModelAndView updateRecruiter(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		if(action.equals("Update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Users user = userDao.get(id);
			
			if(user != null) {
				boolean contactFlag = request.getParameter("contactNo").matches("^[0-9]+$");
				if(!contactFlag || request.getParameter("contactNo").length()!=10) {
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "Contact Number must be of 10 digits and should contain digits only.");
					List<Users> users = userDao.getUsers("Recruiter");
					return new ModelAndView("allRecruitersView","users",users);
				}
				user.setCompanyName(request.getParameter("companyName"));
				user.setContactNo(Long.parseLong(request.getParameter("contactNo")));
				userDao.save(user);
				System.out.println("Updated.");
				session.setAttribute("flag", "Success");
				session.setAttribute("message", "User data updated successfully.");
				List<Users> users = userDao.getUsers("Recruiter");
				return new ModelAndView("allRecruitersView","users",users);
			}else {
				System.out.println("invalid user");
				session.setAttribute("flag", "Error");
				session.setAttribute("message", "User data does not updated.");
				List<Users> users = userDao.getUsers("Recruiter");
				return new ModelAndView("allRecruitersView","users",users);
			}
		}else {
			int id = Integer.parseInt(request.getParameter("id"));
			Users user = userDao.get(id);
			
			if(user != null) {
				userDao.delete(user);
				System.out.println("Deleted.");
				session.setAttribute("flag", "Success");
				session.setAttribute("message", "User deleted successfully.");
				List<Users> users = userDao.getUsers("Recruiter");
				return new ModelAndView("allRecruitersView","users",users);
			}else {
				System.out.println("invalid user");
				session.setAttribute("flag", "Error");
				session.setAttribute("message", "User does not deleted.");
				List<Users> users = userDao.getUsers("Recruiter");
				return new ModelAndView("allRecruitersView","users",users);
			}
		}
	}
	
	@RequestMapping(value = "/addRecruiter.htm", method = RequestMethod.POST)
	public ModelAndView addRecruiter(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {

		HttpSession session = request.getSession();
		session.removeAttribute("flag");
		session.removeAttribute("message");
		boolean emailFlag = request.getParameter("email").matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-zA-Z]+$");
		boolean contactFlag = request.getParameter("contactNo").matches("^[0-9]+$");
		boolean passwordFlag = request.getParameter("password").matches("^((?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@$!%*#?&]).{1,20})$");
		if(!emailFlag) {
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Email must be in format of example@domain.com.");
			List<Users> users = userDao.getUsers("Recruiter");
			return new ModelAndView("allRecruitersView","users",users);
		}
		if(!contactFlag || request.getParameter("contactNo").length()!=10) {
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Contact Number must be of 10 digits and should contain digits only.");
			List<Users> users = userDao.getUsers("Recruiter");
			return new ModelAndView("allRecruitersView","users",users);
		}
		if(!passwordFlag) {
			session.setAttribute("flag", "Error");
			session.setAttribute("message", "Password should contain atleast 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character.");
			List<Users> users = userDao.getUsers("Recruiter");
			return new ModelAndView("allRecruitersView","users",users);
		}
		Users user = new Users();
		user.setType("Recruiter");
		user.setStatus("Approved");
		user.setCompanyName(request.getParameter("companyName"));
		user.setEmail(request.getParameter("email"));
		user.setContactNo((Long.parseLong(request.getParameter("contactNo"))));
		user.setPassword(request.getParameter("password"));
		userDao.save(user);
		System.out.println("Registered");
		System.out.println("Added");
		session.setAttribute("flag", "Success");
		session.setAttribute("message", "User added successfully.");
		List<Users> users = userDao.getUsers("Recruiter");
		return new ModelAndView("allRecruitersView","users",users);
	}
	
	@RequestMapping(value = "/adminSignOut.htm", method = RequestMethod.POST)
	public ModelAndView recruiterSignOut(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/allJobseekersBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView allJobseekersBackSignOut(HttpServletRequest request, HttpServletResponse response) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			session.removeAttribute("flag");
			session.removeAttribute("Message");
			return new ModelAndView("adminLandingPage");
		}else {
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/addJobseekerBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView addJobseekerBackSignOut(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			session.removeAttribute("flag");
			session.removeAttribute("Message");
			List<Users> users = userDao.getUsers("Jobseeker");
			return new ModelAndView("allJobseekersView","users",users);
		}else {
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/updateJobseekerBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView updateJobseekerBackSignOut(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			session.removeAttribute("flag");
			session.removeAttribute("Message");
			List<Users> users = userDao.getUsers("Jobseeker");
			return new ModelAndView("allJobseekersView","users",users);
		}else {
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/allRecruitersBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView allRecruitersBackSignOut(HttpServletRequest request, HttpServletResponse response) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			session.removeAttribute("flag");
			session.removeAttribute("Message");
			return new ModelAndView("adminLandingPage");
		}else {
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/addRecruiterBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView addRecruiterBackSignOut(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			session.removeAttribute("flag");
			session.removeAttribute("Message");
			List<Users> users = userDao.getUsers("Recruiter");
			return new ModelAndView("allRecruitersView","users",users);
		}else {
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/updateRecruiterBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView updateRecruiterBackSignOut(HttpServletRequest request, HttpServletResponse response, UserDao userDao) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			session.removeAttribute("flag");
			session.removeAttribute("Message");
			List<Users> users = userDao.getUsers("Recruiter");
			return new ModelAndView("allRecruitersView","users",users);
		}else {
			session.invalidate();
			return new ModelAndView("home");
		}
	}

}
