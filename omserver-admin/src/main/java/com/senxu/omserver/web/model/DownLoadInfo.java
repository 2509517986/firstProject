package com.senxu.omserver.web.model;

/**
 * @auther yudarr
 * @date 2022-02-14 11:25
 **/

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@Data
public class DownLoadInfo {
    private String fileName;
}
