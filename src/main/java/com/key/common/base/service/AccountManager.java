package com.key.common.base.service;

import java.util.List;

import com.key.common.base.entity.User;
import com.key.common.plugs.page.PageRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.base.dao.UserDao;
import com.key.common.exception.ServiceException;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.plugs.security.ShiroDbRealm;
import com.key.common.utils.security.DigestUtils;

/**
 * 
 * @author KeYuan
 * @date 2013下午10:22:04
 *
 */
@Service
public class AccountManager {
	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	@Autowired
	private UserDao userDao;
	

//	@Autowired
//	private NotifyMessageProducer notifyMessageProducer;//JMS消息推送
	
	private ShiroDbRealm shiroRealm;

	/**
	 *When the user is saved, a user modification notification message is sent, and the message receiver asynchronously sends the time-consuming notification email.
	 * 
	 * If you attempt to modify the superuser, remove the current operator user, print its information and throw an exception.
	 * 
	 */
	// 演示指定非默认名称的TransactionManager.
	@Transactional
	public void saveUser(User user) {
		if (isSupervisor(user)) {
			logger.warn("Operator {} attempts to modify super administrator user", SecurityUtils.getSubject()
					.getPrincipal());
			throw new ServiceException("Cannot modify super administrator user");
		}
		//Determine whether there are duplicate users
		String shaPassword = DigestUtils.sha1Hex(user.getPlainPassword());
//		System.out.println(shaPassword+":"+user.getPlainPassword());
		user.setShaPassword(shaPassword);
		boolean bool=user.getId()==null?true:false;
		userDao.save(user);
		if (shiroRealm != null) {
			shiroRealm.clearCachedAuthorizationInfo(user.getLoginName());
		}
		/*if(bool){
//			Email email=new Email();
//			sendNotifyMessage(email);	使用jms辅助 发送邮件
			System.out.println("新注册，发激活账号邮件");
			simpleMailService.sendRegisterMailByAsync(user);
		}*/
	}

	@Transactional
	public void saveUp(User user){
		if (isSupervisor(user)) {
			logger.warn("Operator {} attempts to modify super administrator user", SecurityUtils.getSubject().getPrincipal());
			throw new ServiceException("Cannot modify super administrator user");
		}
		userDao.save(user);
	}

	@Transactional
	public void updatePwd(String curpwd, String newPwd) {
		User user = getCurUser();
		if(user!=null){
			//Determine whether there are duplicate users
			String curShaPassword = DigestUtils.sha1Hex(curpwd);
			if(user.getShaPassword().equals(curShaPassword)){
				String shaPassword = DigestUtils.sha1Hex(newPwd);
				user.setShaPassword(shaPassword);
				userDao.save(user);
			}
		}
	}
	/*public User getByUid(String userSource,String uid){
		Criterion cri1=Restrictions.eq("thirdSource", userSource);
		Criterion cri2=Restrictions.eq("thirdUid", uid);
		return userDao.findUnique(cri1,cri2);
	}*/
	//新注册用户，注册后
//	private void sendNotifyMessage(Email email) {
//		notifyMessageProducer.sendQueue(email);
//	}

	/**
	 * 分页查询剧本，得到用户列表  <--> Pagination query script to get user list  replace with jparepository native pagination
	 * @param page
	 * @param filters
	 * @return
	 */
	public Page<User> find(Page<User> page,List<PropertyFilter> filters){
		return userDao.findPage(page, filters);
	}

	/**
	 * 
	 */
	public Page<User> listUser(final PageRequest page, final List<PropertyFilter> filters){
		Page<User> pageUser=userDao.findPage(page,filters);
		return pageUser;
	}
	
	/**
	 * Determine if you are a super administrator.
	 */
	private boolean isSupervisor(User user) {
//		return (user.getId() != null && user.getId() == 1L);
		return false;
	}

	@Transactional(readOnly = true)
	public User getUser(String id) {
		return userDao.get(id);
	}
	
	/**
	 * Use attribute filters to query.
	 */
	@Transactional(readOnly = true)
	public Page<User> searchUser(final PageRequest page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	
	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}
	
	@Transactional(readOnly = true)
	public User findUserByLoginNameOrEmail(String loginName) {
		if(loginName!=null){
			if(loginName.contains("@")){
				//是邮箱账号
				return userDao.findUniqueBy("email", loginName);
			}else{
				return userDao.findUniqueBy("loginName", loginName);
			}
		}
		return null;
	}
	
	/*Verify the existence of the mailbox*/
	@Transactional(readOnly = true)
	public User findUserByEmail(String email){
		List<User> users=userDao.findBy("email", email);
		if(users!=null && users.size()>0){
			return users.get(0);
		}
		return null;
	}

	/**
	 * Check if the username is unique.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}
	
	/**
	 * 取出当前登陆用户  current logged in user ???
	 */
	public User getCurUser(){
		Subject subject=SecurityUtils.getSubject();
		
		if(subject!=null){
			Object principal=subject.getPrincipal();
			if(principal!=null){
				User user = findUserByLoginName(principal.toString());
				return user;
			}
		}
		return null;
	}


}
