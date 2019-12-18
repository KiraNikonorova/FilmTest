package com.example.filmstest.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmstest.Model.Film;
import com.example.filmstest.Model.FilmAdapter;
import com.example.filmstest.R;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class FragmentFilm extends Fragment {


    private ArrayList<Film> filmList;

    public FragmentFilm(ArrayList<Film> listFilm){
        this.filmList = listFilm;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_film_genre, container, false);
        FilmAdapter fadapter = new FilmAdapter(filmList);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);


        recyclerView.setAdapter(fadapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);


        fadapter.setListener(new FilmAdapter.Listener() {
            @Override
            public void onClick(int position) {


                Fragment fragmentDetail = new FilmDetail(filmList.get(position));


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.add(R.id.frame,fragmentDetail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        return  recyclerView;
    }



}
