package com.example.whatch_moovium.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whatch_moovium.Contract;
import com.example.whatch_moovium.Model.Model;
import com.example.whatch_moovium.Model.StorageClass;
import com.example.whatch_moovium.R;

import java.util.List;

public class LandingPage_Genre_ParentItemAdapter extends RecyclerView.Adapter<LandingPage_Genre_ParentItemAdapter.ParentViewHolder> {

    private final Contract.IGenrePresenter genrePresenter;
    private RecyclerView.RecycledViewPool viewPool;
    private List<Model> itemList;

    LandingPage_Genre_ParentItemAdapter(Contract.IGenrePresenter genrePresenter, List<Model> itemList)
    {
        this.genrePresenter = genrePresenter;
        this.itemList = itemList;
        this.viewPool = new RecyclerView.RecycledViewPool();

    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_titles, viewGroup, false);

        return new ParentViewHolder(view, genrePresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position) {

        Model parentItem = itemList.get(position);

        parentViewHolder.ParentItemTitle.setText(parentItem.getParentItemTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.ChildRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        layoutManager.setInitialPrefetchItemCount(parentItem.getArrayList().size());

        LandingPage_Genre_ChildItemAdapter childItemAdapter = new LandingPage_Genre_ChildItemAdapter(genrePresenter, parentItem.getArrayList());
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView, Contract.IGenrePresenter genrePresenter) {
            super(itemView);


            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            ChildRecyclerView = itemView.findViewById(R.id.child_recyclerview);

            ChildRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    genrePresenter.setMovieVertical(getAdapterPosition());

                    return false;

                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });

        }
    }


}
