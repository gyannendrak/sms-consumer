package com.ghf.sms.process;

import com.ghf.sms.dao.NotificationDao;
import com.ghf.sms.model.Notification;
import com.ghf.sms.model.SMSInfo;
import com.ghf.sms.service.SMSProcessService;
import com.ghf.sms.service.SMSTemplate;
import com.ghf.sms.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SMSDataProcessor {

    @Autowired
    private SMSProcessService smsProcessService;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private SMSTemplate smsTemplate;

    public void sendSMS(Notification notification) {
        try {
            SMSInfo smsInfo = Utilities.mapSMSInfo(notification);
            Utilities.mapSMSData(smsInfo, notificationDao);
            notificationDao.save(smsInfo);
            log.info("Sending....");
            smsTemplate.sendSMS(notification.getMessage(), notification.getMobileNo());
        } catch (Exception e) {
            log.error("Unable to send message on this mobile no ({}) and message : {}, exception: {}", notification.getMobileNo(), notification.getMessage(), e);
        }
    }
}
