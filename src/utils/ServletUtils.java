package utils;

import engine.Engine;
import engine.UserManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String ENGINE_ATTRIBUTE_NAME = "engine";


	public static UserManager getUserManager(ServletContext servletContext) {
		if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
			servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
		}
		return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
	}

	public static Engine getEngine(ServletContext servletContext) {
		if (servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME) == null) {
			servletContext.setAttribute(ENGINE_ATTRIBUTE_NAME, new Engine());
		}
		return (Engine) servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME);
	}
}
