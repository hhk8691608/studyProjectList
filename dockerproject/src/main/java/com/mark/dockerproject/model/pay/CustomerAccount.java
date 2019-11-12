package com.mark.dockerproject.model.pay;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class CustomerAccount implements Serializable {

    private int id;

    private String accountId;

    private String accountNo;

    private Date dateTime;

    private BigDecimal currentBalance;

    private Integer version;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("accountId="+accountId==null?"":accountId+", ");
        stringBuffer.append("accountNo="+accountNo==null?"":accountNo+", ");
        stringBuffer.append("currentBalance="+currentBalance==null?"":currentBalance+", ");
        stringBuffer.append("version="+version==null?"":version+", ");
        return stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
    }


}
