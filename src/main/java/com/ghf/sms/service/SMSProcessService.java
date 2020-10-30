package com.ghf.sms.service;

import com.ghf.sms.model.SMSData;
import com.ghf.sms.model.Notification;
import com.ghf.sms.process.SMSDataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SMSProcessService {

    @Autowired
    private KieSession session;
    @Autowired
    private SMSDataProcessor SMSDataProcessor;

    public void sendSMS(SMSData smsData, Notification notification) {
        session.insert(smsData);
        session.fireAllRules();
        try{
            if(smsData.isStop()){
                SMSDataProcessor.sendSMS(notification);
            }else{
                log.info("SMS with same content could not be send on same mobile no.");
            }
        }catch (Exception e){
            log.error("Exception occurred while sending SMS to person.", e);
        }
    }
}
