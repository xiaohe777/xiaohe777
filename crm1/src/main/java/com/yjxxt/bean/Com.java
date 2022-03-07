package com.yjxxt.bean;

import java.util.List;

public class Com {
    private Integer id;

    private String comName;

    private Integer comPrice;

    private Integer comHousenum;

    private String comRemark;

    private String imgAddr;

    private String comState;

    //配料
    private List<Asy> asys;

    public Com() {
    }

    public List<Asy> getAsys() {
        return asys;
    }

    public void setAsys(List<Asy> asys) {
        this.asys = asys;
    }

    public String getComState() {
        return comState;
    }

    public void setComState(String comState) {
        this.comState = comState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public Integer getComPrice() {
        return comPrice;
    }

    public void setComPrice(Integer comPrice) {
        this.comPrice = comPrice;
    }

    public Integer getComHousenum() {
        return comHousenum;
    }

    public void setComHousenum(Integer comHousenum) {
        this.comHousenum = comHousenum;
    }

    public String getComRemark() {
        return comRemark;
    }

    public void setComRemark(String comRemark) {
        this.comRemark = comRemark == null ? null : comRemark.trim();
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr == null ? null : imgAddr.trim();
    }

    @Override
    public String toString() {
        return "Com{" +
                "id=" + id +
                ", comName='" + comName + '\'' +
                ", comPrice=" + comPrice +
                ", comHousenum=" + comHousenum +
                ", comRemark='" + comRemark + '\'' +
                ", imgAddr='" + imgAddr + '\'' +
                ", comState='" + comState + '\'' +
                ", asys=" + asys +
                '}';
    }
}