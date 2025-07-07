package com.kumar.springbootlearning.pattern.factory.file;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public abstract class FileHandlerFactory {

    public FileHandler getHandlerByExtension(String extension) {
        switch (extension.toLowerCase()) {
//            case "csv":
//                return createCSVHandler();
            case "xlsx":
                return createExcelHandler();
//            case "json":
//                return createJsonHandler();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + extension);
        }
    }

//    @Lookup
//    protected abstract CSVFileHandlerImpl createCSVHandler();

    @Lookup
    protected abstract ExcelFileHandlerImpl createExcelHandler();

//    @Lookup
//    protected abstract JsonFileHandlerImpl createJsonHandler();
}
