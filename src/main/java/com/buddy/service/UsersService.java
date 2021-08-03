package com.buddy.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buddy.model.Users;
import com.buddy.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service @Transactional @Slf4j
public class UsersService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = userRepo.findByUsername(username);
		
		if(user.equals(null)) {
			
			log.error("User not found in the DB");
			
			throw new UsernameNotFoundException("User not found in the DB");
			
		} else {
			
			log.info("User found in the DB : {}", username);
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
//		user.getRoles().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		});
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}
	
	public Users getUser(String username) {
		
		log.info("Fetching user {}", username);
		
		return userRepo.findByUsername(username);
		
	}
	
	public Optional<Users> getUserById(Long id) {
		
		log.info("Users found");
		
		return userRepo.findById(id);
		
	}
	
	public List<Users> getUsers() {
		
		log.info("Fetching all users");
		
		return userRepo.findAll();
		
	}
	
	public Users saveUser(Users users) {
		
		log.info("Saving new user {} to the DB", users.getUsername());
		
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		
		return userRepo.save(users);
		
	}
	
	public Users updateUser(Users user) {
		
		return userRepo.save(user);
		
	}
	
	public void deleteByUsername(String username) {
		
		userRepo.deleteById(userRepo.findIdByUsername(username));   
		
	}

	
	

}
