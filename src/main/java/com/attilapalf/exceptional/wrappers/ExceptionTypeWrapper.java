package com.attilapalf.exceptional.wrappers;

import com.attilapalf.exceptional.entities.BeingVotedExceptionTypeEntity;
import com.attilapalf.exceptional.entities.ExceptionTypesEntity;

/**
 * Created by palfi on 2015-08-20.
 */
public class ExceptionTypeWrapper {
    private int id;
    private int version;
    private String shortName;
    private String prefix;
    private String description;
    private String type;
    private Submitter submitter;
    private int voteCount;

    public ExceptionTypeWrapper() {
    }

    public ExceptionTypeWrapper(int id, int version, String shortName, String prefix, String description,
                                String type, Submitter submitter, int voteCount) {
        this.id = id;
        this.version = version;
        this.shortName = shortName;
        this.prefix = prefix;
        this.description = description;
        this.type = type;
        this.submitter = submitter;
        this.voteCount = voteCount;
    }

    public ExceptionTypeWrapper(ExceptionTypesEntity exceptionTypesEntity) {
        id = exceptionTypesEntity.getId();
        version = exceptionTypesEntity.getVersion();
        shortName = exceptionTypesEntity.getShortName();
        prefix = exceptionTypesEntity.getPrefix();
        description = exceptionTypesEntity.getDescription();
        type = exceptionTypesEntity.getType();
        if (exceptionTypesEntity.getSubmittedBy() != null) {
            submitter = new Submitter(exceptionTypesEntity.getSubmittedBy().getFirstName(),
                    exceptionTypesEntity.getSubmittedBy().getLastName());
        }
    }

    public ExceptionTypeWrapper(BeingVotedExceptionTypeEntity exception) {
        id = exception.getId();
        version = exception.getVersion();
        shortName = exception.getShortName();
        prefix = exception.getPrefix();
        description = exception.getDescription();
        type = "SUBMITTED";
        submitter = new Submitter(exception.getSubmittedBy().getFirstName(),
                exception.getSubmittedBy().getLastName());
        voteCount = exception.getVotes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public class Submitter {
        private String firstName;
        private String lastName;

        public Submitter() {
        }

        public Submitter(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
