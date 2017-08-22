package com.example.buildingspringrestservice.model.bookmarks;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Cada Account pode ter nenhum, um, ou muitas entidades Bookmark uma relacao 1:N

@Entity
public class Account {	
	
	@OneToMany(mappedBy = "account")
	private Set<Bookmark> bookmarks = new HashSet<>();
	
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	public String password;
	public String username;
	
	Account(){}
	
	public Account(String name, String password){
		this.username = name;
		this.password = password;
	}
	
	public Set<Bookmark> getBookmarks(){
		return bookmarks;
	}
	
	public Long getId() {
		return id;
	}

}
