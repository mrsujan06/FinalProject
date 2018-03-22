package com.test.musicfinderpro;

import android.net.Uri;
import android.os.Bundle;
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
import com.test.musicfinderpro.api.ApiObservableArtistService;
import com.test.musicfinderpro.api.SendPushApi;
import com.test.musicfinderpro.model.AlbumResponse;
import com.test.musicfinderpro.model.modelpush.Notification;
import com.test.musicfinderpro.model.modelpush.PushRequestBody;
import com.test.musicfinderpro.model.modelpush.PushResponse;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements Detail_Fragment.OnFragmentInteractionListener {

    @BindView(R.id.imageViewArtist)
    ImageView imageViewArtist;
    @BindView(R.id.fanart)
    ImageView imageFanArt;
    @BindView(R.id.fanart2)
    ImageView imageFanArt2;
    @BindView(R.id.fanart3)
    ImageView imageFanArt3;
    @BindView(R.id.logo)
    ImageView imageViewLogo;
    @BindView(R.id.banner)
    ImageView imageViewBanner;
    @BindView(R.id.toolbar3)
    Toolbar toolbar3;
    @BindView(R.id.toolbar4)
    Toolbar toolbar4;
    @BindView(R.id.toolbar5)
    Toolbar toolbar5;
    @BindView(R.id.toolbar6)
    Toolbar toolbar6;
    @BindView(R.id.recyclerViewAlbums)
    RecyclerView recyclerViewAlbums;

    ApiObservableArtistService apiObservableArtistService;
    SearchAlbumAdapter searchAlbumAdapter;
    SearchView mSearchView;
    RecyclerView albumList;


    ApiObservableArtistService reqInterface3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Toolbar3
        toolbar3.setTitle("Bio");
        toolbar3.setTitleTextColor(0xFFFFFFFF);

        //Toolbar4
        toolbar4.setTitle("Pictures");
        toolbar4.setTitleTextColor(0xFFFFFFFF);

        //Toolbar4
        toolbar5.setTitle("Albums");
        toolbar5.setTitleTextColor(0xFFFFFFFF);

        setSupportActionBar(toolbar);


        networkCallForAlbums("Ed Sheeran");

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

    private void getIncomingIntent() {

        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("bio") && getIntent().hasExtra("logo")
                && getIntent().hasExtra("fanart") && getIntent().hasExtra("fanart2") && getIntent().hasExtra("fanart3")
                && getIntent().hasExtra("banner"))

        {

            String imageUrl = (String) getIntent().getExtras().getSerializable("image_url");
            String bio = (String) getIntent().getExtras().getSerializable("bio");
            String logo = (String) getIntent().getExtras().getSerializable("logo");
            String fanart = (String) getIntent().getExtras().getSerializable("fanart");
            String fanart2 = (String) getIntent().getExtras().getSerializable("fanart2");
            String fanart3 = (String) getIntent().getExtras().getSerializable("fanart3");
            String banner = (String) getIntent().getExtras().getSerializable("banner");
            setImage(imageUrl, bio, logo, fanart, fanart2, fanart3, banner);

        }


    }

    private void setImage(String imageUrl, String bio, String logo, String fanart, String fanart2, String fanart3, String banner) {

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

        //banner
        Glide.with(this)
                .asBitmap()
                .load(banner)
                .into(imageViewBanner);


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

        try {

            int cacheSize = 10 * 1024 * 1024; // 10 MB
            Cache cache = new Cache(getCacheDir(), cacheSize);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.theaudiodb.com")
                    .client(okHttpClient)
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

        }catch (Exception e){
            Toast.makeText(this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();
        }

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

    public void addToFavorite(View view){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        String token = "eAVmuPmTLFk:APA91bGc_HOqpwVa_cIuIaLAzGXWmFIB6zr6-h5LqvZBj8mw9MxnA0-KwJUBeXLhkoGvh9vBwlwidM128ZlnzdvCZ762NlCCYIvA4FahkZrTRbGimvIQaJkwhRtEkNEWq0eQvqo_AN7M";
        SendPushApi pushApi = retrofit.create(SendPushApi.class);
        Notification notification = new Notification("Artist successfully added to Favorite" , "Artist Added To Favorite");
        pushApi.sendPush(new PushRequestBody(token , notification))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PushResponse>() {
                    @Override
                    public void accept(PushResponse pushResponse) throws Exception {
                        Toast.makeText(getApplicationContext(), "Artist Added To Favorite", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        Toast.makeText(this, "Artist added to Favorite", Toast.LENGTH_SHORT).show();
    }


}
