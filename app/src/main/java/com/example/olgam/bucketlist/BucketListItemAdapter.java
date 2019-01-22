package com.example.olgam.bucketlist;

import android.app.usage.NetworkStats;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class BucketListItemAdapter extends RecyclerView.Adapter<BucketListItemAdapter.ViewHolder> {

    private List<BucketListItem> mItems;
    final private ItemClickListener mItemClickListener;

    public interface ItemClickListener{
        void itemOnClick (int i);
    }

    public BucketListItemAdapter (List<BucketListItem> mItems, ItemClickListener mItemClickListener) {
        this.mItems = mItems;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public BucketListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, null);
        BucketListItemAdapter.ViewHolder viewHolder = new BucketListItemAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BucketListItemAdapter.ViewHolder viewHolder, final int i) {
        BucketListItem item =  mItems.get(i);
        if (mItems.get(i).isCrossedOff()) {
            viewHolder.titleView.setPaintFlags(viewHolder.titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textView.setPaintFlags(viewHolder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.titleView.setPaintFlags(viewHolder.titleView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.textView.setPaintFlags(viewHolder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        viewHolder.titleView.setText(item.getItemTitle());
        viewHolder.textView.setText(item.getItemText());
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(mItems.get(i).isCrossedOff());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("onBindViewHolderChanged","crossedOff set to "+isChecked);
                mItems.get(i).setCrossedOff(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mItems == null)
            return 0;
        else
            return mItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleView;
        public TextView textView;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.itemTitle);
            textView = itemView.findViewById(R.id.itemText);
            checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setOnClickListener(this);
        }

        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mItemClickListener.itemOnClick(clickedPosition);
        }
    }

    public void swapList (List<BucketListItem> newList) {
        mItems = newList;
        if (newList != null) {
            this.notifyDataSetChanged();
        }
    }
}
