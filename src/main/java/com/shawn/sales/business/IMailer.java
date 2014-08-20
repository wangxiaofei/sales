package com.shawn.sales.business;

import com.shawn.sales.business.dto.EmailDto;

/**
 * 邮件发送接口
 * @author shawn
 *
 */
public interface IMailer {

	void sendMailBySyncMode(EmailDto email) throws Exception;
	
	void sendMailByAsyncMode(EmailDto email) throws Exception;
}
