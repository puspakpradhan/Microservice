package com.example.demo.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		//User user = repository.findByUserName(userName);//working fine
		Optional<User> user = repository.findById(Integer.valueOf(userName));
		
		String userNameFRomDB = null;
		String passwordFromDB = null;
		if(null != user) {
			 userNameFRomDB = user.get().getUserName();
			 passwordFromDB = user.get().getPassword();
		}
		 
		
		return new org.springframework.security.core.userdetails.User(userName ,passwordFromDB,new ArrayList<>());
	}

}
