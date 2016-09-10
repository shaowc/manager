package com.familyan.smarth.manager.mapper.sms;

import java.util.List;

import com.familyan.smarth.manager.domain.sms.SmsBlackListDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SmsBlackListMapper {

	@Insert("INSERT INTO SMS_BLACK_LIST(id,mobile,reason,status,gmt_create,gmt_modified  )VALUES(#{id},#{mobile},#{reason},#{status},#{gmtCreate},#{gmtModified}  )")
	int insert(SmsBlackListDO smsBlackList);
	
	@Update("UPDATE SMS_BLACK_LIST SET id=#{id},mobile=#{mobile},reason=#{reason},status=#{status},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified}  WHERE ID=#{id}")
	int update(SmsBlackListDO smsBlackList);
	
	@Update("UPDATE SMS_BLACK_LIST SET status=-1 WHERE id=#{id}")
	int delete(long id);
	
	@Select("SELECT * FROM SMS_BLACK_LIST WHERE id=#{id}")
	SmsBlackListDO findById(long id);
	
	@Select("SELECT * FROM SMS_BLACK_LIST WHERE mobile=#{mobile} AND status=0")
	SmsBlackListDO findByMobile(@Param("mobile")String mobile);
	
	@Select("SELECT * FROM SMS_BLACK_LIST WHERE mobile=#{mobile} AND status=#{status}")
	List<SmsBlackListDO> findHisByMobile(@Param("mobile")String mobile, @Param("status")int status);
	
	@Select("SELECT * FROM SMS_BLACK_LIST WHERE mobile=#{mobile}")
	List<SmsBlackListDO> findAllByMobile(String mobile);
	
	int countByPage(@Param("params")SmsBlackListDO smsBlackListDO);
	
	List<SmsBlackListDO> findByPage(@Param("params")SmsBlackListDO smsBlackListDO, @Param("pageSize")int pageSize, @Param("offset")long offset);
	
}
