package inno.innocv.ui.adapter;

import android.view.View;

/**
 * Created by eladiofreire on 24/8/17.
 */

public interface ItemClickListener {
    /**
     * Click in item of recycler.
     *
     * @param view        view.
     * @param position position.
     * @param isLongClick is long click.
     */
    void onClick(View view, int position, boolean isLongClick);
}