package com.test.musicfinderpro.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.musicfinderpro.R;
import com.test.musicfinderpro.model.Result;

import java.util.List;

/**
 * Created by Sujan on 17/03/2018.
 */

public class ArtistAdaperItune extends RecyclerView.Adapter<ArtistAdaperItune.ArtistAdaperItuneViewHolder> {

    List<Result> results;
    Context context;

    public ArtistAdaperItune(List<Result> results , Context context ) {
        this.results = results;
        this.context = context;
    }

    @Override
    public ArtistAdaperItuneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_preview_layout, parent, false);
        return new ArtistAdaperItuneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistAdaperItuneViewHolder holder, int position) {
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
    class ArtistAdaperItuneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView ptvName;
        final TextView ptvCollection;
        final ImageView pimageView4;


        ArtistAdaperItuneViewHolder(View itemView) {
            super(itemView);
            ptvName = itemView.findViewById(R.id.ptvName);
            ptvCollection = itemView.findViewById(R.id.ptvCollection);
            pimageView4 = itemView.findViewById(R.id.pimageView4);

            itemView.setOnClickListener(this);

        }

        void bind(int position) {

            ptvName.setText(results.get(position).getArtistName());
            ptvCollection.setText(results.get(position).getCollectionName());
            Glide.with(context.getApplicationContext()).load(results.get(position).getArtworkUrl100()).into(pimageView4);

        }

        @Override
        public void onClick(View v) {


           // Toast.makeText(v.getContext(), results.get(getAdapterPosition()).getArtistName(), Toast.LENGTH_SHORT).show();
            playMusic(results.get(getAdapterPosition()).getPreviewUrl());
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
