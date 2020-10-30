package com.ghf.sms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghf.sms.dao.NotificationDao;
import com.ghf.sms.model.Notification;
import com.ghf.sms.model.SMSInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

import java.util.Date;

@Slf4j
public class Utilities {

    public static Notification mapNotification(Message message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new String(message.getBody()), Notification.class);
        } catch (Exception e) {
            log.error("Unable to map object with entity : {} ", e);
            return null;
        }
    }

    public static void mapSMSData(SMSInfo smsInfo, NotificationDao notificationDao){
        smsInfo.setCreatedDateTime(new Date());
        smsInfo.setUpdatedDateTime(new Date());
        long count = notificationDao.count();
        count += 1;
        smsInfo.setSmsId(count);
    }

    public static SMSInfo mapSMSInfo(Notification notification){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(notification, SMSInfo.class);
        } catch (Exception e) {
            log.error("Unable to map object with entity : {} ", e);
        }
        return null;
    }

    public static <T> String convertObjectToRequestString(T data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error("Unable to convert object to string : {} ", e);
        }
        return null;
    }
}
