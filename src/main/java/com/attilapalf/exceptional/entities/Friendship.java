package com.attilapalf.exceptional.entities;

import java.math.BigInteger;

import javax.persistence.*;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table( name = "friendships", schema = "", catalog = "exceptional" )
public class Friendship {
    private BigInteger friendshipId;
    private User user1;
    private User user2;

    public Friendship( ) {
    }

    public Friendship( User user, User friend ) {
        user1 = user;
        user2 = friend;
    }

    @Id
    @GeneratedValue
    @Column( name = "friendship_id", columnDefinition = "BIGINT(255)" )
    public BigInteger getFriendshipId( ) {
        return friendshipId;
    }

    public void setFriendshipId( BigInteger friendshipId ) {
        this.friendshipId = friendshipId;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Friendship that = (Friendship) o;

        if ( friendshipId != null ? !friendshipId.equals( that.friendshipId ) : that.friendshipId != null )
            return false;

        return true;
    }

    @Override
    public int hashCode( ) {
        return friendshipId != null ? friendshipId.hashCode() : 0;
    }

    @OneToOne
    @JoinColumn( name = "user_fb_id_1", referencedColumnName = "facebook_id", nullable = false )
    public User getUser1( ) {
        return user1;
    }

    public void setUser1( User fromUser ) {
        this.user1 = fromUser;
    }

    @OneToOne
    @JoinColumn( name = "user_fb_id_2", referencedColumnName = "facebook_id", nullable = false )
    public User getUser2( ) {
        return user2;
    }

    public void setUser2( User toUser ) {
        this.user2 = toUser;
    }
}
