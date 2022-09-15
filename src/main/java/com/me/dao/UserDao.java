package com.me.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.me.pojo.Users;

@Component
public class UserDao extends DAO {
	
	public void save(Users user) {
		try {
			begin();
			getSession().saveOrUpdate(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
	}
	
	public Users getUser(String email) {
		Users user = null;
		try {
			begin();
			Query query = getSession().createQuery("from Users where email= :email");
			query.setParameter("email", email);
			user = (Users) query.uniqueResult();
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return user;
	}
	
	public List<Users> getUsers(String type) {
		 try {
	           begin();
	           Query query = getSession().createQuery("From Users where type= :type");
	           query.setParameter("type", type);
	           List<Users> users = query.list();
	           commit();
	           return users;
	        } catch (HibernateException e) {
	            rollback();
	            e.printStackTrace();
	            throw new HibernateException(e);
	        }
	
	}

	public Users get(int id) {
		Users user = null;
		try {
			begin();
			user = (Users) getSession().get(Users.class, id);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
		return user;
	}
	
	public void delete(Users user) {
		try {
			begin();
			getSession().delete(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			e.printStackTrace();
		}
	}
}