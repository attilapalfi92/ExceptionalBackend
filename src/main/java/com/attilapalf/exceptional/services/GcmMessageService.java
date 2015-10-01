package com.attilapalf.exceptional.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.attilapalf.exceptional.entities.DevicesEntity;
import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.messages.notifications.ExceptionNotification;
import com.attilapalf.exceptional.messages.notifications.FriendNotification;

/**
 * Created by palfi on 2015-08-29.
 */
@Service
public class GcmMessageService {
    private final String URL = "https://android.googleapis.com/gcm/send";
    private final String API_KEY = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String PROJECT_NUMBER = "947608408958";

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders httpHeaders;

    @PostConstruct
    public void initHttpHeaders( ) {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType( MediaType.APPLICATION_JSON );
        httpHeaders.set( "Authorization", "key=" + API_KEY );
    }

    public void sendFriendNotification( UsersEntity newUser, List<UsersEntity> friends ) {
        friends.forEach( friend -> pushNewFriendNotification( friend, newUser ) );
    }

    public void sendExceptionNotification( UsersEntity receiver, UsersEntity sender, ExceptionInstancesEntity exception ) {
        List<String> receiverGcmIds = getGcmIds( receiver );
        ExceptionNotification notification = new ExceptionNotification( receiverGcmIds, exception, receiver.getPoints(),
                sender.getPoints() );
        HttpEntity<ExceptionNotification> gcmRequestData = new HttpEntity<>( notification, httpHeaders );
        try {
            String gcmResponse = restTemplate.postForObject( URL, gcmRequestData, String.class );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void pushNewFriendNotification( UsersEntity receiver, UsersEntity newUser ) {
        List<String> receiverGcmIds = getGcmIds( receiver );
        FriendNotification notification = new FriendNotification(
                newUser.getFacebookId(),
                newUser.getFirstName().trim() + " " + newUser.getLastName().trim(),
                receiverGcmIds );
        HttpEntity<FriendNotification> gcmRequestData = new HttpEntity<>( notification, httpHeaders );
        try {
            String gcmResponse = restTemplate.postForObject( URL, gcmRequestData, String.class );
        } catch ( RestClientException e ) {
            e.printStackTrace();
        }
    }

    private List<String> getGcmIds( UsersEntity receiver ) {
        return receiver.getDevices().stream().map( DevicesEntity::getGcmId ).collect( Collectors.toList() );
    }
}
