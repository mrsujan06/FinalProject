package com.test.musicfinderpro.tabs;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.test.musicfinderpro.DetailActivity;
import com.test.musicfinderpro.R;
import com.test.musicfinderpro.adapters.AlbumAdapter;
import com.test.musicfinderpro.adapters.PagerAdapter;
import com.test.musicfinderpro.adapters.SearchArtistAdapter;
import com.test.musicfinderpro.api.ApiObservableArtistService;
import com.test.musicfinderpro.model.AlbumResponse;
import com.test.musicfinderpro.model.ArtistResponse;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchArtistTab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchArtistTab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchArtistTab2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recyclerViewAlbums) RecyclerView recyclerViewAlbums;
    ApiObservableArtistService reqInterface3;

    static SearchView mSearchView;
    RecyclerView artistList;

    ArtistResponse artistResponse;
    private Unbinder unbinder;
    private ApiObservableArtistService apiObservableArtistService;
    SearchArtistAdapter searchArtistAdapter;
    private View view;
    boolean isDataLoaded = false;
    private SwipeRefreshLayout strl;
    PagerAdapter pagerAdapter;


    private OnFragmentInteractionListener mListener;

    public SearchArtistTab2() {

    }

    public static SearchArtistTab2 newInstance(String param1, String param2) {
        SearchArtistTab2 fragment = new SearchArtistTab2();
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

       getChildFragmentManager().addOnBackStackChangedListener(new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
           @Override
           public void onBackStackChanged() {

               if (getView()!=null){
                   getView().setFocusableInTouchMode(true);
                   getView().requestFocus();
               }

           }
       });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_artist_tab2, container, false);
        mSearchView = view.findViewById(R.id.artistSearchView);
        artistList = view.findViewById(R.id.artistList);
        artistList.setLayoutManager(new LinearLayoutManager(getActivity()));
        artistList.setAdapter(searchArtistAdapter = new SearchArtistAdapter());
        mSearchView.getQuery();
//        SearchView.OnQueryTextListener queryTextListener =(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                getInput(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
//
//        mSearchView.setOnQueryTextListener(queryTextListener);
        mSearchView.setFocusable(true);

        retroCall();
        loadData();

        return view;
    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        unbinder = null;
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser == true) {
            loadData();
        }
    }

    //Method to load Artist from Api
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
                .switchMap(new Function<CharSequence, ObservableSource<ArtistResponse>>() {
                    @Override
                    public ObservableSource<ArtistResponse> apply(CharSequence query) throws Exception {
                       return apiObservableArtistService.searchArtist(query.toString());



                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArtistResponse>() {
                               @Override
                               public void accept(ArtistResponse response) throws Exception {

                                   searchArtistAdapter.addArtistResponses(response.getArtists());



                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
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
                            recyclerViewAlbums.setAdapter(new AlbumAdapter(albumResponse.getAlbum(), getActivity()));
                            recyclerViewAlbums.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        }
                        //   Toast.makeText(getActivity(), albumResponse.getAlbum().get(0).getIntYearReleased(), Toast.LENGTH_SHORT).show();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void getInput(String searchText)
    {
        Intent in = new Intent(getContext(), DetailActivity.class);
        in.putExtra("searchQuery", searchText);
        startActivity(in);
    }


    public SearchView getmSearchView() {
        return mSearchView;
    }

public void updateFrame() {
    Fragment currentFragment = this;
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.detach(currentFragment);
    fragmentTransaction.attach(currentFragment);
    fragmentTransaction.commit();
}

}

