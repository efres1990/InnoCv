package inno.innocv.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eladiofreire on 24/8/17.
 */

public class EditUserRequest extends NewUserRequest {
    @SerializedName("id")
    @Expose
    private int mId;

    /**
     * Constructor class.
     *
     * @param name      name user.
     * @param birthdate birthdate.
     * @param id        user id.
     */
    public EditUserRequest(String name, String birthdate, int id) {
        super(name, birthdate);
        mId = id;
    }

    /**
     * Get user id.
     *
     * @return mId.
     */
    public int getId() {
        return mId;
    }

    /**
     * Set user ID.
     *
     * @param mId user id.
     */
    public void setId(int mId) {
        this.mId = mId;
    }

    /**
     * To String.
     *
     * @return toString.
     */
    @Override
    public String toString() {
        return "EditUserRequest{" +
                "mId=" + mId +
                '}';
    }
}
