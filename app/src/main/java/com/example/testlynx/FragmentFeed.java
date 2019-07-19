package com.example.testlynx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentFeed extends Fragment {
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public static final String TITLE = "Feed";

    public static FragmentFeed newInstance() {

        return new FragmentFeed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed,container,false);

        rv = (RecyclerView) view.findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(view.getContext());

        rv.setLayoutManager(layoutManager);
        adapter = new FeedCardsAdapter(view.getContext());
        rv.setAdapter(adapter);

        return view;
    }
}
