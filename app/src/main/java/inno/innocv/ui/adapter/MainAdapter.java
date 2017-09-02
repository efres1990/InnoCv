package inno.innocv.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import inno.innocv.R;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.listener.SubmitListener;
import inno.innocv.ui.activity.EditActivity;
import inno.innocv.ui.fragment.main.MainPresenter;
import inno.innocv.ui.fragment.main.MainPresenterImpl;
import inno.innocv.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eladiofreire on 22/8/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context mContext;
    private List<UserInfoValue> mData;
    private MainPresenter mPresenter;


    public MainAdapter(Context context, List<UserInfoValue> data) {
        mContext = context;
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private View rootView;
        private TextView mTextViewData;
        private TextView mTextViewBirthday;
        private ImageView mUserImageView;
        private ItemClickListener clickListener;
        private ImageView mImageViewOption;

        private ViewHolder(View v) {
            super(v);
            rootView = v.findViewById(R.id.root_view);
            mTextViewData = (TextView) v.findViewById(R.id.text_userlist);
            mUserImageView = (ImageView) v.findViewById(R.id.image_user);
            mTextViewBirthday = (TextView) v.findViewById(R.id.text_birthday);
            mImageViewOption = (ImageView) v.findViewById(R.id.image_option);


            v.setTag(v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            mImageViewOption.setOnClickListener(this);


        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        mPresenter = new MainPresenterImpl(mContext, new SubmitListener() {
            @Override
            public void onSubmit(ArrayList<UserInfoValue> userInfoValues) {

            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserInfoValue model = mData.get(position);
        holder.mImageViewOption.setImageResource(R.drawable.ic_menu);
        holder.mImageViewOption.setVisibility(View.VISIBLE);

        holder.mTextViewData.setText(model.getName() + " (Id: " + model.getId() + ")");
        holder.mTextViewBirthday.setText(Utils.changeDate((model.getBrithdate()).replace("T", " ")));
        holder.mUserImageView.setImageResource(R.drawable.ic_filled_circle);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {

                    System.out.println(position);
                } else {
                    switch (view.getId()) {
                        case R.id.image_option:
                            System.out.println("dentro");
                            PopupMenu popup = new PopupMenu(mContext, holder.mImageViewOption);
                            //inflating menu from xml resource
                            popup.inflate(R.menu.menu_options);
                            //adding click listener
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.menu_edit:
                                            Intent i = new Intent(mContext, EditActivity.class);
                                            i.putExtra("name", model.getName());
                                            i.putExtra("date", Utils.changeDate(model.getBrithdate().replace("T", " ")));
                                            i.putExtra("id", model.getId());
                                            mContext.startActivity(i);
                                            //editUser(mContext, model.getBrithdate(), model.getName(), model.getId());
                                            break;
                                    }
                                    return false;
                                }
                            });
                            //displaying the popup
                            popup.show();
                            break;
                    }
                }
            }

        });

    }

    private void editUser(Context context, String date, String name, int id) {
        mPresenter.onUpdateUser(context, date, name, id);
    }

    private void deleteUser(Context context, int id) {
        mPresenter.onDeleteUser(context, id);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<UserInfoValue> getData() {
        return mData;
    }

    public void setData(List<UserInfoValue> data) {
        mData = data;
    }


}
