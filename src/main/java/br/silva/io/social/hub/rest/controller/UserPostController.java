package br.silva.io.social.hub.rest.controller;


import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.silva.io.social.hub.security.Authorization;
import br.silva.io.social.hub.service.UserPostService;
import br.silva.io.social.hub.vo.UserPostVO;

@RequestScoped
@Path("/posts")
@Authorization
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserPostController{

	@Inject
	private UserPostService postService;
	
	@Inject
    private Logger log;
	
	@HeaderParam("Authorization")
	private String sessionKey;
	
	@GET
	public Response getLastPosts(@QueryParam("page") Integer page) {
		Response.ResponseBuilder builder = null;
		
		List<UserPostVO> lastPosts = postService.listLastPosts(page);
		builder = Response.ok(lastPosts);
		
		return builder.build();
	}
	
	@POST
	public Response createPost(UserPostVO post) {
		Response.ResponseBuilder builder = null;
		
		try {
			UserPostVO createdPost = postService.savePost(sessionKey, post.getTitle(), post.getDescription());
			
			builder = Response.ok(createdPost);
		} catch(Exception ex) {
			builder = Response.serverError();
		}
		
		return builder.build();
	}
	
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response updatePost(UserPostVO post, @PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;
		
		try {
			UserPostVO savedPost = postService.updatePost(id, post.getTitle(), post.getDescription());
			builder = Response.ok(savedPost);
		} catch (Exception ex) {
			builder = Response.status(404);
		}
		
		return builder.build();
	}
}
