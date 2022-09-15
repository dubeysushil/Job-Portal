package com.me.hibernate;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
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

import com.me.dao.JobApplicationDao;
import com.me.dao.RecruiterDao;
import com.me.dao.UserDao;
import com.me.pojo.JobApplication;
import com.me.pojo.JobPosting;
import com.me.pojo.Users;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RecruiterController {

	@RequestMapping(value = "/recruiterHome.htm", method = RequestMethod.POST)
	public ModelAndView recruiterAction(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao, JobApplicationDao jobApplicationDao) {

		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			if(action.equals("Manage Job Postings")) {
				List<JobPosting> jobs = recruiterDao.getJobs(user.getId());
				for (JobPosting job : jobs) {
					System.out.println(job.getJobTitle());
				}
				return new ModelAndView("allJobPostingsView","jobs",jobs);
			}else {
				List<JobApplication> applications = jobApplicationDao.getApplicationsForRecruiter(user.getId());
				return new ModelAndView("allJobApplicationsView","applications",applications);
			}
		}else {
			session.invalidate();
			System.out.println("null user");
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/manageJobPosting.htm", method = RequestMethod.POST)
	public ModelAndView manageJobPosting(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		String action = request.getParameter("action");
		if(user != null) {
			if(action.equals("Add")) {
				return new ModelAndView("addJobPosting");
			}else {
				String[] job = request.getParameterValues("job");
				if(job != null) {
					if(job.length > 1) {
						System.out.println("Select only one");
						List<JobPosting> jobs = recruiterDao.getJobs(user.getId());
						return new ModelAndView("allJobPostingsView","jobs",jobs);
					}else if(job.length == 0){
						System.out.println("Select one");
						List<JobPosting> jobs = recruiterDao.getJobs(user.getId());
						return new ModelAndView("allJobPostingsView","jobs",jobs);
					}else {
						JobPosting job1 = recruiterDao.get(Integer.parseInt(job[0]));
						return new ModelAndView("updateJobPosting","job",job1);
					}
				}else {
					List<JobPosting> jobs = recruiterDao.getJobs(user.getId());
					return new ModelAndView("allJobPostingsView","jobs",jobs);
				}
			}
		}else {
			System.out.println("null user");
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/addJobPosting.htm", method = RequestMethod.POST)
	public ModelAndView addJobPosting(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			JobPosting job = new JobPosting();
			job.setJobTitle(request.getParameter("jobTitle"));
			job.setCompanyName(user.getCompanyName());
			job.setJobDescription(request.getParameter("jobDescription"));
			job.setNumberOfOpenings(Integer.parseInt(request.getParameter("numberOfOpenings")));
			job.setLocation(request.getParameter("location"));
			job.setSalary(Integer.parseInt(request.getParameter("salary")));
			job.setJobType(request.getParameter("jobType"));
			job.setDate(new Date());
			job.setCompanyId(user.getId());
			job.setStatus(request.getParameter("status"));
			job.setUser(user);
			recruiterDao.save(job);
			System.out.println("added");
			List<JobPosting> jobs = recruiterDao.getJobs(user.getId());
			return new ModelAndView("allJobPostingsView","jobs",jobs);
		}else {
			System.out.println("null user");
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/updateJobPosting.htm", method = RequestMethod.POST)
	public ModelAndView updateJobPosting(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {

		String action = request.getParameter("action");
		int id = Integer.parseInt(request.getParameter("id"));
		JobPosting job = recruiterDao.get(id);
		if(job != null) {
			if(action.equals("Update")) {
				job.setJobTitle(request.getParameter("jobTitle"));
				job.setJobDescription(request.getParameter("jobDescription"));
				job.setNumberOfOpenings(Integer.parseInt(request.getParameter("numberOfOpenings")));
				job.setLocation(request.getParameter("location"));
				job.setSalary(Integer.parseInt(request.getParameter("salary")));
				job.setJobType(request.getParameter("jobType"));
				job.setStatus(request.getParameter("status"));
				recruiterDao.save(job);
				System.out.println("updated");
				List<JobPosting> jobs = recruiterDao.getJobs(job.getCompanyId());
				return new ModelAndView("allJobPostingsView","jobs",jobs);
			}else {
				int companyId = job.getCompanyId();
				recruiterDao.delete(job);
				System.out.println("Deleted.");
				List<JobPosting> jobs = recruiterDao.getJobs(companyId);
				return new ModelAndView("allJobPostingsView","jobs",jobs);
			}
		}else {
			System.out.println("null job");
			return new ModelAndView("recruiterLandingPage");
		}
	}
	
	@RequestMapping(value = "/viewApplicationDetails.htm", method = RequestMethod.POST)
	public ModelAndView viewApplicationDetails(HttpServletRequest request, HttpServletResponse response, JobApplicationDao jobApplicationDao) {

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			String applicationId = request.getParameter("applicationId");
			if(applicationId != null) {
				JobApplication application = jobApplicationDao.get(Integer.parseInt(applicationId));
				if(application != null) {
					return new ModelAndView("applicationDetailsView","application",application);
				}else {
					System.out.println("null application");
					List<JobApplication> applications = jobApplicationDao.getApplicationsForRecruiter(user.getId());
					return new ModelAndView("allJobApplicationsView","applications",applications);
				}
//				JobPosting job = recruiterDao.get(Integer.parseInt(jobId));
//				return new ModelAndView("jobDetailsView","job",job);
			}else {
				System.out.println("null id application");
				List<JobApplication> applications = jobApplicationDao.getApplicationsForRecruiter(user.getId());
				return new ModelAndView("allJobApplicationsView","applications",applications);
			}
		}else {
			System.out.println("null user");
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/sendEmail.htm", method = RequestMethod.POST)
	public ModelAndView sendEmail(HttpServletRequest request, HttpServletResponse response, JobApplicationDao jobApplicationDao) {

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			String id = request.getParameter("applicationId");
			JobApplication application = jobApplicationDao.get(Integer.parseInt(id));
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("ta3404840@gmail.com", "accountForTesting"));
			email.setSSLOnConnect(true);
			try {
				System.out.println(user.getEmail());
				email.setFrom(user.getEmail());
				email.setSubject("Schedule Interview Call");
				email.setMsg(request.getParameter("emailBody"));
				email.addTo(application.getJobseekerProfile().getUser().getEmail());
				email.send();
				System.out.println("Email sent");
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			application.setStatus("First Round Interview");
			jobApplicationDao.save(application);
			List<JobApplication> applications = jobApplicationDao.getApplicationsForRecruiter(user.getId());
			return new ModelAndView("allJobApplicationsView","applications",applications);
		}else {
			System.out.println("null user");
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/rejectJobApplication.htm", method = RequestMethod.POST)
	public ModelAndView rejectJobApplication(HttpServletRequest request, HttpServletResponse response, JobApplicationDao jobApplicationDao) {

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			String id = request.getParameter("applicationId");
			JobApplication application = jobApplicationDao.get(Integer.parseInt(id));
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("ta3404840@gmail.com", "accountForTesting"));
			email.setSSLOnConnect(true);
			try {
				email.setFrom(user.getEmail());
				email.setSubject("Application Rejected");
				email.setMsg("Your Job Application for the role "+application.getJobPosting().getJobTitle()+" has been rejected.");
				email.addTo(application.getJobseekerProfile().getUser().getEmail());
				email.send();
				System.out.println("Email sent");
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			application.setStatus("Rejected");
			jobApplicationDao.save(application);
			List<JobApplication> applications = jobApplicationDao.getApplicationsForRecruiter(user.getId());
			return new ModelAndView("allJobApplicationsView","applications",applications);
		}else {
			System.out.println("null user");
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/recruiterSignOut.htm", method = RequestMethod.POST)
	public ModelAndView recruiterSignOut(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/allJobApplicationBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView allJobApplicationBackSignOut(HttpServletRequest request, HttpServletResponse response) {
		String action = request.getParameter("action");
		if(action.equals("Back")) {
			return new ModelAndView("recruiterLandingPage");
		}else {
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/allJobPostingBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView allJobPostingBackSignOut(HttpServletRequest request, HttpServletResponse response) {
		String action = request.getParameter("action");
		if(action.equals("Back")) {
			return new ModelAndView("recruiterLandingPage");
		}else {
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/addJobPostingBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView addJobPostingBackSignOut(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {
		String action = request.getParameter("action");
		if(action.equals("Back")) {
			HttpSession session = request.getSession();
			Users user = (Users) session.getAttribute("user");
			if(user != null) {
				List<JobPosting> jobs = recruiterDao.getJobs(user.getId());
				return new ModelAndView("allJobPostingsView","jobs",jobs);
			}else {
				System.out.println("null user");
				return new ModelAndView("home");
			}
		}else {
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/updateJobPostingBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView updateJobPostingBackSignOut(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {
		String action = request.getParameter("action");
		if(action.equals("Back")) {
			HttpSession session = request.getSession();
			Users user = (Users) session.getAttribute("user");
			if(user != null) {
				List<JobPosting> jobs = recruiterDao.getJobs(user.getId());
				return new ModelAndView("allJobPostingsView","jobs",jobs);
			}else {
				System.out.println("null user");
				return new ModelAndView("home");
			}
		}else {
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/applicationDetailsBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView applicationDetailsBackSignOut(HttpServletRequest request, HttpServletResponse response, JobApplicationDao jobApplicationDao) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			Users user = (Users) session.getAttribute("user");
			if(user != null) {
				session.removeAttribute("flag");
				session.removeAttribute("message");
				List<JobApplication> applications = jobApplicationDao.getApplicationsForRecruiter(user.getId());
				return new ModelAndView("allJobApplicationsView","applications",applications);
			}else {
				System.out.println("null user");
				session.invalidate();
				return new ModelAndView("home");
			}
		}else {
			session.invalidate();
			return new ModelAndView("home");
		}
	}

}
