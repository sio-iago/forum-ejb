package br.silva.io.social.hub.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;


import br.silva.io.social.hub.model.User;
import br.silva.io.social.hub.security.exception.AuthorizationException;
import br.silva.io.social.hub.service.AuthService;

@Provider
@Authorization
public class AuthorizationFilter implements ContainerRequestFilter {

	public static String AUTHORIZATION = "Authorization";
	
	@Inject
	protected AuthService authService;
	
	@Inject
    private Logger log;
	
	public AuthorizationFilter() {
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		UriInfo uriInfo = requestContext.getUriInfo();
		List<PathSegment> segments = uriInfo.getPathSegments();
		
		log.info("["+requestContext.getMethod()+"] Secured route accessed "+segments.toString());
		
		Map<String, List<String>> headers = requestContext.getHeaders();
		
		List<String> sessionKeyHeader = headers.get(AUTHORIZATION);
		String sessionKey = (sessionKeyHeader != null && sessionKeyHeader.size()>0 ? sessionKeyHeader.get(0) : null);
		
		User loggedUser = authService.getUserBySessionKey(sessionKey);
		
		if(!(loggedUser instanceof User)) {
			log.warning("Access to sessionKey denied! ${"+sessionKey+"}");
			requestContext.abortWith(Response.serverError().build());
		}
		
		requestContext.setProperty("br.silva.io.social.hub.security.loggedUser", loggedUser);
	}
	
}
