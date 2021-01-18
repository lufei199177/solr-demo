package com.spring.solrdemo.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * @author lufei
 * @date 2020/12/28
 * @desc
 */
@Configuration
@EnableSolrRepositories
public class SolrConfig {

    @Autowired
    private SolrClient solrClient;

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(this.solrClient);
    }
}
