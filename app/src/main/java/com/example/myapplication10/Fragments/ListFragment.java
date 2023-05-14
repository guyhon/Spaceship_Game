package com.example.myapplication10.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication10.Interfaces.CallBack_SendClick;
import com.example.myapplication10.MySharedPreferences;
import com.example.myapplication10.R;
import com.example.myapplication10.Adapter.ScoreAdapter;
import com.example.myapplication10.object.Score;
import com.example.myapplication10.object.ScoreList;
import com.google.gson.Gson;

public class ListFragment extends Fragment {


    private RecyclerView main_LST_scores;



    private Score score;

    private int points=0;
    private ScoreList scoreList;

    private CallBack_SendClick callBack_SendClick;

    public void setCallBack(CallBack_SendClick callBack_SendClick) {
        this.callBack_SendClick = callBack_SendClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
//        Score score = new Score();
//        score.setScore(points);
//        score.setTitle("Score");
//        String fromSP = MySharedPreferences.getInstance().getString("com.example.myapplication10","");
//        ScoreList scoreListFromJson = new Gson().fromJson(fromSP, ScoreList.class);
//        Log.d("Scores from json:", scoreListFromJson.toString());
//        scoreListFromJson.getScores().add(score);
//        initViews(scoreListFromJson);
//        String json = scoreListFromJson.toString();
//        MySharedPreferences.getInstance().putString("myapplication10", json);


        String fromSP = MySharedPreferences.getInstance().getString("com.example.myapplication10","");
        scoreList = new Gson().fromJson(fromSP, ScoreList.class);
        if(scoreList == null){
            scoreList = new ScoreList();
        }
        //Collections.sort(scoreList.getScores());
        ScoreAdapter scoreAdapter = new ScoreAdapter(view.getContext(), scoreList, callBack_SendClick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setAdapter(scoreAdapter);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        Log.d("TAG","onCreateView: " + scoreList);

//        main_LST_scores.setOnClickListener(v -> {
//            sendClicked();
//        });

        return view;
    }

    public void setPoints(int points){
        this.points = points;
    }

//    private void sendClicked() {
//        if(callBack_SendClick != null){
//            callBack_SendClick.userNameChosen(list_ET_name.getText().toString());
//        }
//    }



//    private void initViews(ScoreList scoreList) {
//        ScoreAdapter scoreAdapter = new ScoreAdapter(scoreList.getScores());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        main_LST_scores.setAdapter(scoreAdapter);
//        main_LST_scores.setLayoutManager(linearLayoutManager);
//    }

    private void findViews(View view) {
        main_LST_scores = view.findViewById(R.id.main_LST_scores);
    }

}
