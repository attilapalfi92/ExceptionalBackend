package com.attilapalf.exceptional.services;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Attila on 2015-06-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ExceptionService {
    @Autowired
    private ExceptionInstanceCrud exceptionCrud;
    @Autowired
    private UserCrud userCrud;
    @Autowired
    private ExceptionTypeCrud exceptionTypeCrud;
    @Autowired
    private ConstantCrud constantCrud;
    @Autowired
    private GcmMessageService gcmMessageService;


    @Transactional
    public ExceptionSentResponse throwException(ExceptionInstanceWrapper exceptionInstanceWrapper) {
        UsersEntity sender = userCrud.findOne(exceptionInstanceWrapper.getFromWho());
        UsersEntity receiver = userCrud.findOne(exceptionInstanceWrapper.getToWho());
        updateUsersPoints(sender, receiver);
        ExceptionInstancesEntity exception = exceptionCrud.saveNewException(exceptionInstanceWrapper);
        exceptionInstanceWrapper.setInstanceId(exception.getId());
        gcmMessageService.sendExceptionNotification(receiver, sender, exception);
        return createExceptionSentResponse(sender, receiver, exceptionInstanceWrapper, exception);
    }

    private void updateUsersPoints(UsersEntity sender, UsersEntity receiver) {
        sender.setPoints(sender.getPoints() + 25);
        receiver.setPoints(receiver.getPoints() - 20);
        userCrud.save(sender);
        userCrud.save(receiver);
    }

    private ExceptionSentResponse createExceptionSentResponse(UsersEntity sender, UsersEntity receiver,
                                                              ExceptionInstanceWrapper instanceWrapper,
                                                              ExceptionInstancesEntity exception) {
        return new ExceptionSentResponse(
                instanceWrapper,
                exception.getType().getShortName(),
                sender.getPoints(),
                receiver.getPoints());
    }

    @Transactional
    public ExceptionRefreshResponse refreshExceptions(BaseExceptionRequest requestBody) {
        UsersEntity user = userCrud.findOne(requestBody.getUserFacebookId());
        List<ExceptionInstancesEntity> exceptions = exceptionCrud.findLastExceptionsNotAmongIds(
                user,
                requestBody.getKnownExceptionIds()
        );
        return new ExceptionRefreshResponse(exceptions);
    }
}
