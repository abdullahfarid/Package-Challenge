package com.mobiquity.beans;

import java.util.List;

public class PackageInfo {
    private int weightCapicaty;
    private List<ItemInfo> itemInfoList;

    public PackageInfo(int weightCapicaty, List<ItemInfo> itemInfoList) {
        this.weightCapicaty = weightCapicaty;
        this.itemInfoList = itemInfoList;
    }

    public int getWeightCapicaty() {
        return weightCapicaty;
    }

    public void setWeightCapicaty(int weightCapicaty) {
        this.weightCapicaty = weightCapicaty;
    }

    public List<ItemInfo> getItemInfoList() {
        return itemInfoList;
    }

    public void setItemInfoList(List<ItemInfo> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }
}
