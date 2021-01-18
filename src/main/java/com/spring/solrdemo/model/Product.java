package com.spring.solrdemo.model;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * @author lufei
 * @date 2020/12/28
 * @desc
 */
@Data
public class Product {

    @Field
    private Double account;
    @Field
    private Integer boothCount;
    @Field
    private Integer canSellCount;
    @Field
    private Double consume7DayQFB;
    @Field
    private Integer dial7DayCount;
    @Field
    private String dialConsumeAvg;
    @Field
    private Boolean expire;
    @Field
    private Integer expiringSellCount;
    @Field
    private String id;
    @Field
    private String levelName;
    @Field
    private String name;
    @Field
    private Integer openPermissionCount;
    @Field
    private String ownerCuId;
    @Field
    private String photoUrl;
    @Field
    private Integer qBalance;
    @Field
    private Boolean recharge;
    @Field
    private Integer sell7DayCount;
    @Field
    private Integer sellingCount;
    @Field
    private Integer unreadMsgCount;
    @Field
    private String virtualNumber;
    @Field
    private Date date;
}
