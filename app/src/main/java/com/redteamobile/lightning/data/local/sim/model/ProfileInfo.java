package com.redteamobile.lightning.data.local.sim.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileInfo implements Parcelable {

    private String iccid;
    private String icon;
    private int iconType;
    private String isdpAid;
    private int profileClass;
    private String profileName;
    private String profileNickname;
    private int profileState;
    private String serviceProviderName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iccid);
        dest.writeString(this.icon);
        dest.writeInt(this.iconType);
        dest.writeString(this.isdpAid);
        dest.writeInt(this.profileClass);
        dest.writeString(this.profileName);
        dest.writeString(this.profileNickname);
        dest.writeInt(this.profileState);
        dest.writeString(this.serviceProviderName);
    }

    public ProfileInfo() {
    }

    @Override
    public String toString() {
        return "ProfileInfo{" +
                "iccid='" + iccid + '\'' +
                ", icon='" + icon + '\'' +
                ", iconType=" + iconType +
                ", isdpAid='" + isdpAid + '\'' +
                ", profileClass=" + profileClass +
                ", profileName='" + profileName + '\'' +
                ", profileNickname='" + profileNickname + '\'' +
                ", profileState=" + profileState +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                '}';
    }

    protected ProfileInfo(Parcel in) {
        this.iccid = in.readString();
        this.icon = in.readString();
        this.iconType = in.readInt();
        this.isdpAid = in.readString();
        this.profileClass = in.readInt();
        this.profileName = in.readString();
        this.profileNickname = in.readString();
        this.profileState = in.readInt();
        this.serviceProviderName = in.readString();
    }

    public static final Creator<ProfileInfo> CREATOR = new Creator<ProfileInfo>() {
        @Override
        public ProfileInfo createFromParcel(Parcel source) {
            return new ProfileInfo(source);
        }

        @Override
        public ProfileInfo[] newArray(int size) {
            return new ProfileInfo[size];
        }
    };

    public String getIccid() {
        return iccid;
    }

    public String getIcon() {
        return icon;
    }

    public int getIconType() {
        return iconType;
    }

    public String getIsdpAid() {
        return isdpAid;
    }

    public int getProfileClass() {
        return profileClass;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getProfileNickname() {
        return profileNickname;
    }

    public int getProfileState() {
        return profileState;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public void setProfileState(int profileState) {
        this.profileState = profileState;
    }
}
