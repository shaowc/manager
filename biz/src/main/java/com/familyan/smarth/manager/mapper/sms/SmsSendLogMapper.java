package com.familyan.smarth.manager.mapper.sms;

import com.familyan.smarth.manager.domain.sms.SmsSendLogDO;
import com.familyan.smarth.manager.vo.SmsSendLogVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SmsSendLogMapper {

	@Insert("INSERT INTO SMS_SEND_LOG(id,mobile,content,template_id,send_time,return_value,status,gmt_create,gmt_modified,chanel_provider,chanel_id,chanel_account  )VALUES(#{id},#{mobile},#{content},#{templateId},#{sendTime},#{returnValue},#{status},#{gmtCreate},#{gmtModified},#{chanelProvider},#{chanelId},#{chanelAccount}  )")
	int insert(SmsSendLogDO smsSendLog);
	
	int update(SmsSendLogDO smsSendLog);
	
	@Select("SELECT * FROM SMS_SEND_LOG WHERE id=#{id}")
	SmsSendLogDO findById(long id);

	int countByPage(@Param("params")SmsSendLogVO SmsSendLogVO);

	List<SmsSendLogDO> findByPage(@Param("params")SmsSendLogVO smsSendLogVO, @Param("pageSize")int pageSize, @Param("offset")long offset);
	
}
