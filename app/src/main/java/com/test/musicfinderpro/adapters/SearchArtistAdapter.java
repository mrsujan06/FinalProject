package com.test.musicfinderpro.adapters;

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

import java.util.LinkedList;
import java.util.List;

public class SearchArtistAdapter  extends RecyclerView.Adapter<SearchArtistAdapter.SearchArtistAdapterViewHolder> {

   List<Artist> mResult = new LinkedList<>();;
//    Context context;

//    public iSearchArtistAdapter(List<Artist> results , Context context ) {
//        this.mResult = results;
//        this.context = context;
//    }

   @Override
   public SearchArtistAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_layout, parent, false);
//        return new SearchArtistAdapterViewHolder(view);
       return new SearchArtistAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_layout, parent, false));
   }

    @Override
    public void onBindViewHolder(SearchArtistAdapterViewHolder holder, int position) {
        holder.bind(mResult.get(position));
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
   public class SearchArtistAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       final ImageView artistImageView;
       final TextView artistName;
       private Artist response;


       SearchArtistAdapterViewHolder(View itemView) {
           super(itemView);
           artistImageView = itemView.findViewById(R.id.artistImageView);
           artistName = itemView.findViewById(R.id.artistName);
           itemView.setOnClickListener(this);

       }

       void bind(Artist response) {
           this.response = response;
           this.artistName.setText(response.getStrArtist());
           Glide.with(this.artistImageView.getContext()).load(response.getStrArtistThumb()).into(artistImageView);
       }

       @Override
       public void onClick(View v) {

           Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();
           //playMusic(results.get(getAdapterPosition()).getIdAlbum());
       }

   }

//    private void playMusic(String url) {
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setDataAndType(uri, "audio/m4a");
//        //intent.setDataAndType(uri, "audio/*");
//        context.startActivity(intent);
//    }

}
