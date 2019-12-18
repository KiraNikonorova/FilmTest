package com.example.filmstest.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.filmstest.Model.Film;
import com.example.filmstest.R;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class FilmDetail extends Fragment {

    private Film filmDetail;

    public FilmDetail(Film filmDetail){
        this.filmDetail = filmDetail;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle SavedInstanceState){

        View view = inflater.inflate(R.layout.fragment_detail, container,false);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView localizedName =  getView().findViewById(R.id.localized_name);
        ImageView imageURL = getView().findViewById(R.id.image_url);
        TextView name = getView().findViewById(R.id.name);
        TextView year = getView().findViewById(R.id.year);
        TextView reting = getView().findViewById(R.id.rating);
        TextView description = getView().findViewById(R.id.description);


        localizedName.setText(filmDetail.getLocalizedName());
        Picasso.get().load(filmDetail.getImageUrl().toString()).placeholder(R.drawable.no_image)
                .error(R.drawable.no_image).into(imageURL);
        name.setText(filmDetail.getName());
        Integer yearInt = filmDetail.getYear();
        year.setText( "Year:  " + String.format("%d",yearInt));
        reting.setText("Rating:  " + filmDetail.getRating().toString());
        description.setText(filmDetail.getDescription());


    }

}
