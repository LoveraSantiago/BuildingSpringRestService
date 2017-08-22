package com.example.buildingspringrestservice.rest.bookmarks;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.buildingspringrestservice.model.bookmarks.AccountRepository;
import com.example.buildingspringrestservice.model.bookmarks.Bookmark;
import com.example.buildingspringrestservice.model.bookmarks.BookmarkRepository;

@RestController
@RequestMapping("{userId}/bookmarks")
public class BookmarkRestController {
	
	private final BookmarkRepository bookmarkRepository;
	private final AccountRepository accountRepository;
	
	@Autowired
	BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
		this.bookmarkRepository = bookmarkRepository;
		this.accountRepository = accountRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Collection<Bookmark> readBookmarks(@PathVariable String userId){		
		validateUser(userId);
		return this.bookmarkRepository.findByAccountUserName(userId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input){
		this.validateUser(userId);
		return this.accountRepository
						.findByUserName(userId)
						.map(account -> {
							Bookmark result = bookmarkRepository.save(new Bookmark(account, input.uri, input.description));
							URI location = ServletUriComponentsBuilder
									.fromCurrentRequest().path("/{id}")
									.buildAndExpand(result.getId()).toUri();
							return ResponseEntity.created(location).build();
						})
						.orElse(ResponseEntity.noContent().build());
						
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
	Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId){
		this.validateUser(userId);
		return this.bookmarkRepository.findOne(bookmarkId);
	}
	
	
	private void validateUser(String userId){
		this.accountRepository.findByUserName(userId)
							  .orElseThrow(()-> new UserNotFoundException(userId));
	}
}
