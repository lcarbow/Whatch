package com.example.whatch_moovium.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private View view;
    private List<Model> itemList;
    LinearLayoutManager layoutManager;



    LandingPage_Genre_ParentItemAdapter(Contract.IGenrePresenter genrePresenter, List<Model> itemList)
    {
        this.genrePresenter = genrePresenter;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_titles, viewGroup, false);

        return new ParentViewHolder(view, genrePresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position) {

        // Create an instance of the ParentItem
        // class for the given position
        Model parentItem = itemList.get(position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        parentViewHolder.ParentItemTitle.setText(parentItem.getParentItemTitle());

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        layoutManager = new LinearLayoutManager(parentViewHolder.ChildRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager.setInitialPrefetchItemCount(parentItem.getArrayList().size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        LandingPage_Genre_ChildItemAdapter childItemAdapter = new LandingPage_Genre_ChildItemAdapter(parentItem.getArrayList());
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    // This method returns the number
    // of items we have added in the
    // ParentItemList i.e. the number
    // of instances we have created
    // of the ParentItemList
    @Override
    public int getItemCount() {

        return itemList.size();
    }


    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    class ParentViewHolder extends RecyclerView.ViewHolder {

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView, Contract.IGenrePresenter genrePresenter) {
            super(itemView);


            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            ChildRecyclerView = itemView.findViewById(R.id.child_recyclerview);

            ChildRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                ////////////////AUSLAGERN IN PRESENTER TODO ////////////////
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    StorageClass.getInstance().setMyModel(StorageClass.getInstance().getMyModelList().get(getAdapterPosition()));

                    return false;

                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
            ////////////////

            ChildRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    //int newPosition = layoutManager.findFirstCompletelyVisibleItemPosition();

                    //Log.i("helperclass", String.valueOf(firstVisiblePosition));

                    if(newState == 0 && StorageClass.getInstance().isReady() == true) {
                        LinearLayoutManager layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());
                        int newPosition = layoutManager.findFirstVisibleItemPosition();
                        StorageClass.getInstance().setHorizontalPosition(newPosition);
                        genrePresenter.loadImages(StorageClass.getInstance().getHorizontalPosition(), 4, StorageClass.getInstance().getVerticalPosition(), 1);

                    }



                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                }
            });

        }
    }


}
