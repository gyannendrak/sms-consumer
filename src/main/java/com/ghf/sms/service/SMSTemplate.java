package com.ghf.sms.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.Arrays;

@Slf4j
@Component
public class SMSTemplate implements EnvironmentAware {

    private static final String URL = "spring.interface.sms.url";
    private static final String API_KEY = "spring.interface.sms.app.id";
    private static final String API_PASSWORD = "spring.interface.sms.password";
    private static final String SENDER_ID = "spring.interface.sms.from.id";
    private static final String MESSAGE_TYPE = "spring.interface.message.type";

    @Autowired
    private RestTemplate restTemplate;
    private Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }


    public void sendSMS(final String message, final String mobile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        StringBuilder url = new StringBuilder().append(this.environment.getProperty(URL));

        String encoded_message = URLEncoder.encode(message);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url.toString())
                .queryParam("api_key", this.environment.getProperty(API_KEY))
                .queryParam("pass", this.environment.getProperty(API_PASSWORD))
                .queryParam("senderid", this.environment.getProperty(SENDER_ID))
                .queryParam("dest_mobileno", mobile)
                .queryParam("message", encoded_message)
                .queryParam("mtype", this.environment.getProperty(MESSAGE_TYPE));

        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(builder.toUriString(), httpEntity, Void.class);
            int statusCode = response.getStatusCode().value();
            if (200 <= statusCode && statusCode < 300) {
                log.debug("SMS sent successfully on this mobile no ({}) and message : {} ", mobile, message);
            }
        } catch (Exception ex) {
            log.error(StringUtils.join("Error Occurred while sending SMS to ", mobile));
        }
    }
}
