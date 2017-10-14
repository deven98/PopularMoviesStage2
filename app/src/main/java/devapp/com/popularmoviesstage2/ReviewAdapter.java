package devapp.com.popularmoviesstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;

    ReviewAdapter(Context mContext){

        this.mContext = mContext;

    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(mContext).inflate(R.layout.review_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return DetailActivity.reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView reviewTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            reviewTextView = (TextView) itemView.findViewById(R.id.review_item_text_view);

        }

        void bind(int position){

            reviewTextView.setText(DetailActivity.reviews.get(position));

        }

    }

}
