package com.familyan.smarth.manager.mapper;

import com.familyan.smarth.manager.domain.XtXzqhDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Koala on 2015/8/22 0022.
 */
public interface XtXzqhMapper {

    @Select("SELECT * FROM XT_XZQH WHERE code=#{code}")
    XtXzqhDO findByCode(int code);

    @Select("SELECT * FROM XT_XZQH WHERE level=#{level}")
    List<XtXzqhDO> findByLevel(int level);

    @Select("SELECT * FROM XT_XZQH WHERE display_level=#{displayLevel} AND display=1")
    List<XtXzqhDO> findByDisplayLevel(int displayLevel);

    @Select("SELECT * FROM XT_XZQH WHERE parent_code=#{parentCode}")
    List<XtXzqhDO> findByParentCode(int parentCode);

    @Select("SELECT * FROM XT_XZQH WHERE display_parent_code=#{displayParentCode} AND display=1")
    List<XtXzqhDO> findByDisplayParentCode(int displayParentCode);

    /**
     * 用户member表中手机城市数据
     * @return
     */
    @Select("SELECT code,name FROM XT_XZQH WHERE `name` LIKE CONCAT(#{name},'%') AND display_parent_code = #{displayParentCode}")
    List<XtXzqhDO> findCodeByName(Map<String, Object> map);

    @Select("SELECT * FROM XT_XZQH where old_code=#{oldCode}")
    XtXzqhDO findByOldCode(@Param("oldCode")Integer oldCode);

}
