package com.attilapalf.exceptional.businessLogic;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.repositories.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.U2ECrud;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.wrappers.ExceptionSentResponse;
import com.attilapalf.exceptional.wrappers.ExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

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

    @Transactional
    public ExceptionSentResponse sendException(ExceptionWrapper exceptionWrapper) {

        // storing the exception
        String shortName = exceptionCrud.saveNewException(exceptionWrapper);
        ExceptionSentResponse response = new ExceptionSentResponse(exceptionWrapper.getToWho(), shortName);

        // TODO: send push notif to recipient
        long toWho = exceptionWrapper.getToWho();

        return response;
    }
}
