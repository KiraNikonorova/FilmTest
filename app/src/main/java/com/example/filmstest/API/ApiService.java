package com.example.filmstest.API;

import com.example.filmstest.Model.FilmsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("sequeniatesttask/films.json")
    Call<FilmsList> getData();


}
