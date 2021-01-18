package com.spring.solrdemo.controller;

import com.spring.solrdemo.service.FilmSolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPivotFieldEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lufei
 * @date 2021/1/4
 * @desc
 */
@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmSolrService filmSolrService;

    @GetMapping("/facetRangeQuery")
    public Page<FacetFieldEntry> facetRangeQuery() {
        return this.filmSolrService.facetRangeQuery();
    }

    @GetMapping("/facetPivotQuery")
    public List<FacetPivotFieldEntry> facetPivotQuery() {
        return this.filmSolrService.facetPivotQuery();
    }
}
