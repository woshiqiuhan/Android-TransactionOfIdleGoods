package com.hznu.transactionofidlegoods.domain;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IdleGoods {
    private String goodsId;
    private String userId;
    private String categoryId;
    private String goodsTags;
    private String goodsName;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStatus;
    private Timestamp goodsCreateDate;
    private Integer messageNum;
    private String goodsProvince;
    private User user;
    private String goodsCoverImgDir; //封面图片地址

    private String orderId;

    @Override
    public String toString() {
        return "IdleGoods{" +
                "goodsId='" + goodsId + '\'' +
                ", userId='" + userId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", goodsTags='" + goodsTags + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsStatus=" + goodsStatus +
                ", goodsCreateDate=" + goodsCreateDate +
                ", messageNum=" + messageNum +
                ", goodsProvince='" + goodsProvince + '\'' +
                ", user=" + user +
                ", goodsCoverImgDir='" + goodsCoverImgDir + '\'' +
                ", orderId='" + orderId + '\'' +
                ", isCollected=" + isCollected +
                ", orderStatus=" + orderStatus +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private boolean isCollected; // 在用户登录情况下，当前记录是否被收藏

    private Integer orderStatus; // 在被用户拍下之后，订单处于的状态，在被拍下后才有值

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGoodsCoverImgDir() {
        return goodsCoverImgDir;
    }

    public void setGoodsCoverImgDir(String goodsCoverImgDir) {
        this.goodsCoverImgDir = goodsCoverImgDir;
    }

    public String getGoodsProvince() {
        return goodsProvince;
    }

    public void setGoodsProvince(String goodsProvince) {
        this.goodsProvince = goodsProvince;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsTags() {
        return goodsTags;
    }

    public void setGoodsTags(String goodsTags) {
        this.goodsTags = goodsTags;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public Timestamp getGoodsCreateDate() {
        return goodsCreateDate;
    }

    public void setGoodsCreateDate(Timestamp goodsCreateDate) {
        this.goodsCreateDate = goodsCreateDate;
    }

    public Integer getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(Integer messageNum) {
        this.messageNum = messageNum;
    }

    public IdleGoods(String userId, String categoryId, String goodsTags, String goodsName, String goodsDetail, Double goodsPrice, Integer goodsStatus, String goodsProvince) {
        this.goodsId = createIdleGoodsId();
        this.userId = userId;
        this.categoryId = categoryId;
        this.goodsTags = goodsTags;
        this.goodsName = goodsName;
        this.goodsDetail = goodsDetail;
        this.goodsPrice = goodsPrice;
        this.goodsStatus = goodsStatus;
        this.goodsProvince = goodsProvince;
        int i = this.goodsDetail.indexOf("<img src=") + 10;
        int j = this.goodsDetail.indexOf("\"", i);
        this.goodsCoverImgDir = this.goodsDetail.substring(i, j);
    }

    public IdleGoods() {
    }

    private String createIdleGoodsId() {
        return "ig_" + (new Date()).getTime();
    }

    public static List<IdleGoods> parseToList(String result) {
        List<IdleGoods> res = new ArrayList<>();
        List<Map<Object, Object>> mpLists = (List<Map<Object, Object>>) JSON.parse(result);
        for (Map<Object, Object> mpList : mpLists) {
            res.add(new IdleGoods(mpList));
        }
        return res;
    }

    public IdleGoods(Map<Object, Object> mp) {
        this.goodsId = (String) mp.get("goodsId");
        this.goodsProvince = (String) mp.get("goodsProvince");
        this.goodsStatus = (Integer) mp.get("goodsStatus");
        BigDecimal goodsPrice = (BigDecimal) mp.get("goodsPrice");
        this.goodsPrice = goodsPrice.doubleValue();
        this.isCollected = (Boolean) mp.get("collected");
        this.goodsCoverImgDir = (String) mp.get("goodsCoverImgDir");
        this.goodsName = (String) mp.get("goodsName");
        Map<Object, Object> user = (Map<Object, Object>) mp.get("user");
        Log.d("IdleGoodsInfoList", user.toString());

        this.user = new User();
        this.user.setUserName((String) user.get("userName"));
    }
}
