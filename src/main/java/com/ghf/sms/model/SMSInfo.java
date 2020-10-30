package com.ghf.sms.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@Entity
@Table(name = "sms")
public class SMSInfo {
    @Id
    @Column(name = "sms_id")
    private Long smsId;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date_time")
    private Date createdDateTime;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date_time")
    private Date updatedDateTime;
}
