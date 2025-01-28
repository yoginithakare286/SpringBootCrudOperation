package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteVendorQuote;
import com.yts.revaux.ntquote.repository.NtQuoteVendorQuoteRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteVendorQuote} entity.
 */
public interface NtQuoteVendorQuoteSearchRepository
    extends ElasticsearchRepository<NtQuoteVendorQuote, Long>, NtQuoteVendorQuoteSearchRepositoryInternal {}

interface NtQuoteVendorQuoteSearchRepositoryInternal {
    Page<NtQuoteVendorQuote> search(String query, Pageable pageable);

    Page<NtQuoteVendorQuote> search(Query query);

    @Async
    void index(NtQuoteVendorQuote entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteVendorQuoteSearchRepositoryInternalImpl implements NtQuoteVendorQuoteSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteVendorQuoteRepository repository;

    NtQuoteVendorQuoteSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, NtQuoteVendorQuoteRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteVendorQuote> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteVendorQuote> search(Query query) {
        SearchHits<NtQuoteVendorQuote> searchHits = elasticsearchTemplate.search(query, NtQuoteVendorQuote.class);
        List<NtQuoteVendorQuote> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteVendorQuote entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteVendorQuote.class);
    }
}
