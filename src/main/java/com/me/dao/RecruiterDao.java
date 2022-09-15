package com.me.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.me.pojo.JobPosting;
import com.me.pojo.Users;

@Component
public class RecruiterDao extends DAO {
	
	public void save(JobPosting job) {
		try {
			begin();
			getSession().saveOrUpdate(job);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
	}
	
	public List<JobPosting> getJobs(int companyId) {
		 try {
	           begin();
	           System.out.println("comapny ID= "+companyId);
	           Query query = getSession().createQuery("From JobPosting where companyId= :companyId");
	           query.setParameter("companyId", companyId);
	           List<JobPosting> jobs = query.list();
	           commit();
	           return jobs;
	        } catch (HibernateException e) {
	            rollback();
	            e.printStackTrace();
	            throw new HibernateException(e);
	        }
	}
	
	public JobPosting get(int id) {
		JobPosting job = null;
		try {
			begin();
			job = (JobPosting) getSession().get(JobPosting.class, id);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return job;
	}
	
	public void delete(JobPosting job) {
		try {
			begin();
			getSession().delete(job);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
	}
	
	public List<JobPosting> getActiveJobs() {
		 try {
	           begin();
//	           System.out.println("comapny ID= "+companyId);
	           Query query = getSession().createQuery("From JobPosting where status= :status");
	           query.setParameter("status", "Active");
	           List<JobPosting> jobs = query.list();
	           commit();
	           return jobs;
	        } catch (HibernateException e) {
	            rollback();
	            e.printStackTrace();
	            throw new HibernateException(e);
	        }
	}
	
}