package com.shawn.sales.business.impl;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shawn.sales.business.IMailer;
import com.shawn.sales.business.dto.EmailDto;

@Service
public class MailerImpl implements IMailer {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TaskExecutor taskExecutor;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void sendMailBySyncMode(EmailDto email) throws Exception {
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage mime = new MimeMessage(session);
		MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
		helper.setFrom(email.getFrom());// 发件人
		helper.setTo(InternetAddress.parse(email.getAddressee()));// 收件人
		// helper.setBcc("administrator@chinaptp.com");//暗送
		// helper.setReplyTo("xiaofei.wang@radida.com");// 回复到
		helper.setSubject(email.getSubject());// 邮件主题
		helper.setText(email.getContent(), true);// true表示设定html格式
		mailSender.send(mime);
		logger.info("send Mail success, info : "+ JSONObject.fromObject(email).toString());
	}

	@Override
	public void sendMailByAsyncMode(final EmailDto email) throws Exception {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendMailBySyncMode(email);					
				} catch (Exception e) {
					logger.error("send Mail error MailInfo : " + JSONObject.fromObject(email).toString());
					logger.error("send Mail error exception : " + e.getMessage());
				}
			}
		});
	}
}
