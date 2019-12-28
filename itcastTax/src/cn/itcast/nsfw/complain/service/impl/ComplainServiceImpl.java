package cn.itcast.nsfw.complain.service.impl;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.utils.QueryHelper;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {
    private ComplainDao complainDao;
    @Resource
    public void setComplainDao(ComplainDao complainDao){
        setBaseDao(complainDao);
        this.complainDao = complainDao;
    }

    @Override
    public void autoDeal() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置当前时间的日期为1号
        cal.set(Calendar.HOUR_OF_DAY, 0);//设置当前时间的日期为1号,0时
        cal.set(Calendar.MINUTE, 0);//设置当前时间的日期为1号,0分
        cal.set(Calendar.SECOND, 0);//设置当前时间的日期为1号,0秒

        //1、查询本月之前的待受理的投诉列表
        QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
        queryHelper.addCondition("c.state=?", Complain.COMPLAIN_STATE_UNDONE);
        queryHelper.addCondition("c.compTime < ?", cal.getTime());//本月1号0时0分0秒

        List<Complain> list = findObjects(queryHelper);
        if(list != null && list.size() > 0){
            //2、更新投诉信息的状态为 已失效
            for(Complain comp: list){
                comp.setState(Complain.COMPLAIN_STATE_INVALID);
                update(comp);
            }
        }
    }

    @Override
    public List<Map> getAnnualStatisticDataByYear(Integer year) {
        List<Map> resList = new ArrayList<Map>();
        //1、获取统计数据
        List<Object[]> list = complainDao.getAnnualStatisticDataByYear(year);
        if (list!=null&&list.size()>0){
            Calendar cal = Calendar.getInstance();
            int curYear = cal.get(Calendar.YEAR);
            int curMonth = cal.get(Calendar.MONTH)+1;//当前月份
            boolean isCurYear = curYear==year;
            //2.格式化统计结果
            int temMonth = 0;
            Map<String, Object> map = null;
            for (Object[] obj : list){
                temMonth = Integer.valueOf((obj[0])+"");
                map = new HashMap<String, Object>();
                map.put("label", temMonth+ " 月");
                if (isCurYear){
                    //当前年份:如果月份已过的则直接取投诉并且值为空或null时设为零；如果月份未过的全部设为null
                    if (curMonth>=temMonth){
                        map.put("value", obj[1]==null?"0":obj[1]);
                    }else {
                        map.put("value", "");
                    }
                }else {
                    map.put("value", obj[1]==null?"0":obj[1]);
                }
                resList.add(map);
            }

        }
        return resList;
    }
}
