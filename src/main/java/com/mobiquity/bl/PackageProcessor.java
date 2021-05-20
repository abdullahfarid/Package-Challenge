package com.mobiquity.bl;

import com.mobiquity.beans.ItemInfo;
import com.mobiquity.beans.PackageInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PackageProcessor {

    public PackageInfo process(int weightCapacity, List<ItemInfo> itemInfos) {
        weightCapacity = weightCapacity * 100;
        updateItemWeighIntotWholeNumber(itemInfos);
        itemInfos.sort(Comparator.comparing(ItemInfo::getWeight));

        int[] []  packageSelectionArray = prepare2DArrayForPackaeSelection(weightCapacity, itemInfos);
       return selectPackgeWithMaxCostAndMinWeight(weightCapacity,itemInfos, packageSelectionArray);

    }

    private PackageInfo selectPackgeWithMaxCostAndMinWeight(int weightCapacity, List<ItemInfo> itemInfos, int[][] packageSelectionArray) {
        int weight = weightCapacity;
        int maxEligibleValue = packageSelectionArray[itemInfos.size() ][weight];
        List<ItemInfo> eligibleItems =  new ArrayList<>();

        for(int i = itemInfos.size(); i > 0 && maxEligibleValue > 0; i--){
            if(weight > 0 && packageSelectionArray[i -1][weight] > 0 && maxEligibleValue != packageSelectionArray[i -1][weight]) {
                eligibleItems.add(itemInfos.get(i -1));
                weight -= itemInfos.get(i -1).getWeight();
                maxEligibleValue = itemInfos.get(i -1).getCost();
            }
        }
        eligibleItems.sort(Comparator.comparing(ItemInfo::getIndex));
        return new PackageInfo(weightCapacity,eligibleItems);

    }

    private int[][] prepare2DArrayForPackaeSelection(int weightCapacity, List<ItemInfo> itemInfos) {
        int [][] packageSelectionArray =  new int[itemInfos.size() +1][weightCapacity +1];

        buildArrayValuesBasedOnKnapSackFormula(weightCapacity, itemInfos, packageSelectionArray);
        return packageSelectionArray;

    }

    private void buildArrayValuesBasedOnKnapSackFormula(int weightCapacity,List<ItemInfo> itemInfos, int[][] packageSelectionArray) {

        for(int i = 0; i <= itemInfos.size(); i++) {
            for(int weight= 0; weight <= weightCapacity; weight++) {
                if(i == 0 || weight == 0) {
                    packageSelectionArray[i][weight]=0;
                    continue;
                }

                if(itemInfos.get(i -1).getWeight() > weight) {
                    packageSelectionArray[i][weight] = packageSelectionArray[i-1][weight];
                } else {
                    packageSelectionArray[i][weight] = Math.max(packageSelectionArray[i-1][weight],
                            packageSelectionArray[i-1][weight - itemInfos.get(i -1).getWeight().intValue()] + itemInfos.get(i -1).getCost());
                }

            }
        }

    }


    private void updateItemWeighIntotWholeNumber(List<ItemInfo> itemInfos) {
        itemInfos.stream().forEach(i -> i.setWeight(i.getWeight()  * 100));
    }
}
