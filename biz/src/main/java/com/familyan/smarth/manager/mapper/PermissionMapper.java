package com.familyan.smarth.manager.mapper;

import com.familyan.smarth.manager.domain.PermissionDO;
import com.lotus.service.result.Page;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by Admin on 2015/8/18.
 */
public interface PermissionMapper {

    @Insert("INSERT INTO MANAGER_PERMISSION (`code`,`name`,`parent`,`root`,`type`,`show`,`domain`,`url`,`gmt_create`,`gmt_modified`) " +
                            "VALUES(#{code},#{name},#{parent},#{root},#{type},#{show},#{domain},#{url},now(), now())")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false, statement = "SELECT LAST_INSERT_ID()" ,resultType = Long.class)
    Long insert(PermissionDO permission);

    @Select("SELECT * FROM MANAGER_PERMISSION WHERE root = #{rootId}")
    List<PermissionDO> findByRootId(Long rootId);

    @Select("<script>" +
            "SELECT * FROM MANAGER_PERMISSION WHERE root = #{rootId} AND `show` = 1 AND type IN " +
            "<foreach item='item' index='index' collection='types' open='(' separator=',' close=')'>"+
            "   #{item}"+
            "</foreach> "+
            "</script>")
    List<PermissionDO> findByRootIdAndTypes(@Param("rootId")Long rootId,@Param("types")Integer[] types);

    @Select("SELECT * FROM MANAGER_PERMISSION WHERE show = 1 ORDER BY id desc")
    List<PermissionDO> findByDisplay();

    @Select("SELECT * FROM MANAGER_PERMISSION WHERE parent = #{parentId}")
    List<PermissionDO> findByParent(Long parentId);

    @Select("SELECT * FROM MANAGER_PERMISSION ORDER BY gmt_create desc limit #{start} , #{pageSize}")
    List<PermissionDO> findByPage(Page p);

    @Select("SELECT COUNT(*) FROM MANAGER_PERMISSION")
    int countAll();

    @Select("SELECT * FROM MANAGER_PERMISSION WHERE id = #{id}")
    PermissionDO findById(Long id);

    @Select("<script>" +
            "UPDATE MANAGER_PERMISSION <set>" +
            "<if test='code != null'> `code` = #{code},</if>" +
            "<if test='name != null'> `name` = #{name},</if>" +
            "<if test='show != null'> `show` = #{show},</if>" +
            "<if test='root != null'> `root` = #{root},</if>" +
            "<if test='domain != null'> `domain` = #{domain},</if>" +
            "<if test='url != null'> url = #{url}</if>" +
            " </set>" +
            "WHERE id = #{id};"+
            "</script>")
    void update(PermissionDO permission);

    @Delete("DELETE FROM MANAGER_PERMISSION WHERE id = #{id} AND base != 1")
    void delete(Long id);

    @Select("SELECT * FROM MANAGER_PERMISSION")
    List<PermissionDO> findAll();

    @Select("<script>" +
            "SELECT code FROM MANAGER_PERMISSION WHERE  `show` = 1 AND id IN " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"+
            "   #{item}"+
            "</foreach> "+
            "</script>")
    List<String> findPermissionCodeByIds(@Param("ids")Collection<Long> ids);

    @Select("<script>" +
            "SELECT * FROM MANAGER_PERMISSION WHERE  `show` = 1 AND id IN " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"+
            "   #{item}"+
            "</foreach> "+
            "</script>")
    List<PermissionDO> findByIds(@Param("ids") List<Long> ids);
}
