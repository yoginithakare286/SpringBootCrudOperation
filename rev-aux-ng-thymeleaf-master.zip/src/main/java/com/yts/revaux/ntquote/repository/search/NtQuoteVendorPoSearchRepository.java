package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteVendorPo;
import com.yts.revaux.ntquote.repository.NtQuoteVendorPoRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteVendorPo} entity.
 */
public interface NtQuoteVendorPoSearchRepository
    extends ElasticsearchRepository<NtQuoteVendorPo, Long>, NtQuoteVendorPoSearchRepositoryInternal {}

interface NtQuoteVendorPoSearchRepositoryInternal {
    Page<NtQuoteVendorPo> search(String query, Pageable pageable);

    Page<NtQuoteVendorPo> search(Query query);

    @Async
    void index(NtQuoteVendorPo entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteVendorPoSearchRepositoryInternalImpl implements NtQuoteVendorPoSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteVendorPoRepository repository;

    NtQuoteVendorPoSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, NtQuoteVendorPoRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteVendorPo> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteVendorPo> search(Query query) {
        SearchHits<NtQuoteVendorPo> searchHits = elasticsearchTemplate.search(query, NtQuoteVendorPo.class);
        List<NtQuoteVendorPo> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteVendorPo entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteVendorPo.class);
    }
}
