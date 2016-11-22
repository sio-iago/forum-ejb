package br.silva.io.social.hub.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.silva.io.social.hub.model.User;


@ApplicationScoped
public class UserDaoImpl implements UserDao {

	@Inject
    private EntityManager em;

	public User findById(Long id) {
		return em.find(User.class, id);
	}

	public User findOneByUsername(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        
        Root<User> user = criteria.from(User.class);
        
        criteria.select(user).where(cb.equal(user.get("username"), username));
        return em.createQuery(criteria).getSingleResult();
	}

	public void save(User user) {
		em.persist(user);
	}

	@Override
	public User findOneBySessionKey(String sessionKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        
        Root<User> user = criteria.from(User.class);
        
        try {
	        criteria.select(user).where(cb.equal(user.get("sessionKey"), sessionKey));
	        return em.createQuery(criteria).getSingleResult();
        } catch(Exception ex) {
        	return null;
        }
	}

}
