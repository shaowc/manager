package com.familyan.smarth.manager.mapper.sms;

import com.familyan.smarth.manager.domain.sms.SmsVerifyCodeDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SmsVerifyCodeMapper {

	@Insert("INSERT INTO SMS_VERIFY_CODE(id,mobile,identifier,code,gmt_create,gmt_modify)VALUES(#{id},#{mobile},#{identifier},#{code},#{gmtCreate},#{gmtModify}  )")
	int insert(SmsVerifyCodeDO smsVerifyCodeDO);
	
	@Select("SELECT * FROM SMS_VERIFY_CODE WHERE id=#{id}")
	SmsVerifyCodeDO findById(long id);

	@Select("SELECT * FROM SMS_VERIFY_CODE WHERE identifier=#{identifier}")
	SmsVerifyCodeDO findByIdentifier(@Param("identifier")String identifier);

	@Select("SELECT * FROM SMS_VERIFY_CODE WHERE mobile=#{mobile} AND status=0")
	List<SmsVerifyCodeDO> findByMobile(@Param("mobile")String mobile);

	@Update("UPDATE SMS_VERIFY_CODE set status=1, gmt_modify=now() where id=#{id}")
	void expire(Long id);

	@Update("UPDATE SMS_VERIFY_CODE set status=1, gmt_modify=now() where mobile=#{mobile}")
	void expireByMobile(@Param("mobile")String mobile);

	@Select("SELECT * FROM SMS_VERIFY_CODE WHERE mobile=#{mobile} ORDER BY gmt_create DESC LIMIT 3")
	List<SmsVerifyCodeDO> countSmsNumWithTime(@Param("mobile")String mobile);
}
