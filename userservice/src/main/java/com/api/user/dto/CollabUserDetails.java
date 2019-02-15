package com.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class CollabUserDetails {
	
	private String email;
	private String image;
	
	public CollabUserDetails () {
		
	}
	public CollabUserDetails(String email) {
		this.email=email;
	}
	
	

}
