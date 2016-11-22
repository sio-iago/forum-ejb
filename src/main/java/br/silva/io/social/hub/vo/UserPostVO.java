package br.silva.io.social.hub.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserPostVO {
	
	public Long id;
	
		public String title;
	
	public String description;
	
	public Date postedAt;
	
	public List<PostCommentVO> comments;
	
	public String username;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	public List<PostCommentVO> getComments() {
		return comments;
	}

	public void setComments(List<PostCommentVO> comments) {
		this.comments = comments;
	}

	public UserPostVO() {
		super();
		this.comments = new ArrayList<>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
