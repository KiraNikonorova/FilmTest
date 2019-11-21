package com.example.filmstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmstest.Fragment.FilmDetail;
import com.example.filmstest.Fragment.FragmentDetective;
import com.example.filmstest.Fragment.FragmentDrama;
import com.example.filmstest.Model.Film;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.filmstest.API.ApiService;
import com.example.filmstest.Model.FilmAdapter;
import com.example.filmstest.Model.FilmsList;
import com.example.filmstest.Network.RetroClient;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Film> filmList;
    private FragmentDetective fragDet;
    private FragmentDrama fragDr;
    private FilmDetail fragmentDetail;
    private FragmentTransaction fTrans;
    private RelativeLayout relativeLayout;


    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<FilmsList> call = api.getData();

        /**
         * Enqueue Callback will be call when get response...
         */

        call.enqueue(new Callback<FilmsList>() {
            @Override
            public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {


//                pDialog.dismiss();

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     *
                     */
                    filmList = response.body().getFilms();

                    //список жанров
                    ArrayList<String> listGenre = CreateListGenre();





                    FilmAdapter fadapter = new FilmAdapter(filmList);

                    //filter
                    ArrayList<Film> listDetective = new ArrayList<>(filmList);
                    ArrayList<Film> listDrama = new ArrayList<>(filmList);
                    listDrama.removeIf(s -> !s.getGenres().contains("драма".toLowerCase()));
                    listDetective.removeIf(s -> !s.getGenres().contains("детектив".toLowerCase()));


                    //фрагменты
                    fragDet = new FragmentDetective(listDetective) ;
                    fragDr = new FragmentDrama(listDrama);


                    //активность кнопок
                    Button bt1 = findViewById(R.id.bt1);
                    Button bt2 = findViewById(R.id.bt2);
                    bt1.setEnabled(true);
                    bt2.setEnabled(true);


                    //обработка клика


                }
            }

            @Override
            public void onFailure(Call<FilmsList> call, Throwable t) {

                Log.e("error", "shit happens" + t.getMessage());
                t.printStackTrace();

            }
        });


    }


    public void OnClickButton(View view){
        Button bt1 = findViewById(R.id.bt1);
        Button bt2 = findViewById(R.id.bt2);
        fTrans = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.bt1:
                fTrans.replace(R.id.frgContainer,fragDr);
                //bt1.getBackground().setColorFilter(new LightingColorFilter(, 0xFFAA0000));
                break;
            case R.id.bt2:
                fTrans.replace(R.id.frgContainer, fragDet);
                //bt2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                break;
            default:
                break;

        }
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    private ArrayList<String> CreateListGenre(){

        ArrayList<String> union = filmList.get(0).getGenres();

        for(Integer i =1; i<filmList.size();i++) {

            union = (ArrayList<String>) Stream.concat(filmList.get(i).getGenres().stream(), union.stream())
                    .distinct()
                    .collect(Collectors.toList());
        }

         return union;
    }

//    private void CreateButtonGenre(){
//
//        relativeLayout =  findViewById(R.id.rltive);
//        Button button = new Button(getApplicationContext());
//        button.setText("комедия");
//        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT
//        );
//        buttonParams.
//        relativeLayout.addView(button);
//
//    }



}
