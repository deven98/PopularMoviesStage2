package devapp.com.popularmoviesstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    Context mContext;

    TrailerAdapter(Context mContext){

        this.mContext = mContext;

    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.trailer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return DetailActivity.trailerIDs.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{

        TextView trailerNumberTextView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
        }

        void bind(int position){
            trailerNumberTextView.setText("Trailer " + position+1);
        }

    }

}
