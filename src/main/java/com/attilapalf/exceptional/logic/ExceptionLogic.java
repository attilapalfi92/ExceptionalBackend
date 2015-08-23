package com.attilapalf.exceptional.logic;

import com.attilapalf.exceptional.entities.DevicesEntity;
import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.wrappers.*;
import com.attilapalf.exceptional.wrappers.notifications.ExceptionNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Attila on 2015-06-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ExceptionLogic {
    private final String URL = "https://android.googleapis.com/gcm/send";
    private final String API_KEY = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String PROJECT_NUMBER = "947608408958";

    @Autowired
    private ExceptionInstanceCrud exceptionCrud;
    @Autowired
    private UserCrud userCrud;
    @Autowired
    private ExceptionTypeCrud exceptionTypeCrud;
    @Autowired
    private ConstantCrud constantCrud;

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders httpHeaders;

    @PostConstruct
    public void initHttpHeaders() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "key=" + API_KEY);
    }

    @Transactional
    public ExceptionSentResponse throwException(ExceptionInstanceWrapper exceptionInstanceWrapper) {
        UsersEntity sender = userCrud.findOne(exceptionInstanceWrapper.getFromWho());
        UsersEntity receiver = userCrud.findOne(exceptionInstanceWrapper.getToWho());
        updateUsersPoints(sender, receiver);
        ExceptionInstancesEntity exception = exceptionCrud.saveNewException(exceptionInstanceWrapper);
        sendPushNotification(receiver, exception);
        return createExceptionSentResponse(exceptionInstanceWrapper, sender, exception);
    }

    private void updateUsersPoints(UsersEntity sender, UsersEntity receiver) {
        sender.setPoints(sender.getPoints() + 20);
        receiver.setPoints(receiver.getPoints() - 25);
        userCrud.save(sender);
        userCrud.save(receiver);
    }

    private void sendPushNotification(UsersEntity receiver, ExceptionInstancesEntity exception) {
        List<String> receiverGcmIds = receiver.getDevices().stream().map(DevicesEntity::getGcmId).collect(Collectors.toList());
        ExceptionNotification notification = new ExceptionNotification(receiverGcmIds, exception, receiver.getPoints());
        HttpEntity<ExceptionNotification> gcmRequestData = new HttpEntity<>(notification, httpHeaders);
        String gcmResponse = restTemplate.postForObject(URL, gcmRequestData, String.class);
    }

    private ExceptionSentResponse createExceptionSentResponse(ExceptionInstanceWrapper exceptionInstanceWrapper, UsersEntity sender, ExceptionInstancesEntity exception) {
        return new ExceptionSentResponse(
                exceptionInstanceWrapper.getToWho(),
                exception.getType().getShortName(),
                sender.getPoints());
    }

    @Transactional
    public ExceptionRefreshResponse refreshExceptions(BaseExceptionRequestBody requestBody) {
//        UsersEntity user = userCrud.findOne(requestBody.getUserFacebookId());
//
//        List<ExceptionInstancesEntity> exceptions = exceptionCrud.findLastExceptionsNotAmongIds(
//                user.getDatabaseId(),
//                requestBody.getKnownExceptionIds(),
//                constantCrud.getMaxExceptionsPerUser()
//        );
//
//        return new ExceptionRefreshResponse(exceptions);
        return new ExceptionRefreshResponse();
    }
}
