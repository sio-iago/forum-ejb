package br.silva.io.social.hub.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.silva.io.social.hub.vo.UserVO;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@NotNull
	@Column(unique = true)
	private String email;
	
	@NotNull
	@Column(unique = true)
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	@Column(name = "session_key", unique = true)
	private String sessionKey;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = UserPost.class)
	private UserPost posts;
	

	public UserPost getPosts() {
		return posts;
	}

	public void setPosts(UserPost posts) {
		this.posts = posts;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey() {
		UUID uniqueId = UUID.randomUUID();
		this.sessionKey = uniqueId.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public static String hashPassword(String password) throws NoSuchAlgorithmException{
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			
			String passwordHashed = new String(hash);
			return passwordHashed;
			
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}
	
	public void setPassword(String password) throws NoSuchAlgorithmException {
			
		try {
			this.password = User.hashPassword(password);
		} catch(NoSuchAlgorithmException e) {
			throw e;
		}
	}
	
	public boolean passwordMatches(String password) {
		return this.password.equals(password);
	}
	
	public User() {
		this.setSessionKey();
	}
	
	public User(String username, String password) throws NoSuchAlgorithmException {
		this();
		this.username = username;
		this.setPassword(password);
	}
	
	public UserVO toValueObject() {
		UserVO userVO = new UserVO();
		userVO.setUsername(username);
		userVO.setEmail(email);
		userVO.setSessionKey(sessionKey);
		
		return userVO;
	}
}
