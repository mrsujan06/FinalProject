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

import java.util.List;

/**
 * Created by Sujan on 17/03/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    List<Album> results;
    Context context;

    public AlbumAdapter(List<Album> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.albums_layout, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
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


    //Top Single View Holder
    class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView pimageView3;

        AlbumViewHolder(View itemView) {
            super(itemView);
            pimageView3 = itemView.findViewById(R.id.pimageView3);
            itemView.setOnClickListener(this);


        }

        void bind(int position) {
            Glide.with(context.getApplicationContext()).load(results.get(position).getStrAlbumThumb()).into(pimageView3);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(v.getContext(), results.get(getAdapterPosition()).getStrArtist(), Toast.LENGTH_SHORT).show();
            //playMusic(results.get(getAdapterPosition()).getIdAlbum());
        }

    }

    private void playMusic(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setDataAndType(uri, "audio/m4a");
        //intent.setDataAndType(uri, "audio/*");
        context.startActivity(intent);
    }


}
