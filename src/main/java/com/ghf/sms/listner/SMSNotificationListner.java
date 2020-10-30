package com.ghf.sms.listner;

import com.ghf.sms.dao.NotificationDao;
import com.ghf.sms.exception.GHFException;
import com.ghf.sms.model.SMSData;
import com.ghf.sms.model.Notification;
import com.ghf.sms.model.SMSInfo;
import com.ghf.sms.repository.SMSInfoRepository;
import com.ghf.sms.service.SMSProcessService;
import com.ghf.sms.util.Constants;
import com.ghf.sms.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import java.util.List;


@Slf4j
@Component
public class SMSNotificationListner {

	@Autowired
	private SMSProcessService smsProcessService;
	@Autowired
	private SMSInfoRepository smsInfoRepository;

	@RabbitListener(queues = "${sms.rabbitmq.queue}")
	@Retryable(maxAttempts=4, value= GHFException.class)
	public void onMessage(Message message) {
		try {
			Notification notification = Utilities.mapNotification(message);
			log.debug("SMS notification is mapped with message data : {} ", Utilities.convertObjectToRequestString(notification));
			SMSData smsData = new SMSData();
			NotificationDao notificationDao = new NotificationDao(smsInfoRepository);
			List<SMSInfo> smsInfoList = notificationDao.findByMobileNo(notification.getMobileNo(), notification.getMessage());
			Integer size = smsInfoList.size();
			smsData.setSize(size);
			smsProcessService.sendSMS(smsData, notification);
		} catch (Exception e) {
			log.error("Failed to process data sending request: {}, exception: {}", message, e);
			throw new GHFException(String.format("%s%s%s", Constants.FAILED_PROCESS, Constants.FAILED_REQUEST_DATA, e.getMessage()));
		}
	}
}