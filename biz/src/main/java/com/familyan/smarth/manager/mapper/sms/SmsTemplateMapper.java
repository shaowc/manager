package com.familyan.smarth.manager.mapper.sms;

import java.util.List;

import com.familyan.smarth.manager.domain.sms.SmsTemplateDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SmsTemplateMapper {

	@Insert("INSERT INTO SMS_TEMPLATE(id,chanel_id,name,content,status,gmt_create,gmt_modify)VALUES(#{id},#{chanelId},#{name},#{content},#{status},#{gmtCreate},#{gmtModify}  )")
	int insert(SmsTemplateDO smsTemplate);
	
	@Update("UPDATE SMS_TEMPLATE SET id=#{id},chanel_id=#{chanelId},name=#{name},content=#{content}, status=#{status}, gmt_create=#{gmtCreate},gmt_modify=#{gmtModify}  WHERE ID=#{id}")
	int update(SmsTemplateDO smsTemplate);
	
	@Update("UPDATE SMS_TEMPLATE SET status=-1 WHERE id=#{id}")
	int delete(long id);

	@Update("UPDATE SMS_TEMPLATE SET status=#{status}, gmt_modify=now() WHERE id=#{id}")
	int updateStatus(@Param("id")long id, @Param("status")int status);

	@Select("SELECT * FROM SMS_TEMPLATE WHERE id=#{id}")
	SmsTemplateDO findById(long id);
	
	int countByPage(@Param("params")SmsTemplateDO smsTemplateDO);
	
	List<SmsTemplateDO> findByPage(@Param("params")SmsTemplateDO smsTemplateDO, @Param("pageSize")int pageSize, @Param("offset")long offset);
	
}
