package com.example.filmstest.Model;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmstest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.CustomViewHolder>{

    private ArrayList<Film> adapterListFilm;
    private Listener listener;

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public CustomViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    public interface Listener {
        void onClick(int position);
    }

    //регистрация в качесиве слушателя
    public void setListener (Listener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image,parent,false);
        return new CustomViewHolder(cv);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.image_film);
        Object imageURL = adapterListFilm.get(position).getImageUrl();

        if(imageURL != null) {
            Picasso.get().load(imageURL.toString()).placeholder(R.drawable.no_image)

                    .error(R.drawable.no_image).into(imageView);
        }
        else {imageView.setImageResource(R.drawable.no_image); }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(position);

                }
            }
        });



    }


    public FilmAdapter(ArrayList<Film> listFilm){
        this.adapterListFilm = listFilm;
    }


    public int getItemCount() {
        return adapterListFilm.size();
    }

}
