package com.its.servers.web.cxf.restful.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.its.servers.web.cxf.restful.entity.UserInfo;


@Path(value = "/userRest")
public interface WsUserInfoRestService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String doGet();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/request/{param}")
	public String doRequest(@PathParam("param") String param, @Context HttpServletRequest servletRequest,
			@Context HttpServletResponse servletResponse);

	@GET
	@Path("/getUser/{id}/{name}/{address}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UserInfo getUser(@PathParam("id") int id, @PathParam("name") String name,
			@PathParam("address") String address);

	@POST
	@Path("/postData")
	public UserInfo postData(UserInfo user) throws IOException;

	@PUT
	@Path("/putData/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public UserInfo putData(@PathParam("id") int id, UserInfo user);

	@DELETE
	@Path("/removeData/{id}")
	public void deleteData(@PathParam("id") int id);
}
