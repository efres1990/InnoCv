package inno.innocv.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eladiofreire on 21/8/17.
 */

public class UserInfoValue {

    @SerializedName("$type")
    @Expose
    private String mType;
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("birthdate")
    @Expose
    private String mBrithdate;

    /**
     * Get type.
     *
     * @return type.
     */
    public String getType() {
        return mType;
    }

    /**
     * Set type.
     *
     * @param mType type.
     */
    public void setType(String mType) {
        this.mType = mType;
    }

    /**
     * Get user id.
     *
     * @return id.
     */
    public int getId() {
        return mId;
    }

    /**
     * Set id.
     *
     * @param mId user id.
     */
    public void setId(int mId) {
        this.mId = mId;
    }

    /**
     * Get Name.
     *
     * @return user name.
     */
    public String getName() {
        return mName;
    }

    /**
     * Set name.
     *
     * @param mName user name.
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Get birthdate.
     *
     * @return birthdate.
     */
    public String getBrithdate() {
        return mBrithdate;
    }

    /**
     * Set birthdate.
     *
     * @param mBrithdate birthdate.
     */
    public void setBrithdate(String mBrithdate) {
        this.mBrithdate = mBrithdate;
    }

    /**
     * To String.
     *
     * @return toString.
     */
    @Override
    public String toString() {
        return "UserInfoValue{" +
                "type='" + mType + '\'' +
                ", id=" + mId +
                ", name='" + mName + '\'' +
                ", brithdate='" + mBrithdate + '\'' +
                '}';
    }
}
