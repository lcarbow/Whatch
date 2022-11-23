package com.example.whatch_moovium;



import java.util.List;

public class LandingPage_Genres_ModelParent {

    // Declaration of the variables
    private String ParentItemTitle;
    private List<Movie> ChildItemList;

    // Constructor of the class
    // to initialize the variables
    public LandingPage_Genres_ModelParent(String ParentItemTitle, List<Movie> ChildItemList) {

        this.ParentItemTitle = ParentItemTitle;
        this.ChildItemList = ChildItemList;
    }

    // Getter and Setter methods
    // for each parameter
    public String getParentItemTitle()
    {
        return ParentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle) {
        ParentItemTitle = parentItemTitle;
    }

    public List<Movie> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(List<Movie> childItemList) {
        ChildItemList = childItemList;
    }
}

