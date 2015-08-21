package com.attilapalf.exceptional.logic;

import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.wrappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Attila on 2015-06-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ExceptionLogic {
    @Autowired
    private ExceptionInstanceCrud exceptionCrud;
    @Autowired
    private UserCrud userCrud;
    @Autowired
    private ExceptionTypeCrud exceptionTypeCrud;
    @Autowired
    private ConstantCrud constantCrud;

    private final String url = "https://android.googleapis.com/gcm/send";
    private final String apiKey = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String projectNumber = "947608408958";

    @Transactional
    public ExceptionSentResponse sendException(ExceptionInstanceWrapper exceptionInstanceWrapper) {

//        // storing the exception
//        ExceptionInstancesEntity exception = exceptionCrud.saveNewException(exceptionInstanceWrapper);
//        ExceptionSentResponse response = new ExceptionSentResponse(exceptionInstanceWrapper.getToWho(),
//                exception.getType().getShortName());
//
//        // getting the regId of the recipient
//        String regId = userCrud.findOne(exceptionInstanceWrapper.getToWho()).getGcmId();
//
//        // TODO: send push notif to recipient
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "key=" + apiKey);
//
//        ExceptionNotification exceptionNotification = new ExceptionNotification(regId, exception);
//
//        HttpEntity<ExceptionNotification> requestData = new HttpEntity<>(exceptionNotification, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        // posting the data to recipient with gcm
//        String gcmResponse = restTemplate.postForObject(url, requestData, String.class);
//
//        return response;
        return new ExceptionSentResponse();
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
