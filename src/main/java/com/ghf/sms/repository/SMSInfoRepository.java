package com.ghf.sms.repository;

import com.ghf.sms.model.SMSInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SMSInfoRepository extends JpaRepository<SMSInfo, Integer> {
    @Query(value = "select u from SMSInfo u where u.mobileNo=:mobileNo and u.message=:message")
    List<SMSInfo> findAllByMobileNoAndMessage(@Param("mobileNo") String mobileNo, @Param("message") String message);
}
