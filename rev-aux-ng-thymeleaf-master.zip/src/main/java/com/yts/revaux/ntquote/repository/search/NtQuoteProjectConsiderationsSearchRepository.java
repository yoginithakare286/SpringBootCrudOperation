package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations;
import com.yts.revaux.ntquote.repository.NtQuoteProjectConsiderationsRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteProjectConsiderations} entity.
 */
public interface NtQuoteProjectConsiderationsSearchRepository
    extends ElasticsearchRepository<NtQuoteProjectConsiderations, Long>, NtQuoteProjectConsiderationsSearchRepositoryInternal {}

interface NtQuoteProjectConsiderationsSearchRepositoryInternal {
    Page<NtQuoteProjectConsiderations> search(String query, Pageable pageable);

    Page<NtQuoteProjectConsiderations> search(Query query);

    @Async
    void index(NtQuoteProjectConsiderations entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteProjectConsiderationsSearchRepositoryInternalImpl implements NtQuoteProjectConsiderationsSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteProjectConsiderationsRepository repository;

    NtQuoteProjectConsiderationsSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteProjectConsiderationsRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteProjectConsiderations> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteProjectConsiderations> search(Query query) {
        SearchHits<NtQuoteProjectConsiderations> searchHits = elasticsearchTemplate.search(query, NtQuoteProjectConsiderations.class);
        List<NtQuoteProjectConsiderations> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteProjectConsiderations entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteProjectConsiderations.class);
    }
}
