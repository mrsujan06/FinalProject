package com.test.musicfinderpro.tabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.test.musicfinderpro.R;
import com.test.musicfinderpro.adapters.SearchArtistAdapter;
import com.test.musicfinderpro.api.ApiObservableArtistService;
import com.test.musicfinderpro.model.ArtistResponse;

import java.util.concurrent.TimeUnit;

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

    SearchView mSearchView;
    RecyclerView artistList;

    ArtistResponse artistResponse;

    private Unbinder unbinder;
    private ApiObservableArtistService apiObservableArtistService;
    SearchArtistAdapter searchArtistAdapter;
    private View view;
    boolean isDataLoaded = false;
    private SwipeRefreshLayout strl;



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




//        EditText searchEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.);
//        searchEditText.setTextColor(getResources().getColor(R.color.white));
//        searchEditText.setHintTextColor(getResources().getColor(R.color.white));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.search_artist_tab2, container, false);
        //strl = view.findViewById(R.id.swipe2Refresh);
      //  strl.setRefreshing(true);

        mSearchView = view.findViewById(R.id.artistSearchView);
        artistList = view.findViewById(R.id.artistList);
        artistList.setLayoutManager(new LinearLayoutManager(getActivity()));
        artistList.setAdapter(searchArtistAdapter = new SearchArtistAdapter());

        loadData();
        searchArtistAdapter.notifyDataSetChanged();

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


    //Method to load Artist from Api
    public void loadData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.theaudiodb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiObservableArtistService = retrofit.create(ApiObservableArtistService.class);

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
                        return  apiObservableArtistService.searchArtist(query.toString());
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



}

