package br.silva.io.social.hub.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

import br.silva.io.social.hub.vo.PostCommentVO;

/**
 * Entity implementation class for Entity: PostComment
 *
 */
@Entity
public class PostComment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	private String message;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@ManyToOne(targetEntity = UserPost.class)
	private UserPost post; 
	
	@ManyToOne(targetEntity = User.class)
	private User owner;
	
	public PostComment() {
		super();
		this.setCreatedAt();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserPost getPost() {
		return post;
	}

	public void setPost(UserPost post) {
		this.post = post;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
	
	public PostCommentVO toValueObject() {
		PostCommentVO vo = new PostCommentVO();
		
		vo.setMessage(message);
		vo.setPostId(post.getId());
		vo.setUsername(owner.getUsername());
		vo.setCreatedAt(createdAt);
		
		return vo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt() {
		this.createdAt = Calendar.getInstance().getTime();
	}
}
