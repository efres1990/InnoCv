package inno.innocv.data.model;

import com.google.gson.Gson;

/**
 * @author eladiosuarez.
 */

public class BaseRequest {


    /**
     * Transient means that this field dont want to serialize.
     */
    private transient Gson mSerializer = null;

    /**
     * Class constructor.
     */
    public BaseRequest() {
        mSerializer = new Gson();

    }

    /**
     * Get json serialized.
     *
     * @return json.
     */
    public String getRequestJSONserialized() {
        return mSerializer.toJson(this);
    }
}