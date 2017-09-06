package com.its.common.utils;

public class Constants {

	public class SESSION_KEY {
		/** 用户 */
		public static final String CURRENT_WEB_USER = "currentWebUser";
		/** 验证码 */
		public static final String VERIFY_CODE = "VerifyCode";
		/** 登录后的Session key */
		public static final String ITS_USER_SESSION = "ITS_USER_SESSION";
		/** 时区 */
		public static final String SYS_TIME_ZONE = "SYS_TIME_ZONE";
		/** 登录后的用户权限 Session key */
		public static final String ITS_USER_MENU_SESSION = "ITS_USER_MENU_SESSION";
		/** 登录后需要拦截的权限URL */
		public static final String ITS_INTERCEPTOR_USER_MENU_SESSION = "ITS_INTERCEPTOR_USER_MENU_SESSION";
	}

	public class MENU_TYPE {
		/** 菜单 */
		public static final String MENU = "M";
		/** 按钮 */
		public static final String BUTTON = "B";
	}

	/** 系统名称 */
	public final static String SYS_NAME_CODE = "IOP-MCS";
	/** 权限非法 */
	public final static String NO_PERMISSIONS = "NO_PERMISSIONS";

	/** 操作成功标志 */
	public final static String OPTION_FLAG_SUCCESS = "SUCCESS";
	/** 员工工号已存在 */
	public final static String IS_REPAEAT = "IS_REPEAT";
	/** 操作失败标志 */
	public final static String OPTION_FLAG_FAIL = "FAIL";

	public class COOKIE_KEY {
		public static final String SAVE_PASSWORD = "savePassword";
		public static final String AUTO_LOGIN = "autoLogin";
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
	}

	public class UPGRADE {
		/** 升级版本XML配置文件 */
		public static final String UPGRADE_XML = "upgrade.xml";
		/** 斜线 */
		public static final String SLASH = "\\";
		/** 反斜线 */
		public static final String ALTSLASH = "/";
	}

}
