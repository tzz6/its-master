package com.its.web.controller.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.common.crypto.simple.MD5SHACryptoUtil;
import com.its.common.utils.Constants;
import com.its.model.mybatis.dao.domain.SysMenu;
import com.its.model.mybatis.dao.domain.SysUser;
import com.its.servers.facade.dubbo.sys.service.SysUserFacade;
import com.its.web.model.MenuBean;
import com.its.web.util.CookieUtil;
import com.its.web.util.UserSession;

/**
 * 登录
 *
 */
@Controller
public class LoginController extends BaseController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private SysUserFacade sysUserFacade;

	/**
	 * 登录页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String savePassword = CookieUtil.getCookie(request, Constants.COOKIE_KEY.SAVE_PASSWORD);
		String autoLogin = CookieUtil.getCookie(request, Constants.COOKIE_KEY.AUTO_LOGIN);
		if (autoLogin != null && "1".equals(autoLogin)) {
			model.put("autoLogin", autoLogin);
		}
		if (savePassword != null && "1".equals(savePassword)) {
			String username = CookieUtil.getCookie(request, Constants.COOKIE_KEY.USERNAME);
			String password = CookieUtil.getCookie(request, Constants.COOKIE_KEY.PASSWORD);
			model.put("savePassword", savePassword);
			model.put("username", username);
			model.put("password", password);
		}
		logger.info("to login page");
		return "login";
	}

	/** 登录 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void verifyLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam(value = "verifyCode", required = false) String verifyCode,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "savePassword", required = false) String savePassword,
			@RequestParam(value = "autologin", required = false) String autologin, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String loginUrl = "/index";

			String sessVerifyCode = (String) request.getSession().getAttribute(Constants.SESSION_KEY.VERIFY_CODE);
			if (verifyCode != null && sessVerifyCode.equals(verifyCode.toUpperCase())) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("stCode", username);
				map.put("stPassword", MD5SHACryptoUtil.sha512Encrypt(password));
				SysUser sysUser = sysUserFacade.login(map);
				if (sysUser != null) {
					sysUser.setLanguage(lang);
					UserSession.setUser(sysUser);
					if (StringUtils.isNotBlank(savePassword)) {
						CookieUtil.addCookie(response, Constants.COOKIE_KEY.SAVE_PASSWORD, savePassword);
						CookieUtil.addCookie(response, Constants.COOKIE_KEY.USERNAME, username);
						CookieUtil.addCookie(response, Constants.COOKIE_KEY.PASSWORD, password);

						if (StringUtils.isNotBlank(autologin)) {
							CookieUtil.addCookie(response, Constants.COOKIE_KEY.AUTO_LOGIN, autologin);
						} else {
							CookieUtil.removeCookie(response, Constants.COOKIE_KEY.AUTO_LOGIN);
						}
					} else {
						CookieUtil.removeCookie(response, Constants.COOKIE_KEY.SAVE_PASSWORD);
						CookieUtil.removeCookie(response, Constants.COOKIE_KEY.USERNAME);
						CookieUtil.removeCookie(response, Constants.COOKIE_KEY.PASSWORD);
						CookieUtil.removeCookie(response, Constants.COOKIE_KEY.AUTO_LOGIN);
					}
					writeJSON("success", loginUrl, response);

				} else {
					writeJSON("userError", loginUrl, response);
				}
			} else {
				writeJSON("verifyCodeError", loginUrl, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		logger.info("logout");
		UserSession.removeUser();
		return "logout";
	}

	/**
	 * 首页
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws JsonProcessingException {
		logger.info("to index");
		String timezone = UserSession.getTimezone();
		logger.info("timezone:" + timezone);
		String menuJson = getMenu();
		modelMap.put("menuJson", menuJson);
		return "main";
	}

	/**
	 * 获取菜单
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getMenu() throws JsonProcessingException {
		List<SysMenu> userMenus = UserSession.getSysMenu();
		List<MenuBean> menuBeans = new ArrayList<MenuBean>();
		if (userMenus != null) {
			List<SysMenu> firstMenus = new ArrayList<SysMenu>();
			for (SysMenu sysMenu : userMenus) {
				if (null == sysMenu.getParentMenuId() && sysMenu.getMenuType() != null
						&& Constants.MENU_TYPE.MENU.equals(sysMenu.getMenuType())) {
					firstMenus.add(sysMenu);
				}
			}
			for (SysMenu firstMenu : firstMenus) {
				MenuBean menuBean = new MenuBean();
				String menuId = firstMenu.getMenuId().toString();
				menuBean.setIcon("icon-sys");
				menuBean.setMiHierarchicalstructure(menuId);
				menuBean.setUrl(firstMenu.getMenuUrl());
				menuBean.setMiParameter(menuId);
				menuBean.setMenuId(firstMenu.getMenuId());
				menuBean.setMenuname(firstMenu.getMenuName());

				List<SysMenu> twomenus = new ArrayList<SysMenu>();
				for (SysMenu menu : userMenus) {
					String parentMenuId = menu.getParentMenuId();
					if (parentMenuId != null && parentMenuId.equals(firstMenu.getMenuId()) && menu.getMenuType() != null
							&& Constants.MENU_TYPE.MENU.equals(menu.getMenuType())) {
						twomenus.add(menu);
					}
				}

				List<MenuBean> twoMenuBeans = new ArrayList<MenuBean>();
				for (SysMenu twoMenu : twomenus) {
					MenuBean twoMenuBean = new MenuBean();
					String twoMenuId = twoMenu.getMenuId();
					twoMenuBean.setIcon("icon-nav");
					twoMenuBean.setMiHierarchicalstructure(twoMenuId);
					twoMenuBean.setUrl(twoMenu.getMenuUrl());
					twoMenuBean.setMiParameter(menuBean.getMiHierarchicalstructure());
					twoMenuBean.setMenuId(twoMenuId);
					twoMenuBean.setMenuname(twoMenu.getMenuName());
					twoMenuBeans.add(twoMenuBean);
				}
				menuBean.setMenus(twoMenuBeans);
				menuBeans.add(menuBean);
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		String menuJson = objectMapper.writeValueAsString(menuBeans);
		return menuJson;
	}

}
