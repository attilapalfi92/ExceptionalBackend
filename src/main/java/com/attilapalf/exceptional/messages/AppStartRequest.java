package com.attilapalf.exceptional.messages;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public class AppStartRequest extends BaseExceptionRequest {
    private String deviceId;
    private String gcmId;
    private List<BigInteger> friendsFacebookIds;
    private int exceptionVersion;
    private String firstName, lastName;
    private String deviceName;

    public AppStartRequest() {
    }

    public AppStartRequest(String deviceId, BigInteger userId,
                           List<BigInteger> friendsFacebookIds, List<BigInteger> exceptionIds) {
        this.deviceId = deviceId;
        this.userFacebookId = userId;
        this.friendsFacebookIds = friendsFacebookIds;
        this.knownExceptionIds = exceptionIds;
    }

    public AppStartRequest(String deviceId, String gcmId, List<BigInteger> friendsFacebookIds) {
        this.deviceId = deviceId;
        this.gcmId = gcmId;
        this.friendsFacebookIds = friendsFacebookIds;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public BigInteger getUserFacebookId() {
        return userFacebookId;
    }

    public void setUserFacebookId(BigInteger userId) {
        this.userFacebookId = userId;
    }

    public List<BigInteger> getFriendsFacebookIds() {
        return friendsFacebookIds;
    }

    public void setFriendsFacebookIds(List<BigInteger> friendsFacebookIds) {
        this.friendsFacebookIds = friendsFacebookIds;
    }

    public List<BigInteger> getKnownExceptionIds() {
        return knownExceptionIds;
    }

    public void setKnownExceptionIds(List<BigInteger> exceptionIds) {
        this.knownExceptionIds = exceptionIds;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public int getExceptionVersion() {
        return exceptionVersion;
    }

    public void setExceptionVersion(int exceptionVersion) {
        this.exceptionVersion = exceptionVersion;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        AppStartRequest that = (AppStartRequest) o;

        if ( exceptionVersion != that.exceptionVersion ) return false;
        if ( deviceId != null ? !deviceId.equals( that.deviceId ) : that.deviceId != null ) return false;
        if ( gcmId != null ? !gcmId.equals( that.gcmId ) : that.gcmId != null ) return false;
        if ( friendsFacebookIds != null ? !friendsFacebookIds.equals( that.friendsFacebookIds ) : that.friendsFacebookIds != null )
            return false;
        if ( firstName != null ? !firstName.equals( that.firstName ) : that.firstName != null ) return false;
        if ( lastName != null ? !lastName.equals( that.lastName ) : that.lastName != null ) return false;
        return !( deviceName != null ? !deviceName.equals( that.deviceName ) : that.deviceName != null );

    }

    @Override
    public int hashCode( ) {
        int result = deviceId != null ? deviceId.hashCode() : 0;
        result = 31 * result + ( gcmId != null ? gcmId.hashCode() : 0 );
        result = 31 * result + ( friendsFacebookIds != null ? friendsFacebookIds.hashCode() : 0 );
        result = 31 * result + exceptionVersion;
        result = 31 * result + ( firstName != null ? firstName.hashCode() : 0 );
        result = 31 * result + ( lastName != null ? lastName.hashCode() : 0 );
        result = 31 * result + ( deviceName != null ? deviceName.hashCode() : 0 );
        return result;
    }
}