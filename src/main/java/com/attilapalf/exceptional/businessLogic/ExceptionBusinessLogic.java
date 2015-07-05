package com.attilapalf.exceptional.businessLogic;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.repositories.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.U2ECrud;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.wrappers.ExceptionNotification;
import com.attilapalf.exceptional.wrappers.ExceptionSentResponse;
import com.attilapalf.exceptional.wrappers.ExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

/**
 * Created by Attila on 2015-06-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ExceptionBusinessLogic {
    @Autowired
    U2ECrud exceptionCrud;
    @Autowired
    UserCrud userCrud;
    @Autowired
    ExceptionTypeCrud exceptionTypeCrud;

    RestTemplate restTemplate = new RestTemplate();

    private final String url = "https://android.googleapis.com/gcm/send";
    private final String apiKey = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String projectNumber = "947608408958";

    private final String marciRegId = "APA91bF2OrXis0QjTVffkhyb5SgZXY3-69XrvVx382fWFi77wDP8dxo" +
            "J6UiOWK0fYKVtz8OiEFUb0XtmLtorslMuQz2UWrWunjqa5lYTLrSJ_dzV6QiaI2TLCFPyGkcb9gAm9rVv3yho";

    private final String myRegId = "APA91bFNN4ofjf76nIc2uDuBif5HWXkmMdSzqBWwyK8E0HNBBGIpMEXZ96" +
            "ANwyKai__68l5Yt5AwAqP8sqeE-actVzGqf6wCcvPy6DYg8D77Wb45sDo6I2N8x_4F2MF7SnkGEEP13JFO";

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


        long toWho = exceptionWrapper.getToWho();

        return response;
    }
}
