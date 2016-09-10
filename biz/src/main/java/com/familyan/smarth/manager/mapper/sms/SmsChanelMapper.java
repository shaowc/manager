package com.familyan.smarth.manager.mapper.sms;

import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SmsChanelMapper {

	@Insert("INSERT INTO SMS_CHANEL(id,name,chanel_provider,account,password,create_user,modify_user,gmt_create,gmt_modify  )VALUES(#{id},#{name},#{chanelProvider},#{account},#{password},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify}  )")
	int insert(SmsChanelDO smsChanel);
	
	@Update("UPDATE SMS_CHANEL SET id=#{id},name=#{name},chanel_provider=#{chanelProvider},account=#{account},password=#{password},create_user=#{createUser},modify_user=#{modifyUser},gmt_create=#{gmtCreate},gmt_modify=#{gmtModify}  WHERE ID=#{id}")
	int update(SmsChanelDO smsChanel);
	
	@Select("SELECT * FROM SMS_CHANEL WHERE id=#{id}")
	SmsChanelDO findById(long id);

	@Select("SELECT * FROM SMS_CHANEL")
	List<SmsChanelDO> findAll();

	@Select("<script>" +
			"SELECT * FROM SMS_CHANEL WHERE id IN " +
			"<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"+
			"   #{item}"+
			"</foreach> "+
			"</script>")
	List<SmsChanelDO> findByChanelIds(@Param("ids")List<Long> ids);

	@Select("SELECT count(1) FROM SMS_CHANEL")
	int countAll();

	@Select("SELECT * FROM SMS_CHANEL LIMIT #{pageSize} OFFSET #{offset}")
	List<SmsChanelDO> findByPage(@Param("pageSize")long pageSize, @Param("offset")long offset);
	
}
