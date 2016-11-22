package br.silva.io.social.hub.rest.controller;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.service.AuthService;
import br.silva.io.social.hub.vo.UserVO;

@Path("/auth")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

	@Inject
	private AuthService authService;
	
	@Inject
    private Logger log;
	
	@POST
	@Path("/register")
	public Response registerUser(UserVO user) {
		
		Response.ResponseBuilder builder = null;
		
		try {
			UserVO registeredUser = authService.registerUser(user.getUsername(), user.getPassword(), user.getEmail()); 
			
			builder = Response.ok(registeredUser);
			
		} catch(Exception ex) {
			log.warning(ex.getMessage());
			builder = Response.serverError();
		}
		
		return builder.build();
	}
	
	@POST
	@Path("/login")
	public Response login(UserVO user) {
		
		Response.ResponseBuilder builder = null;
		
		try {
			UserVO retrievedUser = authService.login(user.getUsername(), user.getPassword()); 
			
			builder = Response.ok(retrievedUser);
			
		} catch(Exception ex) {
			log.warning(ex.getMessage());
			builder = Response.serverError();
		}
		
		return builder.build();
	}
}
