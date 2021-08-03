package com.buddy.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddy.model.Users;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<Users, Long> {
	
	Users findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	Optional<Users> findById(Long id);
	
	Long findIdByUsername(String username);

}
