package com.iashwin28.lco;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SwipeAdapter extends BaseAdapter {

    private ArrayList<String> questions;
    private ArrayList<String> answers;
    public Context context;



    public SwipeAdapter(Context context, ArrayList<String> q, ArrayList<String> a) {
        this.questions = q;
        this.answers = a;
        this.context = context;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public String getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        TextView textViewCard = (TextView) convertView.findViewById(R.id.card_text);
        Log.d("abc","t="+questions.get(position).toString());
        textViewCard.setText(questions.get(position).toString());
        TextView text_answer = (TextView) convertView.findViewById(R.id.card_answer);
        text_answer.setText(answers.get(position).toString());

        return convertView;
    }
}
