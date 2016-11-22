package br.silva.io.social.hub.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.silva.io.social.hub.dao.PostCommentDao;
import br.silva.io.social.hub.dao.UserPostDao;
import br.silva.io.social.hub.model.PostComment;
import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.model.UserPost;
import br.silva.io.social.hub.vo.PostCommentVO;

@Stateless
public class PostCommentService {
	
	@Inject
	private PostCommentDao commentDao;
	
	@Inject
	private UserPostDao postDao;
	
	@Inject
	private AuthService authService;
	
	public PostCommentVO createComment(String sessionKey, Long postId, String message) {
		UserPost post = postDao.getPost(postId);
		User user = authService.getUserBySessionKey(sessionKey);
		
		PostComment comment = new PostComment();
		comment.setMessage(message);
		comment.setPost(post);
		comment.setOwner(user);
		post.getComments().add(comment);
		
		commentDao.save(comment);
		
		return comment.toValueObject();
	}
	
	
}
