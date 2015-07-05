package com.attilapalf.exceptional.entities;

import javax.persistence.*;

/**
 * Created by 212461305 on 2015.07.05..
 */
@Entity
@Table(name = "constants", schema = "", catalog = "exceptional")
public class ConstantsEntity {
    private int constantId;
    private String constantName;
    private long constantValueInt;
    private String constantValueString;

    @Id
    @Column(name = "constant_id")
    public int getConstantId() {
        return constantId;
    }

    public void setConstantId(int constantId) {
        this.constantId = constantId;
    }

    @Basic
    @Column(name = "constant_name", columnDefinition = "VARCHAR(45)")
    public String getConstantName() {
        return constantName;
    }

    public void setConstantName(String constantName) {
        this.constantName = constantName;
    }

    @Basic
    @Column(name = "constant_value_int", columnDefinition = "BIGINT(255)")
    public long getConstantValueInt() {
        return constantValueInt;
    }

    public void setConstantValueInt(long constantValueInt) {
        this.constantValueInt = constantValueInt;
    }

    @Basic
    @Column(name = "constant_value_string", columnDefinition = "VARCHAR(200)")
    public String getConstantValueString() {
        return constantValueString;
    }

    public void setConstantValueString(String constantValueString) {
        this.constantValueString = constantValueString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantsEntity that = (ConstantsEntity) o;

        if (constantId != that.constantId) return false;
        if (constantName != null ? !constantName.equals(that.constantName) : that.constantName != null) return false;
        if (constantValueInt != that.constantValueInt) return false;
        if (constantValueString != null ? !constantValueString.equals(that.constantValueString) : that.constantValueString != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = constantId;
        result = 31 * result + (constantName != null ? constantName.hashCode() : 0);
        result = 31 * result + (int) constantValueInt;
        result = 31 * result + (constantValueString != null ? constantValueString.hashCode() : 0);
        return result;
    }
}
