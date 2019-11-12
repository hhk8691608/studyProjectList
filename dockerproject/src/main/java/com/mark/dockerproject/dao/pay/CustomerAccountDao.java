package com.mark.dockerproject.dao.pay;


import com.mark.dockerproject.model.pay.CustomerAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;

@Mapper
public interface CustomerAccountDao {

    @Select("SELECT id id,account_id accountId,account_no accountNo,current_balance currentBalance,version version FROM customer_account WHERE account_id = #{accountId}")
    CustomerAccount findAccountById(@Param("accountId") String accountId);

//     @Update("update user set like_num =#{likeNum} WHERE id =#{id}")
    @Update("update customer_account set  current_balance = #{newBalance,jdbcType=DECIMAL}, version = #{currentVersion} + 1 where account_id = #{accountId,jdbcType=VARCHAR} and  version =  #{currentVersion,jdbcType=INTEGER} ")
    int updateBalance(@Param("accountId")String accountId, @Param("newBalance")BigDecimal newBalance,@Param("currentVersion")int currentVersion,@Param("currentTime")Date currentTime);


}
