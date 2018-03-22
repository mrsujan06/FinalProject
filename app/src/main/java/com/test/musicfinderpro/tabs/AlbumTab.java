package com.test.musicfinderpro.tabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.musicfinderpro.R;
import com.test.musicfinderpro.adapters.AlbumAdapter;
import com.test.musicfinderpro.api.ApiObservableArtistService;
import com.test.musicfinderpro.model.AlbumResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlbumTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlbumTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView image;

    @BindView(R.id.swipe2Refresh4)
    SwipeRefreshLayout strl;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.recyclerView4)
    RecyclerView recyclerView4;
    @BindView(R.id.recyclerView5)
    RecyclerView recyclerView5;
    @BindView(R.id.recyclerView6)
    RecyclerView recyclerView6;
    @BindView(R.id.recyclerView7)
    RecyclerView recyclerView7;
    @BindView(R.id.recyclerView8)
    RecyclerView recyclerView8;
    @BindView(R.id.recyclerView9)
    RecyclerView recyclerView9;
    AlbumAdapter albumAdapter;
    ApiObservableArtistService reqInterface3;
    View view;

    private OnFragmentInteractionListener mListener;

    public AlbumTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumTab.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumTab newInstance(String param1, String param2) {
        AlbumTab fragment = new AlbumTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_album_tab3, container, false);

        ButterKnife.bind(this, view);

        /**Checking network**/
        if (!isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_SHORT).show();
        }

        recyclerView3.setAdapter(albumAdapter);
        networkCallSearchAlbum("Ed Sheeran");
        networkCallSearchAlbum2("bruno mars");
        networkCallSearchAlbum3("the weeknd");
        networkCallSearchAlbum4("taylor swift");
        networkCallSearchAlbum5("eminem");
        networkCallSearchAlbum6("coldplay");
        networkCallSearchAlbum7("beyonce");


        strl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                strl.setRefreshing(true);
                recyclerView3.setAdapter(albumAdapter);
                networkCallSearchAlbum("Ed Sheeran");
                networkCallSearchAlbum2("bruno mars");
                networkCallSearchAlbum3("the weeknd");
                networkCallSearchAlbum4("taylor swift");
                networkCallSearchAlbum5("eminem");
                networkCallSearchAlbum6("coldplay");
                networkCallSearchAlbum7("beyonce");


                strl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView3.setAdapter(albumAdapter);
                        networkCallSearchAlbum("Ed Sheeran");
                        networkCallSearchAlbum2("bruno mars");
                        networkCallSearchAlbum3("the weeknd");
                        networkCallSearchAlbum4("taylor swift");
                        networkCallSearchAlbum5("eminem");
                        networkCallSearchAlbum6("coldplay");
                        networkCallSearchAlbum7("beyonce");
                    }
                }, 300);
                dotheUpdate();
            }
        });
        return view;
    }

    private void dotheUpdate() {
        strl.setRefreshing(false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //search
    public void networkCallSearchAlbum(final String artistName) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

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

                        if (recyclerView3 != null) {
                            recyclerView3.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                            recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        }
                        //   Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    /**
     * List all the Albums of Artist when Artist name pass to
     * @param  artistName
     */

    public void networkCallSearchAlbum2(final String artistName) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

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

                        recyclerView4.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                        //    Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void networkCallSearchAlbum3(final String artistName) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

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

                        recyclerView5.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                        //  Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void networkCallSearchAlbum4(final String artistName) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

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

                        recyclerView6.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                        //      Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void networkCallSearchAlbum5(final String artistName) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

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

                        recyclerView7.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                        recyclerView7.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                        //      Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void networkCallSearchAlbum6(final String artistName) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

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

                        recyclerView8.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                        recyclerView8.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                        //   Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void networkCallSearchAlbum7(final String artistName) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

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

                        recyclerView9.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                        recyclerView9.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                        //      Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
