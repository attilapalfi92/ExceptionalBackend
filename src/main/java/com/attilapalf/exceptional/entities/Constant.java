package com.attilapalf.exceptional.entities;

import java.math.BigInteger;

import javax.persistence.*;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table( name = "constants", schema = "", catalog = "exceptional" )
public class Constant {
    private int id;
    private String constantName;
    private BigInteger intValue;
    private String stringValue;

    @Id
    @GeneratedValue
    @Column( name = "id" )
    public int getId( ) {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    @Basic
    @Column( name = "constant_name" )
    public String getConstantName( ) {
        return constantName;
    }

    public void setConstantName( String constantName ) {
        this.constantName = constantName;
    }

    @Basic
    @Column( name = "int_value", columnDefinition = "BIGINT(255)" )
    public BigInteger getIntValue( ) {
        return intValue;
    }

    public void setIntValue( BigInteger intValue ) {
        this.intValue = intValue;
    }

    @Basic
    @Column( name = "string_value" )
    public String getStringValue( ) {
        return stringValue;
    }

    public void setStringValue( String stringValue ) {
        this.stringValue = stringValue;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Constant that = (Constant) o;

        return id == that.id;
    }

    @Override
    public int hashCode( ) {
        int result = id;
        result = 31 * result + ( constantName != null ? constantName.hashCode() : 0 );
        result = 31 * result + ( intValue != null ? intValue.hashCode() : 0 );
        result = 31 * result + ( stringValue != null ? stringValue.hashCode() : 0 );
        return result;
    }
}
