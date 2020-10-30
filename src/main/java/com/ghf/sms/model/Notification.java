package com.ghf.sms.model;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mobileNo;
    private String message;
}