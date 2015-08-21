package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table(name = "exception_types", schema = "", catalog = "exceptional")
public class ExceptionTypesEntity {
    private int id;
    private String shortName;
    private String prefix;
    private String description;
    private int version;
    private String type;
    private UsersEntity submittedBy;
    private List<ExceptionInstancesEntity> instances;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "prefix")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Basic
    @Column(name = "type", columnDefinition = "ENUM('JAVA','.NET','SWIFT','SUBMITTED')")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExceptionTypesEntity that = (ExceptionTypesEntity) o;

        if (id != that.id) return false;
        if (version != that.version) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "submitted_by_fb_id", referencedColumnName = "facebook_id")
    public UsersEntity getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(UsersEntity submittedBy) {
        this.submittedBy = submittedBy;
    }

    @OneToMany(mappedBy = "type")
    public List<ExceptionInstancesEntity> getInstances() {
        return instances;
    }

    public void setInstances(List<ExceptionInstancesEntity> instances) {
        this.instances = instances;
    }
}
