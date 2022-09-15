package com.me.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.me.pojo.JobApplication;
import com.me.pojo.JobPosting;
import com.me.pojo.Users;

@Component
public class JobApplicationDao extends DAO {
	
	public void save(JobApplication application) {
		try {
			begin();
			getSession().saveOrUpdate(application);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
	}
	
	public JobApplication getApplication(int jobseekerProfile_jobseekerId, int jobPosting_jobId) {
		JobApplication application = null;
		try {
			begin();
			Query query = getSession().createQuery("from JobApplication where jobseekerProfile_jobseekerId= :jobseekerProfile_jobseekerId and jobPosting_jobId= :jobPosting_jobId");
			query.setParameter("jobseekerProfile_jobseekerId", jobseekerProfile_jobseekerId);
			query.setParameter("jobPosting_jobId", jobPosting_jobId);
			application = (JobApplication) query.uniqueResult();
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return application;
	}
	
	public List<JobApplication> getApplicationsForJobseeker(int jobseekerProfile_jobseekerId) {
		List<JobApplication> list = null;
		try {
			begin();
			Query query = getSession().createQuery("from JobApplication where jobseekerProfile_jobseekerId= :jobseekerProfile_jobseekerId");
			query.setParameter("jobseekerProfile_jobseekerId", jobseekerProfile_jobseekerId);
			list = query.list();
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return list;
	}
	
	public List<JobApplication> getApplicationsForRecruiter(int companyId) {
		List<JobApplication> list = null;
		try {
			begin();
			Query query = getSession().createQuery("from JobApplication where companyId= :companyId");
			query.setParameter("companyId", companyId);
			list = query.list();
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return list;
	}
	
//	public List<JobPosting> getJobs(int companyId) {
//		 try {
//	           begin();
//	           System.out.println("comapny ID= "+companyId);
//	           Query query = getSession().createQuery("From JobPosting where companyId= :companyId");
//	           query.setParameter("companyId", companyId);
//	           List<JobPosting> jobs = query.list();
//	           commit();
//	           return jobs;
//	        } catch (HibernateException e) {
//	            rollback();
//	            e.printStackTrace();
//	            throw new HibernateException(e);
//	        }
//	}
//	
	public JobApplication get(int id) {
		JobApplication application = null;
		try {
			begin();
			application = (JobApplication) getSession().get(JobApplication.class, id);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return application;
	}
	
	public void delete(JobApplication application) {
		try {
			begin();
			getSession().delete(application);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
	}
//	
//	public List<JobPosting> getActiveJobs() {
//		 try {
//	           begin();
////	           System.out.println("comapny ID= "+companyId);
//	           Query query = getSession().createQuery("From JobPosting where status= :status");
//	           query.setParameter("status", "Active");
//	           List<JobPosting> jobs = query.list();
//	           commit();
//	           return jobs;
//	        } catch (HibernateException e) {
//	            rollback();
//	            e.printStackTrace();
//	            throw new HibernateException(e);
//	        }
//	}
	
}