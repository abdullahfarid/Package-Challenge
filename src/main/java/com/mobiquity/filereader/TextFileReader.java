package com.mobiquity.filereader;

import com.mobiquity.beans.ItemInfo;
import com.mobiquity.beans.PackageInfo;
import com.mobiquity.utils.CommonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextFileReader implements IFileReader {

    @Override
    public List<String> readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (Stream<String> fileStream = Files.lines(path)) {
             return fileStream.collect(Collectors.toList());
        }


    }

    @Override
    public List<PackageInfo> parse(List<String> fileRecords) throws IOException {
        List<PackageInfo> packageInfos = new ArrayList<>();
        for (String fileRecord:fileRecords ) {
            packageInfos.add(parseSingleRecord(fileRecord));
        }
        return packageInfos;
    }

    private PackageInfo parseSingleRecord(String fileRecord) {
        int weightCapacity = 0;
        List<ItemInfo> itemInfos = null;
        String[] splitRecordForWeightCapcaity = fileRecord.split(" : ");

        if(splitRecordForWeightCapcaity.length == 2) {
            weightCapacity =  Integer.parseInt(splitRecordForWeightCapcaity[0]);
            String[] itemRecords = splitRecordForWeightCapcaity[1].split(" ");
            itemInfos = parseItemRecords(itemRecords);
        }
        return new PackageInfo(weightCapacity, itemInfos);
    }

    private List<ItemInfo> parseItemRecords(String[] itemRecords) {
        List<ItemInfo> itemInfos = new ArrayList<>();
        if(!CommonUtils.isNullOrEmptyArray(itemRecords)) {
            for (String itemRecord:itemRecords) {
                ItemInfo itemInfo = parseSingleItem(itemRecord);
                if(null != itemInfo) {
                    itemInfos.add(itemInfo);
                }
            }
        }
        return itemInfos;
    }

    private ItemInfo parseSingleItem(String itemRecord) {
        String [] itemDetails = itemRecord.replaceAll("\\(", "").replaceAll("\\)","").split(",");
        ItemInfo itemInfo = null;

        if(itemDetails.length == 3) {
            itemInfo = prepareItemInfoFromDetails(itemDetails);
        }
        return itemInfo;
    }

    private ItemInfo prepareItemInfoFromDetails(String[] itemDetails) {
        return new ItemInfo(Integer.parseInt(itemDetails[0]),
                Double.parseDouble(itemDetails[1]),
                Integer.parseInt(CommonUtils.removeSpecialCharacher(itemDetails[2])));
    }

}
