package com.dikong.lightcontroller.entity;

/**
 * 中国行政地区表
 * @author huangwenjun
 * @date 2018年01月20日
 */
public class Cnarea2016 {
    /**
     * 层级
     */
    private Integer level;
    /**
     * 行政代码
     */
    private Long areaCode;
    /**
     * 区号
     */
    private String cityCode;
    /**
     * 名称
     */
    private String name;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 组合名
     */
    private String mergerName;
    /**
     * 拼音
     */
    private String pinyin;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 维度
     */
    private Double lat;

    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public Long getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getMergerName() {
        return mergerName;
    }
    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

}