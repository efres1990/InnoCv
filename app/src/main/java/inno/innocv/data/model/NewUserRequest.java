package inno.innocv.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author eladiofreire on 23/8/17.
 */

public class NewUserRequest extends BaseRequest {
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("birthdate")
    @Expose
    private String mBirthdate;

    /**
     * Constructor class.
     *
     * @param name      user name.
     * @param birthdate birth.
     */
    public NewUserRequest(String name, String birthdate) {
        this.mName = name;
        this.mBirthdate = birthdate;
    }

    /**
     * Get user name.
     *
     * @return name.
     */
    public String getName() {
        return mName;
    }

    /**
     * Set user name.
     *
     * @param mName user name.
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * User birthdate.
     *
     * @return birthdate.
     */
    public String getBirthdate() {
        return mBirthdate;
    }

    /**
     * Set birthdate.
     *
     * @param mBirthdate birthdate.
     */
    public void setBirthdate(String mBirthdate) {
        this.mBirthdate = mBirthdate;
    }

    /**
     * To String.
     *
     * @return toString.
     */
    @Override
    public String toString() {
        return "NewUserRequest{" +
                "name='" + mName + '\'' +
                ", birthdate='" + mBirthdate + '\'' +
                '}';
    }
}
