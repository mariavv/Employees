package com.maria.employees.ui.fragment.specialities;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maria.employees.R;
import com.maria.employees.model.db.DbProvider;

class SpecialtiesFeedAdapter extends RecyclerView.Adapter<SpecialtiesViewHolder>
        implements SpecialtiesViewHolder.Listener {

    private OnItemClickListener mOnItemClickListener;

    private Cursor mItems;

    SpecialtiesFeedAdapter(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SpecialtiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_profession, parent, false);

        return new SpecialtiesViewHolder(v, mItems, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialtiesViewHolder holder, int position) {
        mItems.moveToPosition(position);
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.getCount();
        }
        return 0;
    }

    @Override
    public void itemClick() {
        mOnItemClickListener.onItemClick(mItems.getString(mItems.getColumnIndex(DbProvider.SPEC_ID)));
    }

    public void updateItems(Cursor entities) {
        if (entities != null) {
            mItems = entities;
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String specialityId);
    }
}
