package com.test.musicfinderpro;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.test.musicfinderpro.adapters.AlbumAdapter;
import com.test.musicfinderpro.adapters.SearchAlbumAdapter;
import com.test.musicfinderpro.adapters.SearchArtistAdapter;
import com.test.musicfinderpro.api.ApiObservableArtistService;
import com.test.musicfinderpro.model.AlbumResponse;
import com.test.musicfinderpro.model.Artist;
import com.test.musicfinderpro.model.ArtistResponse;
import com.test.musicfinderpro.tabs.SearchArtistTab2;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements Detail_Fragment.OnFragmentInteractionListener {

    @BindView(R.id.imageViewArtist) ImageView imageViewArtist;
    @BindView(R.id.fanart) ImageView imageFanArt;
    @BindView(R.id.fanart2) ImageView imageFanArt2;
    @BindView(R.id.fanart3) ImageView imageFanArt3;
    @BindView(R.id.logo) ImageView imageViewLogo;
    @BindView(R.id.recyclerViewAlbums) RecyclerView recyclerViewAlbums;
    ApiObservableArtistService apiObservableArtistService;
    SearchAlbumAdapter searchAlbumAdapter;
    SearchView mSearchView;
    RecyclerView albumList;


    ApiObservableArtistService reqInterface3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ButterKnife.bind(this );
        networkCallForAlbums("Simple plan");

//        mSearchView = findViewById(R.id.artistSearchView);
//        albumList = findViewById(R.id.recyclerViewAlbums);
//        albumList.setLayoutManager(new LinearLayoutManager(this));
//        albumList.setAdapter(searchAlbumAdapter = new SearchAlbumAdapter());
       // networkCallForAlbums("Simple plan");
//
//
//        retroCall();
//        loadData();



        getIncomingIntent();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("bio")&& getIntent().hasExtra("logo")
                && getIntent().hasExtra("fanart") && getIntent().hasExtra("fanart2") && getIntent().hasExtra("fanart3"))
        {

            String imageUrl = (String) getIntent().getExtras().getSerializable("image_url");
            String bio = (String) getIntent().getExtras().getSerializable("bio");
            String logo = (String) getIntent().getExtras().getSerializable("logo");
            String fanart = (String) getIntent().getExtras().getSerializable("fanart");
            String fanart2 = (String) getIntent().getExtras().getSerializable("fanart2");
            String fanart3 = (String) getIntent().getExtras().getSerializable("fanart3");
            setImage(imageUrl , bio , logo  , fanart , fanart2 , fanart3);

        }


    }

    private void setImage(String imageUrl , String bio , String logo , String fanart , String fanart2 , String fanart3){

        //fanart
        Glide.with(this)
                .asBitmap()
                .load(fanart)
                .into(imageFanArt);

        //fanart
        Glide.with(this)
                .asBitmap()
                .load(fanart2)
                .into(imageFanArt2);

        //fanart
        Glide.with(this)
                .asBitmap()
                .load(fanart3)
                .into(imageFanArt3);

        //logo
        Glide.with(this)
                .asBitmap()
                .load(logo)
                .into(imageViewLogo);

        //Artist Photo
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(imageViewArtist);

        //bio
        TextView artistBio = findViewById(R.id.bio);
        artistBio.setText(bio);

    }

    //Artist Albums detail page
    public void networkCallForAlbums(String artistName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.theaudiodb.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        reqInterface3 = retrofit.create(ApiObservableArtistService.class);
        reqInterface3.searchAlbums(artistName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AlbumResponse>() {

                    @Override
                    public void accept(AlbumResponse albumResponse) throws Exception {

                        if (recyclerViewAlbums != null) {
                            recyclerViewAlbums.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), DetailActivity.this));
                            recyclerViewAlbums.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        }
                        //   Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void loadData() {
        RxSearchView.queryTextChanges(mSearchView)
                .subscribeOn(AndroidSchedulers.mainThread())
                // .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(100, TimeUnit.MILLISECONDS)

                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return !charSequence.toString().isEmpty();
                    }
                })
                //.distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<CharSequence, ObservableSource<AlbumResponse>>() {
                    @Override
                    public ObservableSource<AlbumResponse> apply(CharSequence query) throws Exception {
                        return apiObservableArtistService.searchAlbums(query.toString());


                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AlbumResponse>() {
                               @Override
                               public void accept(AlbumResponse response) throws Exception {

                                   searchAlbumAdapter.addAlbumResponses(response.getAlbum());

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(DetailActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

    }

    public void retroCall() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.theaudiodb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiObservableArtistService = retrofit.create(ApiObservableArtistService.class);

    }





}
