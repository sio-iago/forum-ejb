package br.silva.io.social.hub.rest.controller;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.security.Authorization;
import br.silva.io.social.hub.service.AuthService;
import br.silva.io.social.hub.service.PostCommentService;
import br.silva.io.social.hub.vo.PostCommentVO;
import br.silva.io.social.hub.vo.UserVO;

@Path("/comments")
@RequestScoped
@Authorization
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostCommentController {

	@Inject
	private PostCommentService commentService;
	
	@Inject
    private Logger log;
	
	@HeaderParam("Authorization")
	private String sessionKey;
	
	@POST
	@Path("/{postId:[0-9][0-9]*}")
	public Response createComment(@PathParam("postId") Long postId, PostCommentVO comment) {
		Response.ResponseBuilder builder = null;
		
		try {
			PostCommentVO vo = commentService.createComment(sessionKey, postId, comment.getMessage());
			builder = Response.ok(vo);
		} catch (Exception ex) {
			builder = Response.serverError();
		}
		
		return builder.build();
	}
}
