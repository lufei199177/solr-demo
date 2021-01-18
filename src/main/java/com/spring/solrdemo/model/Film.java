package com.spring.solrdemo.model;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
import java.util.List;

/**
 * @author lufei
 * @date 2021/1/4
 * @desc
 */
@Data
public class Film {

    @Field
    private String id;

    @Field("directed_by")
    private List<String> directedBy;

    @Field("initial_release_date")
    private Date initialReleaseDate;

    @Field
    private List<String> genre;

    @Field
    private String name;
}
