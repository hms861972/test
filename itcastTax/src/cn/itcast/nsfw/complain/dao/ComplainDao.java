package cn.itcast.nsfw.complain.dao;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.complain.entity.Complain;

import java.util.List;

public interface ComplainDao extends BaseDao<Complain> {

    List<Object[]> getAnnualStatisticDataByYear(Integer year);
}
