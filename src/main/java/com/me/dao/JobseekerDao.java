package com.me.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.me.pojo.JobPosting;
import com.me.pojo.JobseekerProfile;
import com.me.pojo.Users;

@Component
public class JobseekerDao extends DAO {
	
	public void save(JobseekerProfile profile) {
		try {
			begin();
			getSession().saveOrUpdate(profile);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
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
	public JobseekerProfile getJobProfileByUserId(int user_id) {
		JobseekerProfile jobseeker = null;
		try {
			begin();
			Query query = getSession().createQuery("from JobseekerProfile where user_id= :user_id");
			query.setParameter("user_id", user_id);
			jobseeker = (JobseekerProfile) query.uniqueResult();
//			job = (JobseekerProfile) getSession().get();
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return jobseeker;
	}
	
	public JobseekerProfile get(int id) {
		JobseekerProfile jobseeker = null;
		try {
			begin();
			jobseeker = (JobseekerProfile) getSession().get(JobseekerProfile.class, id);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return jobseeker;
	}
//	
//	public void delete(JobPosting job) {
//		try {
//			begin();
//			getSession().delete(job);
//			commit();
//		} catch (HibernateException e) {
//			rollback();
//			e.printStackTrace();
//		}
//	}
	
}