package com.maria.employees.ui.fragment.specialities;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maria.employees.R;
import com.maria.employees.model.db.DbProvider;

public class SpecialtiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mVview;
    private Cursor mItems;
    private Listener mListener;

    SpecialtiesViewHolder(View itemView, Cursor items, Listener listener) {
        super(itemView);

        this.mItems = items;
        this.mListener = listener;

        mVview = itemView.findViewById(R.id.speciality);
        mVview.setOnClickListener(this);
    }

    void bindData() {
        mVview.setText(mItems.getString(mItems.getColumnIndex(DbProvider.SPEC_NAME)));
        mVview.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            mItems.moveToPosition(position);
            mListener.itemClick();
        }
    }

    public interface Listener {
        void itemClick();
    }
}
