package com.example.multifuncui.funcsButton.model;


import java.util.ArrayList;
import java.util.List;

public class HomeData {
    List<HomeItemModel> homeItemModels;
    int  columnsSize;
    boolean ai;

    public HomeData(List<HomeItemModel> homeItemModels, int columnsSize,boolean ai) {
        this.homeItemModels = homeItemModels;
        this.columnsSize = columnsSize;
        this.ai=ai;
    }

    public final boolean isAi() {
        return ai;
    }

    public final void setAi(boolean ai) {
        this.ai = ai;
    }

    public HomeData(HomeData data) {
        this.homeItemModels=new ArrayList<>(data.homeItemModels);
        this.columnsSize=data.columnsSize;
        this.ai=data.isAi();
    }


    public final List<HomeItemModel> getHomeItemModels() {
        return homeItemModels;
    }

    public final void setHomeItemModels(List<HomeItemModel> homeItemModels) {
        this.homeItemModels = homeItemModels;
    }

    public final int getColumnsSize() {
        return columnsSize;
    }

    public final void setColumnsSize(int columnsSize) {
        this.columnsSize = columnsSize;
    }
}
