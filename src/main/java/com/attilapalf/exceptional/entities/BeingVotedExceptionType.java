package com.attilapalf.exceptional.entities;

import javax.persistence.*;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table( name = "being_voted_exception_types", schema = "", catalog = "exceptional" )
public class BeingVotedExceptionType {
    private int id;
    private String shortName;
    private String prefix;
    private String description;
    private int votes;
    private int version;
    private User submittedBy;

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
    @Column( name = "short_name" )
    public String getShortName( ) {
        return shortName;
    }

    public void setShortName( String shortName ) {
        this.shortName = shortName;
    }

    @Basic
    @Column( name = "prefix" )
    public String getPrefix( ) {
        return prefix;
    }

    public void setPrefix( String prefix ) {
        this.prefix = prefix;
    }

    @Basic
    @Column( name = "description" )
    public String getDescription( ) {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Basic
    @Column( name = "votes" )
    public int getVotes( ) {
        return votes;
    }

    public void setVotes( int votes ) {
        this.votes = votes;
    }

    @Basic
    @Column( name = "version" )
    public int getVersion( ) {
        return version;
    }

    public void setVersion( int version ) {
        this.version = version;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        BeingVotedExceptionType that = (BeingVotedExceptionType) o;
        return id == that.id;
    }

    @Override
    public int hashCode( ) {
        int result = id;
        result = 31 * result + ( shortName != null ? shortName.hashCode() : 0 );
        result = 31 * result + ( prefix != null ? prefix.hashCode() : 0 );
        result = 31 * result + ( description != null ? description.hashCode() : 0 );
        result = 31 * result + votes;
        return result;
    }

    @OneToOne
    @JoinColumn( name = "submitted_by_fb_id", referencedColumnName = "facebook_id", nullable = false )
    public User getSubmittedBy( ) {
        return submittedBy;
    }

    public void setSubmittedBy( User submittedBy ) {
        this.submittedBy = submittedBy;
    }
}
