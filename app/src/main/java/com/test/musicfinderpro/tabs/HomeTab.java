package com.test.musicfinderpro.tabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.musicfinderpro.R;
import com.test.musicfinderpro.adapters.SlideShowAdapter;

import com.test.musicfinderpro.adapters.TopAlbumAdapter;
import com.test.musicfinderpro.adapters.TopSingleAdapter;
import com.test.musicfinderpro.api.ApiObservableArtistService;
import com.test.musicfinderpro.model.TrendingResponse;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.swipe2Refresh2)
    SwipeRefreshLayout strl;
    @BindView(R.id.viewPager_id)
    ViewPager viewPager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.circleIndicator_id)
    CircleIndicator indicator;

    Handler handler;
    Runnable runnable;
    Timer timer;
    SlideShowAdapter adapter;
    ApiObservableArtistService reqInterface, reqInterface2;


    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeTab.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeTab newInstance(String param1, String param2) {
        HomeTab fragment = new HomeTab();
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
        view = inflater.inflate(R.layout.fragment_home_tab1, container, false);
        ButterKnife.bind(this , view);

        /**
         * Checking network
         * If Network is not available
         * Toast message to will appear on the screen
         * **/
        if (!isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_SHORT).show();
        }

        //SlideShow
        adapter = new SlideShowAdapter(getContext());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        //Toolbar
        toolbar.setTitle("Top Albums");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        //Toolbar2
        toolbar2.setTitle("Top Singles");
        toolbar2.setTitleTextColor(0xFFFFFFFF);

        strl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                strl.setRefreshing(true);
                networkCallForTopAlbums();
                networkCallForTopSingles();


                strl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        networkCallForTopAlbums();
                        networkCallForTopSingles();
                    }
                }, 500);
                dotheUpdate();
            }
        });


        networkCallForTopAlbums();
        networkCallForTopSingles();


        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {

                int i = viewPager.getCurrentItem();

                if (i == adapter.images.length - 1) {

                    i = 0;
                    viewPager.setCurrentItem(i, true);

                } else {

                    i++;
                    viewPager.setCurrentItem(i, true);
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.post(runnable);
            }
        }, 3500, 3500);

        return view;
    }


    /**
     * Sets swipe to refresh to false
     * if this method is not called
     * refresh will keep on spinning
     */
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
     * Call Top Albums from Api
     */

    public void networkCallForTopAlbums() {

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.theaudiodb.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            reqInterface = retrofit.create(ApiObservableArtistService.class);
            reqInterface.getTrendingAlbums()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<TrendingResponse>() {

                        @Override
                        public void accept(TrendingResponse trendingResponse) throws Exception {

                            recyclerView.setAdapter(new TopAlbumAdapter(trendingResponse.getTrending(), getActivity()));
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            //  Toast.makeText(getActivity(), trendingResponse.getTrending().get(0).getStrArtist(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //  Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }catch (Exception e){
            Toast.makeText(getContext(), "Error Loading", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Checking if networking is Available or not
     * @return Not connected if the network is not
     * available
     */
    public void networkCallForTopSingles() {

        try {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.theaudiodb.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        reqInterface2 = retrofit.create(ApiObservableArtistService.class);
        reqInterface2.getTrendingSingles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrendingResponse>() {

                    @Override
                    public void accept(TrendingResponse trendingResponse) throws Exception {

                        recyclerView2.setAdapter(new TopSingleAdapter(trendingResponse.getTrending(), getActivity()));
                        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        //    Toast.makeText(getActivity(), trendingResponse.getTrending().get(0).getStrArtist(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

        }catch (Exception e){
            Toast.makeText(getContext(), "Error Loading", Toast.LENGTH_SHORT).show();
        }

    }




    /**
     * Checking if networking is Available or not
     * @return Not connected if the network is not
     * available
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
