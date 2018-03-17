package com.test.musicfinderpro.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.musicfinderpro.DetailActivity;
import com.test.musicfinderpro.MainActivity;
import com.test.musicfinderpro.R;
import com.test.musicfinderpro.Tab1;
import com.test.musicfinderpro.Tab2;
import com.test.musicfinderpro.model.Trending;

import java.util.List;

/**
 * Created by Sujan on 15/03/2018.
 */

public class TopSingleAdapter extends RecyclerView.Adapter<TopSingleAdapter.TopSingleViewHolder> {

        List<Trending> results;
        Context context;

        public TopSingleAdapter(List<Trending> results , Context context ) {
            this.results = results;
            this.context = context;
        }

        @Override
        public TopSingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_singles_layout, parent, false);
            return new TopSingleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TopSingleViewHolder holder, int position) {
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
        class TopSingleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            final ImageView pimageView2;

            TopSingleViewHolder(View itemView) {
                super(itemView);
                pimageView2 = itemView.findViewById(R.id.pimageView2);
                itemView.setOnClickListener(this);


            }

            void bind(int position) {
                Glide.with(context.getApplicationContext()).load(results.get(position).getStrTrackThumb()).into(pimageView2);
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

