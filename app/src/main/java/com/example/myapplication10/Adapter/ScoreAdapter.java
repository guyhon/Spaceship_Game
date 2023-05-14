package com.example.myapplication10.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication10.Interfaces.CallBack_SendClick;
import com.example.myapplication10.R;
import com.example.myapplication10.object.Score;
import com.example.myapplication10.object.ScoreList;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    //private ArrayList<Score> scores;
    private Context context;

    private ScoreList scores;

    private CallBack_SendClick callBack_SendClick;

    public ScoreAdapter(Context context, ScoreList scores, CallBack_SendClick callBack_SendClick) {

        this.scores = scores;
        this.context = context;
        this.callBack_SendClick = callBack_SendClick;

    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        ScoreViewHolder movieViewHolder = new ScoreViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = getItem(position);
        holder.score_LBL_title.setText(score.getTitle());
        holder.score_LBL_score.setText(score.getScore() + "");
        //ImageLoader.getInstance().loadImage(score.getImage(), holder.score_IMG_poster);
        holder.score_item.setOnClickListener(v -> giveLocation(score.getLongitude(),score.getLatitude()));

        Log.d("TAG", "onBindViewHolder: "+scores.getScores().get(position));
    }

    @Override
    public int getItemCount() {
        return this.scores == null ? 0 : this.scores.getScores().size();
    }

    private Score getItem(int position) {
        return this.scores.getScores().get(position);
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView score_LBL_score;
        private MaterialTextView score_LBL_title;
        private ShapeableImageView score_IMG_poster;
        private RelativeLayout score_item;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score_LBL_score = itemView.findViewById(R.id.score_LBL_score);
            score_LBL_title = itemView.findViewById(R.id.score_LBL_title);
            score_IMG_poster = itemView.findViewById(R.id.score_IMG_poster);
            score_item = itemView.findViewById(R.id.score_item);
        }
    }

    private void giveLocation(double longitude, double latitude) {
        if (callBack_SendClick != null) {
            callBack_SendClick.giveLocation(longitude, latitude);
        }
    }

//    private void sendClicked(int position) {
//        Score score = getItem(position);
//        if(callBack_SendClick != null){
//            callBack_SendClick.giveLocation(score.getLongitude(),score.getLatitude());
//        }
//    }
}
