package com.spring.solrdemo.service;

import com.spring.solrdemo.model.Film;
import com.spring.solrdemo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimplePivotField;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.FacetPivotFieldEntry;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lufei
 * @date 2021/1/4
 * @desc
 */
@Service
public class FilmSolrService {

    private static final String SOLR_COLLECTION_NAME = "films";

    @Autowired
    private SolrTemplate solrTemplate;

    public Page<FacetFieldEntry> facetRangeQuery() {
        Date now = new Date();
        Date startDate = DateUtil.computeByYear(now, -20);
        Criteria criteria = new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD);

        FacetOptions facetOptions = new FacetOptions();
        facetOptions.addFacetByRange(new FacetOptions.FieldWithDateRangeParameters("initial_release_date", startDate, now, "+1YEAR"));
        facetOptions.setFacetMinCount(0);

        SimpleFacetQuery facetQuery = new SimpleFacetQuery();
        facetQuery.addCriteria(criteria);
        facetQuery.setRows(0);
        facetQuery.setFacetOptions(facetOptions);

        FacetPage<Film> facetPage = this.solrTemplate.queryForFacetPage(SOLR_COLLECTION_NAME, facetQuery, Film.class);
        Page<FacetFieldEntry> page = facetPage.getRangeFacetResultPage("initial_release_date");
        return page;
    }

    public List<FacetPivotFieldEntry> facetPivotQuery() {
        Criteria criteria = new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD);

        FacetOptions facetOptions = new FacetOptions();
        facetOptions.addFacetOnPivot("genre_str", "directed_by_str");

        SimpleFacetQuery facetQuery = new SimpleFacetQuery();
        facetQuery.addCriteria(criteria);
        facetQuery.setRows(0);
        facetQuery.setFacetOptions(facetOptions);

        FacetPage<Film> facetPage = this.solrTemplate.queryForFacetPage(SOLR_COLLECTION_NAME, facetQuery, Film.class);

        List<FacetPivotFieldEntry> facetPivotFieldEntries = facetPage.getPivot(new SimplePivotField("genre_str","directed_by_str"));
        return facetPivotFieldEntries;
    }
}
