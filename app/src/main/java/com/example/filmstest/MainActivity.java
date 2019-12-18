package com.example.filmstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.filmstest.Fragment.FilmDetail;
import com.example.filmstest.Fragment.FragmentFilm;
import com.example.filmstest.Model.Film;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.filmstest.API.ApiService;
import com.example.filmstest.Model.FilmAdapter;
import com.example.filmstest.Model.FilmsList;
import com.example.filmstest.Network.RetroClient;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Film> filmList;
    private FragmentFilm fragmentFilm;
    private FilmDetail fragmentDetail;
    private FragmentTransaction fTrans;
    private RelativeLayout relativeLayout;
    private int idButton = -1;


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
                    ArrayList<String> listGenre = CreateListGenre();
                    CreateButtonGenre(listGenre);


                    //FilmAdapter fadapter = new FilmAdapter(filmList);

                }
            }

            @Override
            public void onFailure(Call<FilmsList> call, Throwable t) {

                Log.e("error", "shit happens" + t.getMessage());
                t.printStackTrace();

            }
        });




    }


    private ArrayList<String> CreateListGenre() {

        ArrayList<String> union = filmList.get(0).getGenres();

        for (Integer i = 1; i < filmList.size(); i++) {

            union = (ArrayList<String>) Stream.concat(filmList.get(i).getGenres().stream(), union.stream())
                    .distinct()
                    .collect(Collectors.toList());
        }

        return union;
    }

    private void CreateButtonGenre(ArrayList<String> listGenre) {

        RelativeLayout relativeLayout = findViewById(R.id.relative);
        ArrayList<Button> listButton = new ArrayList<>();


        RelativeLayout.LayoutParams buttonParams1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fTrans = fragmentManager.beginTransaction();
                ArrayList<Film> listFilmGenre = new ArrayList<>(filmList);
                String textButtom = ((Button) view).getText().toString();

                if (idButton == -1) {
                    idButton = view.getId();
                } else {
                    Button button = findViewById(idButton);
                    button.getBackground().clearColorFilter();
                    idButton = view.getId();
                }

                //view.setBackground(Drawable.createFromPath("drawable/click.xml"));
                view.getBackground().setColorFilter(new LightingColorFilter(0x696969, 0x696969));
                //view.getBackground().clearColorFilter();
                listFilmGenre.removeIf(s -> !s.getGenres().contains(textButtom.toLowerCase()));
                fragmentFilm = new FragmentFilm(listFilmGenre);
                fTrans.replace(R.id.frgContainer,fragmentFilm);
                fTrans.addToBackStack(null);
                fTrans.commit();


            }
        };

        for (Integer i = 0; i < listGenre.size(); i++) {
            //Button button = new Button(this);
            //MaterialButton button = new MaterialButton( new ContextThemeWrapper( this, R.style.Widget_MaterialComponents_Button_OutlinedButton));
            MaterialButton button = new MaterialButton(this, null, R.attr.borderlessButtonStyle);
            button.setText(listGenre.get(i));
            button.setTextSize(13);
            button.setId(View.generateViewId());
            listButton.add(button);
            button.setOnClickListener(onClickListener);
            //button.setEnabled(false);

        }


        buttonParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        buttonParams1.leftMargin = 5;

        relativeLayout.addView(listButton.get(0), buttonParams1);

        for (Integer i = 1; i < listGenre.size(); i++) {

            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            buttonParams1.leftMargin = 10;

            if (i % 3 == 0) {

                buttonParams.addRule(RelativeLayout.BELOW, listButton.get(i - 3).getId());

            } else {
                buttonParams.addRule(RelativeLayout.RIGHT_OF, listButton.get(i - 1).getId());
                buttonParams.addRule(RelativeLayout.ALIGN_TOP, listButton.get(i - 1).getId());
            }

            relativeLayout.addView(listButton.get(i), buttonParams);
        }


    }

}
