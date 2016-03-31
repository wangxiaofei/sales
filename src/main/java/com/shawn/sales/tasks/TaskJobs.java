package com.shawn.sales.tasks;

import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.shawn.sales.business.IMailer;
import com.shawn.sales.business.dto.EmailDto;
import com.shawn.sales.business.model.Customer;
import com.shawn.sales.business.model.SaleRecord;
import com.shawn.sales.business.model.User;
import com.shawn.sales.persistance.CustomerDao;
import com.shawn.sales.persistance.SaleRecordDao;
import com.shawn.sales.persistance.UserDao;
import com.shawn.sales.utils.CustomizedPropertyPlaceholderConfigurer;

@Service
public class TaskJobs {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Logger monitor = LoggerFactory.getLogger("monitor");
	@Autowired
	private IMailer mail;
	@Autowired
	private SaleRecordDao saleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CustomerDao cusDao;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	 @Scheduled(cron = "0/5 * * * * *")
	void doSomethingWith() {
		// System.out.println("I'm doing with cron now!");
		// logger.debug("log-I'm doing with cron now!");
		 monitor.info("test");
		 
		 taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				monitor.info("task....");
				monitor.info("getActiveCount="+taskExecutor.getActiveCount());
				monitor.info("getMaxPoolSize="+taskExecutor.getMaxPoolSize());
				monitor.info("getPoolSize="+taskExecutor.getPoolSize());
				monitor.info("getCorePoolSize="+taskExecutor.getCorePoolSize());
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

//	@Scheduled(cron = "0 0 12 * * ?")
	void checkUpdateProduct() {
		logger.info("检查更新产品...");
		List<SaleRecord> saleList = saleDao.getNeedUpdateList();
		List<User> userList = userDao.getAllAdmin();
		if (saleList == null || saleList.isEmpty()) {
			return;
		}
		if (userList == null || userList.isEmpty()) {
			return;
		}

		String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		StringBuffer content = new StringBuffer();
		content.append("管理员，你好：<br>");
		content.append(tab + "有以下产品需要更新：<br>");
		for (SaleRecord s : saleList) {
			Customer cus = cusDao.getById(s.getCustomerId());

			content.append(tab + tab + "ID：" + s.getId());
			content.append("&nbsp;&nbsp;安装机型：" + s.getMachineModel());
			content.append("&nbsp;&nbsp;机身编号：" + s.getMachineNumber());
			content.append("&nbsp;&nbsp;客户姓名：" + cus.getUserName());
			content.append("&nbsp;&nbsp;客户地址：" + cus.getAddress());
			content.append("&nbsp;&nbsp;销售时间：" + DateFormatUtils.format(s.getSaleTime(), "yyyy-MM-dd"));

			content.append("<br>");
		}
		content.append(tab + "请及时处理需要更新的产品。");

		String from = (String) CustomizedPropertyPlaceholderConfigurer.getContextProperty("mail.from");
		for (User u : userList) {
			if (u.getEmail() != null) {
				EmailDto email = new EmailDto();
				email.setAddressee(u.getEmail());
				email.setContent(content.toString());
				email.setFrom(from);
				email.setSubject("有产品需要更新");
				try {
					mail.sendMailByAsyncMode(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
