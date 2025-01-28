package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteTermsConditions;
import com.yts.revaux.ntquote.repository.NtQuoteTermsConditionsRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link NtQuoteTermsConditions} entity.
 */
public interface NtQuoteTermsConditionsSearchRepository
    extends ElasticsearchRepository<NtQuoteTermsConditions, Long>, NtQuoteTermsConditionsSearchRepositoryInternal {}

interface NtQuoteTermsConditionsSearchRepositoryInternal {
    Page<NtQuoteTermsConditions> search(String query, Pageable pageable);

    Page<NtQuoteTermsConditions> search(Query query);

    @Async
    void index(NtQuoteTermsConditions entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteTermsConditionsSearchRepositoryInternalImpl implements NtQuoteTermsConditionsSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteTermsConditionsRepository repository;

    NtQuoteTermsConditionsSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteTermsConditionsRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteTermsConditions> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteTermsConditions> search(Query query) {
        SearchHits<NtQuoteTermsConditions> searchHits = elasticsearchTemplate.search(query, NtQuoteTermsConditions.class);
        List<NtQuoteTermsConditions> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteTermsConditions entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteTermsConditions.class);
    }
}
