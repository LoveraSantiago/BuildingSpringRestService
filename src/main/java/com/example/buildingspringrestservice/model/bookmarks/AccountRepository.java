package com.example.buildingspringrestservice.model.bookmarks;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Optional<Account> findByUserName(String username);

}
