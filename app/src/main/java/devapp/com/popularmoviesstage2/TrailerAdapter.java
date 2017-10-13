package devapp.com.popularmoviesstage2;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context mContext;
    private TrailerAdapter.ItemClickListener itemClickListener;

    TrailerAdapter(Context mContext, ItemClickListener itemClickListener){

        this.mContext = mContext;
        this.itemClickListener = itemClickListener;

    }

    interface ItemClickListener{

        void onItemClick(int position);

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

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView trailerNumberTextView;

        TrailerViewHolder(View itemView) {
            super(itemView);

            trailerNumberTextView = (TextView) itemView.findViewById(R.id.trailer_item_text_view);

            itemView.setOnClickListener(this);

        }

        void bind(int position){
            String trailerDisplay = "Trailer " + String.valueOf(position + 1);
            trailerNumberTextView.setText(trailerDisplay);
        }

        @Override
        public void onClick(View v) {

            itemClickListener.onItemClick(getAdapterPosition());

        }
    }

}
