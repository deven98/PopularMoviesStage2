package devapp.com.popularmoviesstage2;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class DisplayMoviesAdapter extends RecyclerView.Adapter<DisplayMoviesAdapter.MovieViewHolder> {

    private Context mContext;
    private ItemClickListener itemClickListener;

    public DisplayMoviesAdapter(Context mContext, ItemClickListener itemClickListener){

        this.mContext = mContext;
        this.itemClickListener = itemClickListener;

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.display_movies_item,parent,false));

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return MainActivity.movieNames.size();
    }

    public interface ItemClickListener{

        public void onItemClick(int position);

    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView displayMovieImageView;

        MovieViewHolder(View itemView) {

            super(itemView);

            displayMovieImageView = (ImageView) itemView.findViewById(R.id.display_item_image_view);

            itemView.setOnClickListener(this);

        }

        void bind(int position){

            try{
                Picasso.with(mContext).load(MainActivity.moviePosterLinks.get(position)).into(displayMovieImageView);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onClick(View v) {

            itemClickListener.onItemClick(getAdapterPosition());

        }
    }

}
