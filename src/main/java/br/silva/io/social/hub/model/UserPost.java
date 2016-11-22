package br.silva.io.social.hub.model;

import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.vo.UserPostVO;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;


/**
 * Entity implementation class for Entity: UserPost
 *
 */
@Entity
public class UserPost implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	private String description;
	
	@Column(name = "posted_at")
	private Date postedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	private User owner;
	
	@OneToMany(targetEntity = PostComment.class)
	@OrderBy(value="createdAt desc")
	private Set<PostComment> comments;

	public UserPost() {
		super();
		this.setPostedAt(Calendar.getInstance().getTime());
		this.comments = new HashSet<>();
	}   
	
	public UserPost(String title, String description) {
		this();
		this.title = title;
		this.description = description;
	}
	
	public UserPost(User owner, String title, String description) {
		this(title, description);
		this.owner = owner;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}   
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}   
	public String getDescription() {
		return this.description;
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

	public Set<PostComment> getComments() {
		return comments;
	}

	public void setComments(Set<PostComment> comments) {
		this.comments = comments;
	}
   
	public UserPostVO toValueObject() {
		UserPostVO vo = new UserPostVO();
		vo.setId(id);
		vo.setTitle(title);
		vo.setDescription(description);
		vo.setPostedAt(postedAt);
		vo.setUsername(owner.getUsername());
		
		if(comments != null && comments.size() > 0) {
			for(PostComment comment : comments) {
				vo.getComments().add(comment.toValueObject());
			}
		}
		
		return vo;
	}
}
