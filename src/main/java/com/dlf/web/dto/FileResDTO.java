package com.dlf.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FileResDTO implements Serializable {

    private static final long serialVersionUID = -5720465799133949142L;
    private String path;

    private String name;
}
