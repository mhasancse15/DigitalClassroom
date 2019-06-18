package com.digitalclassroom15.digitalclassroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalclassroom15.digitalclassroom.R;
import com.digitalclassroom15.digitalclassroom.model.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public UserAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textSection.setText(uploadCurrent.getSectionName());
        holder.textClassName.setText(uploadCurrent.getClassName());
        holder.textSubjectName.setText(uploadCurrent.getSubjectName());
        holder.textsubdate.setText(uploadCurrent.getLastDate());

        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                //           .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName,textClassName,textSection,textSubjectName,textsubdate;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.titleTV);
            imageView = itemView.findViewById(R.id.imageIV);
            textSection=itemView.findViewById(R.id.sectionNameTV);
            textClassName=itemView.findViewById(R.id.classNameTV);
            textSubjectName=itemView.findViewById(R.id.subjectNameTV);
            textsubdate=itemView.findViewById(R.id.lastDateTV);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Download");
            doWhatever.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}