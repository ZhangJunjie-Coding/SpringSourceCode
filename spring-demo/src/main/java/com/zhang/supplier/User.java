package com.zhang.supplier;

public class User {
	private String username;

	public User() {
	}

	public User(String name) {
		this.username = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				'}';
	}
}
