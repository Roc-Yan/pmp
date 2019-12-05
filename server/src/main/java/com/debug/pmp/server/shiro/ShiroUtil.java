package com.debug.pmp.server.shiro;

import com.debug.pmp.common.exception.CommonException;
import com.debug.pmp.model.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

//Shiro工具类
public class ShiroUtil {

	//加密算法
	public final static String hashAlgorithmName = "SHA-256";

	//循环次数
	public final static int hashIterations = 16;

	public static String sha256(String password, String salt) {
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}

	//获取Shiro Session
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	//获取Shiro Subject
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	//获取Shiro中的真正主体
	public static SysUser getUser() {
		return (SysUser)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUser().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * 获取验证码
	 * @param key
	 * @return
	 */
	public static String getKaptcha(String key) {
		Object object=getSessionAttribute(key);
		if (object==null){
			throw new CommonException("验证码已失效!");
		}
		String newCode=object.toString();
		getSession().removeAttribute(key);

		return newCode;
	}

	public static void main(String[] args) {
		String password="123456";
		System.out.println(ShiroUtil.sha256(password, "q8WwxUma2bqO4voBMNK4"));
	}

}