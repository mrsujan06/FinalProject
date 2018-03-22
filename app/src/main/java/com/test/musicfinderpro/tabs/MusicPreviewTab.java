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
import android.widget.Toast;

import com.test.musicfinderpro.R;
import com.test.musicfinderpro.adapters.ArtistAdaperItune;
import com.test.musicfinderpro.adapters.ArtistAdapter;
import com.test.musicfinderpro.api.ApiObservableArtistService;
import com.test.musicfinderpro.model.ResultResponse;

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
 * {@link MusicPreviewTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicPreviewTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicPreviewTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recyclerView10)
    RecyclerView recyclerView10;
    @BindView(R.id.swipe2Refresh)
    SwipeRefreshLayout strl;

    ApiObservableArtistService reqInterface;
    ArtistAdapter artistAdapter;

    View view;

    private OnFragmentInteractionListener mListener;

    public MusicPreviewTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicPreviewTab.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicPreviewTab newInstance(String param1, String param2) {
        MusicPreviewTab fragment = new MusicPreviewTab();
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
        view = inflater.inflate(R.layout.fragment_music_preview_tab4, container, false);
        ButterKnife.bind(this, view);

        recyclerView10.setAdapter(artistAdapter);
        networkCallSearchArtist();

        /**Checking network**/
        if (!isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_SHORT).show();
        }

        strl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                strl.setRefreshing(true);
                networkCallSearchArtist();


                strl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        networkCallSearchArtist();
                    }
                }, 300);
                dotheUpdate();
            }
        });

        //   networkCallSearchArtist();
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

    /**
     * call network and Artist name , Album name will be displayed in recyclerView
     * Data can be viewed offline
     */
    public void networkCallSearchArtist() {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getActivity().getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        reqInterface = retrofit.create(ApiObservableArtistService.class);
        reqInterface.getPopData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultResponse>() {

                    @Override
                    public void accept(ResultResponse resultResponse) throws Exception {

                        recyclerView10.setAdapter(new ArtistAdaperItune(resultResponse.getResults(), getActivity()));
                        recyclerView10.setLayoutManager(new LinearLayoutManager(getActivity()));
                        Toast.makeText(getActivity(), resultResponse.getResults().get(0).getArtistName(), Toast.LENGTH_LONG).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "Please check your network", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     *Checks phone network connections
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
