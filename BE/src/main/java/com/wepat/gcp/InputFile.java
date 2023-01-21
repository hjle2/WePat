package com.wepat.gcp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputFile {

    private Long id;
    private String fileName;
    private String fileUrl;

    public InputFile(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}