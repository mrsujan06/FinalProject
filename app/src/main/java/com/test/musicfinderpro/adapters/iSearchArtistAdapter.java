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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.musicfinderpro.R;
import com.test.musicfinderpro.model.Artist;

import java.util.List;

/**
 * Created by Sujan on 18/03/2018.
 */

public class iSearchArtistAdapter extends RecyclerView.Adapter<iSearchArtistAdapter.SearchArtistAdapterViewHolder> {

    List<Artist> mResult;
    Context context;

//    public iSearchArtistAdapter(List<Artist> results , Context context ) {
//        this.mResult = results;
//        this.context = context;
//    }

    @Override
    public SearchArtistAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_layout, parent, false);
        return new SearchArtistAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchArtistAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    public void addArtistResponses(List<Artist> result) {
        mResult.clear();
        mResult.addAll(result);
        notifyDataSetChanged();
    }


    //Top Single View Holder
    class SearchArtistAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView artistImageView;
        final TextView artistName;


        SearchArtistAdapterViewHolder(View itemView) {
            super(itemView);
            artistImageView = itemView.findViewById(R.id.artistImageView);
            artistName = itemView.findViewById(R.id.artistName);
            itemView.setOnClickListener(this);

        }

        void bind(int position) {
            this.artistName.setText(mResult.get(position).getStrArtist());
            Glide.with(context.getApplicationContext()).load(mResult.get(position).getStrArtistThumb()).into(artistImageView);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(v.getContext(), mResult.get(getAdapterPosition()).getStrArtist(), Toast.LENGTH_SHORT).show();
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
