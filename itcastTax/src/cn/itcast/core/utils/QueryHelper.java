package cn.itcast.core.utils;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {

    //form 子句
    private String fromClause="";
    //where子句
    private String whereClause="";
    //order by 子句
    private String orderByClause="";

    //排序顺序 降序
    public static String  ORDER_BY_DESC = "DESC";
    //排序顺序 升序
    public static String ORDER_BY_ASC = "ASC";


    private List<Object> parameters;

    /**
     * 构造from子句
     * @param clzz  实体类
     * @param alias 实体类对应的别名
     */
    public QueryHelper(Class clzz,String alias){
        fromClause = "from "+ clzz.getSimpleName()+ " "+alias+" ";
    }

    /**
     * 构造where子句
     * @param condition 查询条件语句;例如:i.title like ?
     * @param params
     */
    public void addCondition(String condition,Object... params){

        if (whereClause.length()>1){
            whereClause += " and " + condition + " ";
        }else {
            whereClause += " where "+condition +" ";
        }
        if (parameters==null){
            parameters = new ArrayList<>();
        }
        if (params!=null){
            for (Object param : params){
                parameters.add(param);
            }
        }
    }

    /**
     * 构造orderBy子句
     * @param property 排序属性
     * @param order     排序顺序  desc 或dsa
     */
    public void addOrderByProperty(String property,String order){
        if (orderByClause.length()>1){
            orderByClause += ","+property +" "+order +" ";
        }else {
            orderByClause += " order by "+property + " " + order +" ";
        }
    }

    //查询hql语句
    public String getQueryListHql(){
        System.out.println("whereClause="+whereClause);
        return fromClause+whereClause+orderByClause;
    }

    //查询统计数的hql语句
    public String getQueryCountHql(){
        return "SELECT COUNT(*) " + fromClause + whereClause;
    }

    //查询hql语句中?对应的查询条件值集合
    public List<Object> getParameters(){
        return parameters;
    }



}
