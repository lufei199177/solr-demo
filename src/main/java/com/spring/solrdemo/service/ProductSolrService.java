package com.spring.solrdemo.service;

import com.spring.solrdemo.model.Product;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.FieldStatsResult;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.data.solr.core.query.result.StatsPage;
import org.springframework.data.solr.repository.SolrRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * @author lufei
 * @date 2020/12/28
 * @desc
 */
@Service
public class ProductSolrService implements SolrRepository<Product, String> {

    private static final String SOLR_COLLECTION_NAME = "test";
    private static final String ID_FIELD_NAME = "id";

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public long count() {
        return 0;
    }

    public Optional<Product> findById(String id) {
        return this.solrTemplate.queryForObject(SOLR_COLLECTION_NAME, new SimpleQuery((new Criteria(ID_FIELD_NAME)).is(id)), Product.class);
    }

    @Transactional
    public int save(Product product) {
        UpdateResponse updateResponse = this.solrTemplate.saveBean(SOLR_COLLECTION_NAME, product);
        return updateResponse.getStatus();
    }

    @Transactional
    public void update(PartialUpdate update) {
        solrTemplate.saveBean(SOLR_COLLECTION_NAME, update);
    }

    @Transactional
    public void batchSave(List<Product> products) {
        UpdateResponse updateResponse = this.solrTemplate.saveBeans(SOLR_COLLECTION_NAME, products, Duration.ofSeconds(1));
        //this.solrTemplate.commit(SOLR_COLLECTION_NAME);
    }

    @Transactional
    public void deleteByIds(List<String> ids) {
        this.solrTemplate.deleteByIds(SOLR_COLLECTION_NAME, ids);
    }

    public FacetPage<Product> listFacetPage() {
        FacetQuery query = new SimpleFacetQuery(new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD))
                .setFacetOptions(new FacetOptions().addFacetOnField("name").setFacetLimit(5));

        FacetPage<Product> facetPage = this.solrTemplate.queryForFacetPage(SOLR_COLLECTION_NAME, query, Product.class);
        return facetPage;
    }

    public ScoredPage<Product> listScoredPage() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Query query = new SimpleQuery();
        query.addSort(Sort.by(Sort.Direction.DESC, "id"));

        try {
            Criteria criteria = Criteria.where("boothCount").greaterThanEqual(80)
                    .and("date").between(dateFormat.parse("2020-12-23"), dateFormat.parse("2020-12-26"));
            query.addCriteria(criteria);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Pageable pageable = new SolrPageRequest(0, 5);
        query.setPageRequest(pageable);

        ScoredPage<Product> scoredPage = this.solrTemplate.queryForPage(SOLR_COLLECTION_NAME, query, Product.class);
        return scoredPage;
    }

    public StatsPage<Product> statsPage() {
        // simple field stats
        StatsOptions statsOptions = new StatsOptions().addField("account");

        // query
        SimpleQuery statsQuery = new SimpleQuery("*:*");
        statsQuery.setStatsOptions(statsOptions);
        StatsPage<Product> statsPage = solrTemplate.queryForStatsPage(SOLR_COLLECTION_NAME, statsQuery, Product.class);

        // retrieving stats info
        FieldStatsResult priceStatResult = statsPage.getFieldStatsResult("account");
        Object max = priceStatResult.getMax();
        Long missing = priceStatResult.getMissing();
        Object min = priceStatResult.getMin();
        Object sum = priceStatResult.getSum();
        Long count = priceStatResult.getCount();
        Object mean = priceStatResult.getMean();
        Double st = priceStatResult.getStddev();
        return statsPage;
    }
}
