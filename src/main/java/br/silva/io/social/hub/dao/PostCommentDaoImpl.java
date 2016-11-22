package br.silva.io.social.hub.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.silva.io.social.hub.model.PostComment;

public class PostCommentDaoImpl implements PostCommentDao {

	@Inject
    private EntityManager em;
	
	@Override
	public PostComment save(PostComment comment) {
		em.persist(comment);
		
		return comment;
	}

	@Override
	public void delete(Long id) {
		PostComment comment = em.find(PostComment.class, id);
		
		if(comment != null)
			em.remove(comment);
	}

}
