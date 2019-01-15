package com.api.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

	@Entity
	@Table(name="user_details")
	public class User implements Serializable
	{
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="id")
		private Long id;
		
		@Column(name="user_name")
		
		private String name;
		
		//@UniqueElements
		
		private String email;
		
		@Pattern(regexp = "[0-9]{10}", message = "Number Should Only Be Digit And 10 digit only")
		private String mobileNumber;
		
		//^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])
	/*	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z]).{6,20})", 
				message="Password Should Be like 1 UpperCase 1 Lower Case 1 Special Symbol")*/
		private String password;
		
		private boolean isverification;
		
		public boolean isIsverification() 
		{
			return isverification;
		}

		public void setIsverification(boolean isverification) {
			this.isverification = isverification;
		}

		public User() 
		{
			
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", email=" + email + ", mobileNumber=" + mobileNumber
					+ ", password=" + password + "]";
		}
	}
	
	/*
	 * create table details (id int not null,user_name varchar(100) not null,
	  email varchar(100) not null, phone varchar(15) not null,password varchar(100) not null,primary key(id)); 
	 * */
