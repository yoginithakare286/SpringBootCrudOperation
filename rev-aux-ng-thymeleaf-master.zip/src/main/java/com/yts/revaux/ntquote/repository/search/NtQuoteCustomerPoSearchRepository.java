package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerPo;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerPoRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteCustomerPo} entity.
 */
public interface NtQuoteCustomerPoSearchRepository
    extends ElasticsearchRepository<NtQuoteCustomerPo, Long>, NtQuoteCustomerPoSearchRepositoryInternal {}

interface NtQuoteCustomerPoSearchRepositoryInternal {
    Page<NtQuoteCustomerPo> search(String query, Pageable pageable);

    Page<NtQuoteCustomerPo> search(Query query);

    @Async
    void index(NtQuoteCustomerPo entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteCustomerPoSearchRepositoryInternalImpl implements NtQuoteCustomerPoSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteCustomerPoRepository repository;

    NtQuoteCustomerPoSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, NtQuoteCustomerPoRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteCustomerPo> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteCustomerPo> search(Query query) {
        SearchHits<NtQuoteCustomerPo> searchHits = elasticsearchTemplate.search(query, NtQuoteCustomerPo.class);
        List<NtQuoteCustomerPo> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteCustomerPo entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteCustomerPo.class);
    }
}
