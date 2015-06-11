package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

/**
 * Created by Attila on 2015-06-11.
 */
@Entity
@Table(name = "exceptions", schema = "", catalog = "exceptional")
public class ExceptionsEntity {
    private int typeId;
    private String shortName;
    private String prefix;
    private String description;
    private Collection<Users2ExceptionsEntity> users2ExceptionsesByTypeId;

    @Id
    @Column(name = "type_id", columnDefinition = "INT(10)", nullable = false, insertable = true, updatable = true)
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "short_name", nullable = false, insertable = true, updatable = true, length = 200)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "prefix", nullable = false, insertable = true, updatable = true, length = 500)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "description", nullable = false, insertable = true, updatable = true, length = 3000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExceptionsEntity that = (ExceptionsEntity) o;

        if (typeId == that.typeId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "exception")
    public Collection<Users2ExceptionsEntity> getUsers2ExceptionsesByTypeId() {
        return users2ExceptionsesByTypeId;
    }

    public void setUsers2ExceptionsesByTypeId(Collection<Users2ExceptionsEntity> users2ExceptionsesByTypeId) {
        this.users2ExceptionsesByTypeId = users2ExceptionsesByTypeId;
    }
}
