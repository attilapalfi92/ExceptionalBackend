package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-11.
 */
@Entity
@Table(name = "friends", schema = "", catalog = "exceptional")
public class FriendsEntity {
    private BigInteger friendshipId;
    private UsersEntity user1;
    private UsersEntity user2;

    @Id
    @Column(name = "friendship_id", columnDefinition = "INT(20)", nullable = false, insertable = true, updatable = true)
    public BigInteger getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(BigInteger friendshipId) {
        this.friendshipId = friendshipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendsEntity that = (FriendsEntity) o;

        if (!friendshipId.equals(that.friendshipId)) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        return friendshipId;
//    }

    @ManyToOne
    @JoinColumn(name = "user_id_1", referencedColumnName = "user_id", nullable = false)
    public UsersEntity getUser1() {
        return user1;
    }

    public void setUser1(UsersEntity usersByUserId1) {
        this.user1 = usersByUserId1;
    }

    @ManyToOne
    @JoinColumn(name = "user_id_2", referencedColumnName = "user_id", nullable = false)
    public UsersEntity getUser2() {
        return user2;
    }

    public void setUser2(UsersEntity usersByUserId2) {
        this.user2 = usersByUserId2;
    }
}
