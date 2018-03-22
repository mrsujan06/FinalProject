package com.test.musicfinderpro.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.musicfinderpro.R;
import com.test.musicfinderpro.model.Album;
import com.test.musicfinderpro.model.Artist;

import java.util.List;

/**
 * Created by Sujan on 21/03/2018.
 */

public class SearchAlbumAdapter extends RecyclerView.Adapter<SearchAlbumAdapter.SearchAlbumViewHolder> {

    List<Album> mResult;

    @Override
    public SearchAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.albums_layout, parent, false);
        return new SearchAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAlbumViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    public void addAlbumResponses(List<Album> result) {
        mResult.clear();
        mResult.addAll(result);
        notifyDataSetChanged();
    }

    public void emptyItems() {
        mResult.clear();
        notifyDataSetChanged();
    }


    //Top Single View Holder
    class SearchAlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView pimageView3;

        SearchAlbumViewHolder(View itemView) {
            super(itemView);
            pimageView3 = itemView.findViewById(R.id.pimageView3);
            itemView.setOnClickListener(this);


        }

        void bind(int position) {
            Glide.with(this.pimageView3.getContext()).load(mResult.get(position).getStrAlbumThumb()).into(pimageView3);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(v.getContext(), mResult.get(getAdapterPosition()).getStrArtist(), Toast.LENGTH_SHORT).show();
        }

    }

}
