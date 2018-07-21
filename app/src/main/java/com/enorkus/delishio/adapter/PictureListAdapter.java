package com.enorkus.delishio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.enorkus.delishio.R;
import com.enorkus.delishio.entity.Picture;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.ViewHolder> {

    private List<Picture> pictures;
    private Context ctx;

    public PictureListAdapter(Context ctx, List<Picture> response) {
        this.ctx = ctx;
        this.pictures = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_item_picture, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Picture picture = pictures.get(position);
        Picasso.with(ctx)
                .load(picture.getWebformatURL())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView picture;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.pictureItem);
        }
    }
}
