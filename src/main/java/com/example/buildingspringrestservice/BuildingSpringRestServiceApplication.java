package com.example.buildingspringrestservice;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.buildingspringrestservice.model.bookmarks.Account;
import com.example.buildingspringrestservice.model.bookmarks.AccountRepository;
import com.example.buildingspringrestservice.model.bookmarks.Bookmark;
import com.example.buildingspringrestservice.model.bookmarks.BookmarkRepository;

@SpringBootApplication
public class BuildingSpringRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildingSpringRestServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(AccountRepository accountRepository, BookmarkRepository bookMarkRepository){
		return (evt) -> Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
							  .forEach((a) ->{
								  Account account = accountRepository.save(new Account(a, "password"));
								  bookMarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
								  bookMarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));
							  });
	}
}
