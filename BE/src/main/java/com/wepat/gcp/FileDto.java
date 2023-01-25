package com.wepat.gcp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDto {

    private String fileName;
    private String fileUrl;
}