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

import com.test.musicfinderpro.R;
import com.test.musicfinderpro.model.Artist;

import java.util.List;

/**
 * Created by Sujan on 17/03/2018.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    List<Artist> results;
    Context context;

    public ArtistAdapter(List<Artist> results , Context context ) {
        this.results = results;
        this.context = context;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_preview_layout, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
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
    class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView pimageView4;

        ArtistViewHolder(View itemView) {
            super(itemView);
            pimageView4 = itemView.findViewById(R.id.pimageView4);

            itemView.setOnClickListener(this);


        }

        void bind(int position) {
          //  Glide.with(context.getApplicationContext()).load(results.get(position).getStrArtistThumb()).into(pimageView4);

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

