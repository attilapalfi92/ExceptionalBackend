package com.attilapalf.exceptional.businessLogic;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.ConstantCrud;
import com.attilapalf.exceptional.repositories.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.U2ECrud;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.wrappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.RefreshFailedException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Attila on 2015-06-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ExceptionBusinessLogic {
    @Autowired
    private U2ECrud exceptionCrud;
    @Autowired
    private UserCrud userCrud;
    @Autowired
    private ExceptionTypeCrud exceptionTypeCrud;
    @Autowired
    private ConstantCrud constantCrud;

    RestTemplate restTemplate = new RestTemplate();

    private final String url = "https://android.googleapis.com/gcm/send";
    private final String apiKey = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String projectNumber = "947608408958";

    @Transactional
    public ExceptionSentResponse sendException(ExceptionWrapper exceptionWrapper) {

        // storing the exception
        Users2ExceptionsEntity exception = exceptionCrud.saveNewException(exceptionWrapper);
        ExceptionSentResponse response = new ExceptionSentResponse(exceptionWrapper.getToWho(),
                exception.getExceptionType().getShortName());

        // getting the regId of the recipient
        String regId = userCrud.findOne(exceptionWrapper.getToWho()).getGcmId();

        // TODO: send push notif to recipient
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + apiKey);

        ExceptionNotification exceptionNotification = new ExceptionNotification(regId, exception);

        HttpEntity<ExceptionNotification> requestData = new HttpEntity<>(exceptionNotification, headers);

        restTemplate = new RestTemplate();

        // posting the data to recipient with gcm
        String gcmResponse = restTemplate.postForObject(url, requestData, String.class);

        return response;
    }


    @Transactional
    public ExceptionRefreshResponse refreshExceptions(BaseRequestBody requestBody) {
        UsersEntity user = userCrud.findOne(requestBody.getUserId());

        List<Users2ExceptionsEntity> exceptions = exceptionCrud.findExceptionsNotAmongIds(
                user.getUserDbId(),
                requestBody.getExceptionIds(),
                constantCrud.findMaxExceptionsPerUser()
        );

        return new ExceptionRefreshResponse(exceptions);
    }
}
