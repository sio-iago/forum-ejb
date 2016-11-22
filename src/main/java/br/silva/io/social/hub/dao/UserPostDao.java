package br.silva.io.social.hub.dao;

import java.util.List;

import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.model.UserPost;
import br.silva.io.social.hub.vo.UserPostVO;

public interface UserPostDao {

	public List<UserPostVO> listLastPosts(Integer pageSize);
	
	public UserPost savePost(UserPost userPost);

	public UserPost getPost(Long id);
	
}
