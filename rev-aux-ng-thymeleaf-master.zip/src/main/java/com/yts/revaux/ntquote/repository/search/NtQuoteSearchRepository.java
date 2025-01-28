package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.repository.NtQuoteRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuote} entity.
 */
public interface NtQuoteSearchRepository extends ElasticsearchRepository<NtQuote, Long>, NtQuoteSearchRepositoryInternal {}

interface NtQuoteSearchRepositoryInternal {
    Page<NtQuote> search(String query, Pageable pageable);

    Page<NtQuote> search(Query query);

    @Async
    void index(NtQuote entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteSearchRepositoryInternalImpl implements NtQuoteSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteRepository repository;

    NtQuoteSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, NtQuoteRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuote> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuote> search(Query query) {
        SearchHits<NtQuote> searchHits = elasticsearchTemplate.search(query, NtQuote.class);
        List<NtQuote> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuote entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuote.class);
    }
}
