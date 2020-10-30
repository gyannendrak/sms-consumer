package com.ghf.sms.dao;

import com.ghf.sms.model.SMSInfo;
import com.ghf.sms.repository.SMSInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class NotificationDao {

    private SMSInfoRepository smsInfoRepository;

    @Autowired
    public NotificationDao(SMSInfoRepository smsInfoRepository){
        this.smsInfoRepository = smsInfoRepository;
    }

    public List<SMSInfo> findByMobileNo(String mobileNo, String message) {
        try {
            List<SMSInfo> smsInfoHistories = smsInfoRepository.findAllByMobileNoAndMessage(mobileNo, message);
            return smsInfoHistories;
        } catch (Exception e) {
            log.error("Mobile No = {} for SMS does not exist. " + e, mobileNo);
        }
        return new ArrayList<>();
    }

    public void save(SMSInfo smsInfo) {
        try {
            smsInfoRepository.save(smsInfo);
        } catch (Exception e) {
            log.error("Unable to save SMS into DB. " + e);
        }
    }

    public long count() {
        try {
            return smsInfoRepository.count();
        } catch (Exception e) {
            log.error("Unable to count SMS from DB. " + e);
        }
        return 0l;
    }
}
