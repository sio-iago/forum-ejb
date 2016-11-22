package br.silva.io.social.hub.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.silva.io.social.hub.dao.UserPostDao;
import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.model.UserPost;
import br.silva.io.social.hub.vo.UserPostVO;

@Stateless
public class UserPostService {
	
	@Inject
	private UserPostDao postDao;
	
	@Inject
	private AuthService authService;
	
	public UserPost getPost(Long id) {
		return postDao.getPost(id);
	}
	
	public List<UserPostVO> listLastPosts(Integer page) {
		if(page == null || page<0)
			page = 0;
		
		return postDao.listLastPosts(page);
	}
	
	public UserPostVO updatePost(Long id, String title, String description) {
		
		UserPost post = this.getPost(id);
		post.setTitle(title);
		post.setDescription(description);
		
		postDao.savePost(post);
		
		return post.toValueObject();
	}
	
	public UserPostVO savePost(String ownerSessionKey, String title, String description) {
		
		User owner = authService.getUserBySessionKey(ownerSessionKey);
		
		UserPost post = new UserPost(owner, title, description);
		postDao.savePost(post);
		
		return post.toValueObject();
	}
	
}
