package com.mark.dockerproject.VO;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserVO implements Serializable {

    private String id;

    private String name;

    private String pwd;


}
