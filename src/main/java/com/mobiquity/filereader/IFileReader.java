package com.mobiquity.filereader;

import com.mobiquity.beans.PackageInfo;

import java.io.IOException;
import java.util.List;

public interface IFileReader {

    public List<String> readFile(String filePath) throws IOException;
    public List<PackageInfo> parse(List<String> fileRecords) throws IOException;
}
