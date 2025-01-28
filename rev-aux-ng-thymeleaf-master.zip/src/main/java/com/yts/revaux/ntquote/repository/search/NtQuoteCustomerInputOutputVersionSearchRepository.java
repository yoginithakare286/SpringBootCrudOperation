package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputVersionRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteCustomerInputOutputVersion} entity.
 */
public interface NtQuoteCustomerInputOutputVersionSearchRepository
    extends ElasticsearchRepository<NtQuoteCustomerInputOutputVersion, Long>, NtQuoteCustomerInputOutputVersionSearchRepositoryInternal {}

interface NtQuoteCustomerInputOutputVersionSearchRepositoryInternal {
    Page<NtQuoteCustomerInputOutputVersion> search(String query, Pageable pageable);

    Page<NtQuoteCustomerInputOutputVersion> search(Query query);

    @Async
    void index(NtQuoteCustomerInputOutputVersion entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteCustomerInputOutputVersionSearchRepositoryInternalImpl implements NtQuoteCustomerInputOutputVersionSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteCustomerInputOutputVersionRepository repository;

    NtQuoteCustomerInputOutputVersionSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteCustomerInputOutputVersionRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteCustomerInputOutputVersion> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteCustomerInputOutputVersion> search(Query query) {
        SearchHits<NtQuoteCustomerInputOutputVersion> searchHits = elasticsearchTemplate.search(
            query,
            NtQuoteCustomerInputOutputVersion.class
        );
        List<NtQuoteCustomerInputOutputVersion> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteCustomerInputOutputVersion entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteCustomerInputOutputVersion.class);
    }
}
