package com.api.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

	@Entity
	@Table(name="user_details")
	@Getter
	@Setter
	@ToString
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
		
	//	private String profilePicture; 
		
		//^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])
	/*	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z]).{6,20})", 
				message="Password Should Be like 1 UpperCase 1 Lower Case 1 Special Symbol")*/
		private String password;
		
		private boolean isverification;
		
		private String profileImage;
		public User() 
		{
								
		}

	}
	
	/*
	 * create table details (id int not null,user_name varchar(100) not null,
	  email varchar(100) not null, phone varchar(15) not null,password varchar(100) not null,primary key(id)); 
	 * */
