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
import com.test.musicfinderpro.MainActivity;
import com.test.musicfinderpro.R;
import com.test.musicfinderpro.model.Trending;

import java.util.List;

/**
 * Created by Sujan on 15/03/2018.
 */

public class TopAlbumAdapter  extends RecyclerView.Adapter <TopAlbumAdapter.TopAlbumViewHolder>{

    Context context;
    List<Trending> results;

    public TopAlbumAdapter(List<Trending> results , Context context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public TopAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_artist_layout, parent, false);
        return new TopAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopAlbumViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void emptyItems() {
        results.clear();
        notifyDataSetChanged();
    }

    //Artist View Holder

    class TopAlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView cimageView;

        TopAlbumViewHolder(View itemView) {
            super(itemView);
            cimageView = itemView.findViewById(R.id.pimageView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            Glide.with(context.getApplicationContext()).load(results.get(position).getStrAlbumThumb()).into(cimageView);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), results.get(getAdapterPosition()).getStrArtist(), Toast.LENGTH_SHORT).show();
            // playMusic(results.get(getAdapterPosition()).getPreviewUrl());
        }
    }

    private void playMusic(String url) {
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setDataAndType(uri, "audio/m4a");
//        //intent.setDataAndType(uri, "audio/*");
//        startActivity(intent);
    }


}
