package com.me.pojo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Entity(name = "JobseekerProfile")
@Table(name = "jobseekers")
public class JobseekerProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int jobseekerId;
	private String photoName;
	private String resumeName;
	@Column(length = 2000)
	private String about;
	@Column(length = 1000)
	private String skills;
	@OneToOne
	private Users user;
	@Transient
	private MultipartFile photo;
	@Transient
	private MultipartFile resume;
	
	public int getJobseekerId() {
		return jobseekerId;
	}
	public void setJobseekerId(int jobseekerId) {
		this.jobseekerId = jobseekerId;
	}
	public MultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getResumeName() {
		return resumeName;
	}
	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public MultipartFile getResume() {
		return resume;
	}
	public void setResume(MultipartFile resume) {
		this.resume = resume;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
}
