package br.silva.io.social.hub.dao;

import br.silva.io.social.hub.model.User;

public interface UserDao {
	
	public User findById(Long id);
	
	public User findOneByUsername(String username);
	
	public User findOneBySessionKey(String sessionKey);
	
	public void save(User user);
}
