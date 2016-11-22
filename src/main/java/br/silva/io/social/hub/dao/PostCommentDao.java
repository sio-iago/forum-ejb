package br.silva.io.social.hub.dao;

import br.silva.io.social.hub.model.PostComment;

public interface PostCommentDao {
	
	public PostComment save(PostComment comment);

	public void delete(Long id);
	
}
