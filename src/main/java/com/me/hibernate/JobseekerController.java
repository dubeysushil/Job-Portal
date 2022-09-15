package com.me.hibernate;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;

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
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.JobApplicationDao;
import com.me.dao.JobseekerDao;
import com.me.dao.RecruiterDao;
import com.me.dao.UserDao;
import com.me.pojo.JobApplication;
import com.me.pojo.JobPosting;
import com.me.pojo.JobseekerProfile;
import com.me.pojo.Users;
//import com.yusuf.spring.pojo.Advert;

/**
 * Handles requests for the application home page.
 */
@Controller
public class JobseekerController {
	
	@RequestMapping(value = "/viewProfile.htm", method = RequestMethod.POST)
	public ModelAndView viewProfile(HttpServletRequest request, HttpServletResponse response, JobseekerDao jobseekerDao, ModelMap model, JobseekerProfile jobseekerProfile) {
		
		String jobseekerId = request.getParameter("jobseekerId");
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		System.out.println("jobseeker id= "+jobseekerId);
		if(user != null) {
			session.removeAttribute("flag");
			session.removeAttribute("message");
			JobseekerProfile jobseeker = jobseekerDao.get(Integer.parseInt(jobseekerId));
			if(jobseeker != null) {
				model.addAttribute("jobseekerProfile", jobseekerProfile);
				System.out.println("userrrrr in jobseeker="+ jobseeker.getUser().getFirstName());
				return new ModelAndView("jobseekerProfileView","jobseeker",jobseeker);
			}else {
				JobseekerProfile newJobseeker = new JobseekerProfile();
				model.addAttribute("jobseekerProfile", jobseekerProfile);
				return new ModelAndView("jobseekerProfileView","jobseeker",newJobseeker);
			}
		}else {
			System.out.println("null user");
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/updateJobseekerProfile.htm", method = RequestMethod.POST)
	public ModelAndView updateJobseekerProfile(@ModelAttribute("jobseekerProfile") JobseekerProfile jobseekerProfile, HttpServletRequest request, HttpServletResponse response, JobseekerDao jobseekerDao, UserDao userDao) {
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			MultipartFile photo = jobseekerProfile.getPhoto();
			MultipartFile resume = jobseekerProfile.getResume();
			String[] photoArray = photo.getOriginalFilename().split("[.]",0);
			String[] resumeArray = resume.getOriginalFilename().split("[.]",0);
			String photoExtension = photoArray[photoArray.length-1];
			String resumeExtension = resumeArray[resumeArray.length-1];
			if(!photoExtension.equals("jpeg") || !resumeExtension.equals("pdf")) {
				System.out.println("Upload only jpeg for photo and pdf for resume");
				JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
				if(jobseeker == null) {
					JobseekerProfile newJobseeker = new JobseekerProfile();
					return new ModelAndView("jobseekerLandingPage","jobseeker",newJobseeker);
				}else {
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
			}
			String photoName = user.getFirstName()+"_"+System.currentTimeMillis()+"_img_"+user.getId()+".jpeg";
			String resumeName = user.getFirstName()+"_"+System.currentTimeMillis()+"_resume_"+user.getId()+".pdf";
			try {
				jobseekerProfile.getPhoto().transferTo(new File("C:\\Users\\dubey\\Downloads\\project (3)\\project\\src\\main\\webapp\\filesUploads\\" + photoName));
				jobseekerProfile.getResume().transferTo(new File("C:\\Users\\dubey\\Downloads\\project (3)\\project\\src\\main\\webapp\\filesUploads\\" + resumeName));
				JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
				if(jobseeker == null) {
					JobseekerProfile newJobSeeker = new JobseekerProfile();
					newJobSeeker.setAbout(request.getParameter("about"));
					newJobSeeker.setPhotoName(photoName);
					newJobSeeker.setResumeName(resumeName);
					newJobSeeker.setSkills(request.getParameter("skills"));
					user.setFirstName(request.getParameter("firstName"));
					user.setLastName(request.getParameter("lastName"));
					user.setContactNo(Long.parseLong(request.getParameter("contactNo")));
					newJobSeeker.setUser(user);
					userDao.save(user);
					jobseekerDao.save(newJobSeeker);
					System.out.println("Profile Updated");
					return new ModelAndView("jobseekerLandingPage","jobseeker",newJobSeeker);
				}else {
					jobseeker.setAbout(request.getParameter("about"));
					jobseeker.setPhotoName(photoName);
					jobseeker.setResumeName(resumeName);
					jobseeker.setSkills(request.getParameter("skills"));
					user.setFirstName(request.getParameter("firstName"));
					user.setLastName(request.getParameter("lastName"));
					user.setContactNo(Long.parseLong(request.getParameter("contactNo")));
					jobseeker.setUser(user);
					userDao.save(user);
					jobseekerDao.save(jobseeker);
					System.out.println("Profile Updated");
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
			} catch (IllegalStateException e1) {
				System.out.println("IllegalStateException: " + e1.getMessage());
				System.out.println("Not Updated");
				return new ModelAndView("home");
			} catch (IOException e1) {
				System.out.println("IOException: " + e1.getMessage());
				System.out.println("Not Updated");
				return new ModelAndView("home");
			}
		}else {
			System.out.println("null user");
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/viewJobPostings.htm", method = RequestMethod.POST)
	public ModelAndView viewJobPostings(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			session.removeAttribute("flag");
			session.removeAttribute("message");
			List<JobPosting> jobs = recruiterDao.getActiveJobs();
			return new ModelAndView("availableJobs","jobs",jobs);
		}else {
			System.out.println("null user");
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/viewJobDetails.htm", method = RequestMethod.POST)
	public ModelAndView viewJobDetails(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			String jobId = request.getParameter("jobId");
			if(jobId != null) {
				JobPosting job = recruiterDao.get(Integer.parseInt(jobId));
				return new ModelAndView("jobDetailsView","job",job);
			}else {
				System.out.println("null id job");
				List<JobPosting> jobs = recruiterDao.getActiveJobs();
				return new ModelAndView("availableJobs","jobs",jobs);
			}
		}else {
			System.out.println("null user");
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/applyForJob.htm", method = RequestMethod.POST)
	public ModelAndView applyForJob(HttpServletRequest request, HttpServletResponse response, JobseekerDao jobseekerDao, RecruiterDao recruiterDao, JobApplicationDao jobApplicationDao) {
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
			if(jobseeker == null) {
				System.out.println("update profile first");
				JobseekerProfile newJobseeker = new JobseekerProfile();
				return new ModelAndView("jobseekerLandingPage","jobseeker",newJobseeker);
			}else {
				JobPosting job = recruiterDao.get(Integer.parseInt(request.getParameter("id")));
				JobApplication application = jobApplicationDao.getApplication(jobseeker.getJobseekerId(), job.getJobId());
				if(application == null) {
					JobApplication newApplication = new JobApplication();
					newApplication.setDate(new Date());
					newApplication.setStatus("Under Consideration");
					newApplication.setJobseekerProfile(jobseeker);
					newApplication.setJobPosting(job);
					newApplication.setCompanyId(job.getCompanyId());
					jobApplicationDao.save(newApplication);
					System.out.println("Applied successfully");
					session.setAttribute("flag", "Success");
					session.setAttribute("message", "Applied Successfully.");
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}else {
					System.out.println("Already applied");
					session.setAttribute("flag", "Error");
					session.setAttribute("message", "You have already applied for this job.");
					List<JobPosting> jobs = recruiterDao.getActiveJobs();
					return new ModelAndView("availableJobs","jobs",jobs);
				}
			}
		}else {
			System.out.println("null user");
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/viewJobApplications.htm", method = RequestMethod.POST)
	public ModelAndView viewJobApplications(HttpServletRequest request, HttpServletResponse response, JobApplicationDao jobApplicationDao, JobseekerDao jobseekerDao) {
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			session.removeAttribute("flag");
			session.removeAttribute("message");
			JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
			if(jobseeker != null) {
				List<JobApplication> applications = jobApplicationDao.getApplicationsForJobseeker(jobseeker.getJobseekerId());
				if(applications != null) {
					return new ModelAndView("jobApplications","applications",applications);
				}else {
					System.out.println("No Applications");
					session.setAttribute("flag", "Error.");
					session.setAttribute("message", "You don't have any applications.");
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
			}else {
				System.out.println("update profile first");
				session.setAttribute("flag", "Error.");
				session.setAttribute("message", "Update your profile first before applying for the job.");
				JobseekerProfile newJobseeker = new JobseekerProfile();
				return new ModelAndView("jobseekerLandingPage","jobseeker",newJobseeker);
			}
		}else {
			System.out.println("null user");
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/deleteJobApplication.htm", method = RequestMethod.POST)
	public ModelAndView deleteJobApplication(HttpServletRequest request, HttpServletResponse response, JobApplicationDao jobApplicationDao, JobseekerDao jobseekerDao) {
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if(user != null) {
			String id = request.getParameter("applicationId");
			if(id != null) {
				JobApplication application = jobApplicationDao.get(Integer.parseInt(id));
				JobseekerProfile jobseeker = application.getJobseekerProfile();
				jobApplicationDao.delete(application);
				System.out.println("Application Deleted");
				List<JobApplication> applications = jobApplicationDao.getApplicationsForJobseeker(jobseeker.getJobseekerId());
				if(applications != null) {
					return new ModelAndView("jobApplications","applications",applications);
				}else {
					System.out.println("No Applications");
					session.setAttribute("flag", "Error.");
					session.setAttribute("message", "You don't have any applications.");
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
			}else {
				System.out.println("select one to delete");
				JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
				List<JobApplication> applications = jobApplicationDao.getApplicationsForJobseeker(jobseeker.getJobseekerId());
				if(applications != null) {
					return new ModelAndView("jobApplications","applications",applications);
				}else {
					System.out.println("No Applications");
					session.setAttribute("flag", "Error.");
					session.setAttribute("message", "You don't have any applications.");
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
			}
		}else {
			System.out.println("null user");
			session.invalidate();
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/jobseekerSignOut.htm", method = RequestMethod.POST)
	public ModelAndView jobseekerSignOut(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/jobseekerProfileBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView jobseekerProfileBackSignOut(HttpServletRequest request, HttpServletResponse response, JobseekerDao jobseekerDao) {
		String action = request.getParameter("action");
		if(action.equals("Back")) {
			HttpSession session = request.getSession();
			Users user = (Users) session.getAttribute("user");
			if(user != null) {
				JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
				if(jobseeker == null) {
					JobseekerProfile newJobseeker = new JobseekerProfile();
					return new ModelAndView("jobseekerLandingPage","jobseeker",newJobseeker);
				}else {
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
			}else {
				System.out.println("null user");
				return new ModelAndView("home");
			}
		}else {
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/availableJobsBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView availableJobsBackSignOut(HttpServletRequest request, HttpServletResponse response, JobseekerDao jobseekerDao) {
		String action = request.getParameter("action");
		if(action.equals("Back")) {
			HttpSession session = request.getSession();
			Users user = (Users) session.getAttribute("user");
			if(user != null) {
				session.removeAttribute("flag");
				session.removeAttribute("message");
				JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
				if(jobseeker == null) {
					JobseekerProfile newJobseeker = new JobseekerProfile();
					return new ModelAndView("jobseekerLandingPage","jobseeker",newJobseeker);
				}else {
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
			}else {
				System.out.println("null user");
				return new ModelAndView("home");
			}
		}else {
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/jobDetailsBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView jobDetailsBackSignOut(HttpServletRequest request, HttpServletResponse response, RecruiterDao recruiterDao) {
		String action = request.getParameter("action");
		if(action.equals("Back")) {
			HttpSession session = request.getSession();
			Users user = (Users) session.getAttribute("user");
			if(user != null) {
				List<JobPosting> jobs = recruiterDao.getActiveJobs();
				return new ModelAndView("availableJobs","jobs",jobs);
			}else {
				System.out.println("null user");
				return new ModelAndView("home");
			}
		}else {
			return new ModelAndView("home");
		}
	}
	
	@RequestMapping(value = "/jobApplicationsBackSignOut.htm", method = RequestMethod.POST)
	public ModelAndView jobApplicationsBackSignOut(HttpServletRequest request, HttpServletResponse response, JobseekerDao jobseekerDao) {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("Back")) {
			Users user = (Users) session.getAttribute("user");
			if(user != null) {
				session.removeAttribute("flag");
				session.removeAttribute("message");
				JobseekerProfile jobseeker = jobseekerDao.getJobProfileByUserId(user.getId());
				if(jobseeker == null) {
					JobseekerProfile newJobseeker = new JobseekerProfile();
					return new ModelAndView("jobseekerLandingPage","jobseeker",newJobseeker);
				}else {
					return new ModelAndView("jobseekerLandingPage","jobseeker",jobseeker);
				}
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
