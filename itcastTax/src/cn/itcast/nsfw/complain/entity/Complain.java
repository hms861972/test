package cn.itcast.nsfw.complain.entity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Complain {

    private String compId;

    private String compCompany;
    private String compName;
    private String compMobile;
    private Boolean isNm;
    private Timestamp compTime;
    private String compTitle;
    private String toCompName;
    private String toCompDept;
    private String compContent;
    private String state;
    private Set complainReplies = new HashSet(0);

    public static String COMPLAIN_STATE_UNDONE = "0";
    public static String COMPLAIN_STATE_DONE = "1";
    public static String COMPLAIN_STATE_INVALID = "2";
    public static Map<String, String> COMPLAIN_STATE_MAP;

    static {
        COMPLAIN_STATE_MAP = new HashMap<String, String>();
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_UNDONE, "待受理");
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_DONE, "已受理");
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_INVALID, "已失效");
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompCompany() {
        return compCompany;
    }

    public void setCompCompany(String compCompany) {
        this.compCompany = compCompany;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompMobile() {
        return compMobile;
    }

    public void setCompMobile(String compMobile) {
        this.compMobile = compMobile;
    }

    public Boolean getIsNm() {
        return isNm;
    }

    public void setIsNm(Boolean nm) {
        isNm = nm;
    }

    public Timestamp getCompTime() {
        return compTime;
    }

    public void setCompTime(Timestamp compTime) {
        this.compTime = compTime;
    }

    public String getCompTitle() {
        return compTitle;
    }

    public void setCompTitle(String compTitle) {
        this.compTitle = compTitle;
    }

    public String getToCompName() {
        return toCompName;
    }

    public void setToCompName(String toCompName) {
        this.toCompName = toCompName;
    }

    public String getToCompDept() {
        return toCompDept;
    }

    public void setToCompDept(String toCompDept) {
        this.toCompDept = toCompDept;
    }

    public String getCompContent() {
        return compContent;
    }

    public void setCompContent(String compContent) {
        this.compContent = compContent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set getComplainReplies() {
        return complainReplies;
    }

    public void setComplainReplies(Set complainReplies) {
        this.complainReplies = complainReplies;
    }
}
