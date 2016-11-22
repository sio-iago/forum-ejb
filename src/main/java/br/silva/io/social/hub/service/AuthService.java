package br.silva.io.social.hub.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.silva.io.social.hub.dao.UserDao;
import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.vo.UserVO;

@Stateless
public class AuthService {

	@Inject
    private Logger log;

    @Inject
    private UserDao userDao;
    
    
    public UserVO registerUser(String username, String password, String email) throws Exception {
    	
    	log.info("Registering user @["+username+"]");
    	
    	User user = new User(username, password);
    	user.setEmail(email);
    	
    	try {
    		userDao.save(user);
    		UserVO vo = user.toValueObject();
    		
    		return vo;
    	}
    	catch (Exception ex) {
    		log.warning("Error trying to persist user @["+username+"]\n"+ex.getMessage());
    		throw ex;
    	}
    }
	
    public UserVO login(String username, String password) throws Exception {
    	
    	try {
    		User user = userDao.findOneByUsername(username);
    		String passwordHashed = User.hashPassword(password);
    		
    		if(user != null && user.passwordMatches(passwordHashed)) {
    			
    			user.setSessionKey();
    			userDao.save(user);
    			
    			return user.toValueObject();
    		}
    		else {
    			throw new Exception("No user matched for @["+username+"]");
    		}
    	}
    	catch (Exception ex) {
    		log.warning("Error trying to login user @["+username+"]\n"+ex.getMessage());
    		throw ex;
    	}
    	
    }
    
    public boolean isUserSessionKeyValid(String sessionKey) {
    	if(sessionKey == null)
    		return false;
    	
    	User user = userDao.findOneBySessionKey(sessionKey);
    	
    	return (user instanceof User);
    }
    
    public User getUserBySessionKey(String sessionKey) {
    	return isUserSessionKeyValid(sessionKey) ? userDao.findOneBySessionKey(sessionKey) : null;
    }
}
