package com.mobiquity.packer;

import com.mobiquity.beans.PackageInfo;
import com.mobiquity.bl.PackageProcessor;
import com.mobiquity.exception.APIException;
import com.mobiquity.filereader.IFileReader;
import com.mobiquity.filereader.TextFileReader;
import com.mobiquity.utils.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws APIException {
      IFileReader fileReader = new TextFileReader();
      PackageProcessor packageProcessor =  new PackageProcessor();
      List<PackageInfo> finalPackges = new ArrayList<>();
      String finalResults;
      try {
          List<String> filerecords = fileReader.readFile(filePath);
          List<PackageInfo> packageInfos = fileReader.parse(filerecords);

          for(PackageInfo packageInfo: packageInfos) {
              finalPackges.add(packageProcessor.process(packageInfo.getWeightCapicaty(), packageInfo.getItemInfoList() ));
          }
          finalResults = printPackageSting(finalPackges);
          System.out.print(finalResults);
      } catch (IOException e) {
          throw new APIException("IOException",e);
      }
      return finalResults;
  }

    private static String printPackageSting(List<PackageInfo> finalPackges) {
      StringBuilder sb = new StringBuilder();

      for(PackageInfo packageInfo: finalPackges){
          if(CommonUtils.isNotNullorEmptyPackage(packageInfo)) {
              sb.append(packageInfo.getItemInfoList().stream().map(p ->Integer.toString(p.getIndex())).collect(Collectors.joining(",")));
          } else {
              sb.append("-");
          }
          sb.append("\n");

      }
      return  sb.toString();
    }

    public static void main(String[] args) throws APIException {
        pack("C:/Users/MFarid/Desktop/Backend code assignment - Mobiquity 2021/src/main/test/resources/example_input");

    }

}
