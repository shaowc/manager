package com.familyan.smarth.manager.manager;

import com.familyan.smarth.manager.domain.XtXzqhDO;

import java.util.List;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
public interface XtXzqhManager {

    public XtXzqhDO getByCode(int code);

    public List<XtXzqhDO> getByLevel(int level);

    public List<XtXzqhDO> getByParentCode(int parentCode);

    public List<XtXzqhDO> getByDisplayParentCode(int displayParentCode);

    /**
     * 根据name模糊查询,用户member表
     * @param name
     * @return
     */
    List<XtXzqhDO> findCodeByName(String name,int displayParentCode);

    XtXzqhDO findByOldCode(Integer oldCode);

    List<XtXzqhDO> findByOldCodes(List<Integer> oldCodes);

}
