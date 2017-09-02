package inno.innocv.data.loader;

/**
 * @author eladiofreire on 22/8/17.
 */

public interface DataCallback<T> {

    void onResponse(T t);

    void onFail(int message);
}