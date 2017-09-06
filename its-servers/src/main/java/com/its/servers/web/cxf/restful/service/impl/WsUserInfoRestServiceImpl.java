package com.its.servers.web.cxf.restful.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.its.model.dao.domain.Role;
import com.its.servers.web.cxf.restful.entity.UserInfo;
import com.its.servers.web.cxf.restful.service.WsUserInfoRestService;

@Path(value = "/userRest")
public class WsUserInfoRestServiceImpl implements WsUserInfoRestService {

	@Context
	private UriInfo uriInfo;

	@Context
	private Request request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String doGet() {
		return "get request";
	}

	@GET
	@Path("/request/{param}")
	@Produces(MediaType.TEXT_PLAIN)
	public String doRequest(@PathParam("param") String param, @Context HttpServletRequest servletRequest,
			@Context HttpServletResponse servletResponse) {
		System.out.println(servletRequest);
		System.out.println(servletRequest.getAttribute("name"));
		System.out.println(servletRequest.getContentType());
		System.out.println(servletRequest.getCharacterEncoding());
		System.out.println(servletRequest.getContentType());
		System.out.println("Method:" + request.getMethod());
		System.out.println("uri:" + uriInfo.getPath());
		System.out.println(uriInfo.getPathParameters());
		return "success" + param;
	}

	@GET
	@Path("/getUser/{id}/{name}/{address}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UserInfo getUser(@PathParam("id") int id, @PathParam("name") String name,
			@PathParam("address") String address) {
		UserInfo user = buildUserInfo(id, name, address);
		return user;
	}

	@POST
	@Path("/postData")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UserInfo postData(UserInfo user) throws IOException {
		printUserInfo(user);
		return user;
	}

	@PUT
	@Path("/putData/{id}")
	@Produces({ MediaType.APPLICATION_XML })
	public UserInfo putData(@PathParam("id") int id, UserInfo user) {
		printUserInfo(user);
		return user;
	}

	@DELETE
	@Path("/removeData/{id}")
	public void deleteData(@PathParam("id") int id) {
		System.out.println("delete---" + id);
	}

	private UserInfo buildUserInfo(int id, String name, String address) {
		UserInfo user = new UserInfo();
		user.setId(id);
		user.setName(name);
		user.setAddress(address);
		List<Role> roles = new ArrayList<Role>();
		Map<String, Role> maps = new HashMap<String, Role>();
		for (int i = 0; i < 3; i++) {
			Role role = new Role();
			role.setId(Long.parseLong(i + ""));
			role.setName("role" + i);
			roles.add(role);
			maps.put("role" + i, role);
		}
		user.setRoles(roles);
		user.setMaps(maps);
		return user;
	}

	private void printUserInfo(UserInfo user) {
		System.out.println(user.getId() + "---" + user.getName() + "---" + user.getAddress());
		System.out.println("-----roles----");
		for (Role role : user.getRoles()) {
			System.out.println(role.getName());
		}
		System.out.println("-----maps----");
		for (Map.Entry<String, Role> map : user.getMaps().entrySet()) {
			System.out.println(map.getValue().getName());
		}
	}

}
