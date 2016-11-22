package br.silva.io.social.hub.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.model.UserPost;
import br.silva.io.social.hub.vo.UserPostVO;

@ApplicationScoped
public class UserPostDaoImpl implements UserPostDao {

	@Inject
    private EntityManager em;

	public static Integer PAGE_SIZE = 20;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPostVO> listLastPosts(Integer page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserPost> criteria = cb.createQuery(UserPost.class);
        
        Root<UserPost> userPost = criteria.from(UserPost.class);
        
        criteria.select(userPost).orderBy(cb.desc(userPost.get("postedAt")));
		
        List<UserPost> posts = em.createQuery(criteria).setMaxResults(PAGE_SIZE).setFirstResult(PAGE_SIZE*page).getResultList();
        
        List<UserPostVO> vos = new ArrayList<>();
        
        if(posts != null && posts.size()>0) {
        	for(UserPost post : posts) {
        		vos.add(post.toValueObject());
        	}
        }
        
		return vos;
	}

	@Override
	public UserPost savePost(UserPost userPost) {		
		em.persist(userPost);
		return userPost;
	}

	@Override
	public UserPost getPost(Long id) {
		return em.find(UserPost.class, id);
	}

}
