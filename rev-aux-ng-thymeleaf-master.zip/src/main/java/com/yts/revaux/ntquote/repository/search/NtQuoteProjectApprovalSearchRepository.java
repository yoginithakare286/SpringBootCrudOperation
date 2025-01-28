package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import com.yts.revaux.ntquote.repository.NtQuoteProjectApprovalRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteProjectApproval} entity.
 */
public interface NtQuoteProjectApprovalSearchRepository
    extends ElasticsearchRepository<NtQuoteProjectApproval, Long>, NtQuoteProjectApprovalSearchRepositoryInternal {}

interface NtQuoteProjectApprovalSearchRepositoryInternal {
    Page<NtQuoteProjectApproval> search(String query, Pageable pageable);

    Page<NtQuoteProjectApproval> search(Query query);

    @Async
    void index(NtQuoteProjectApproval entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteProjectApprovalSearchRepositoryInternalImpl implements NtQuoteProjectApprovalSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteProjectApprovalRepository repository;

    NtQuoteProjectApprovalSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteProjectApprovalRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteProjectApproval> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteProjectApproval> search(Query query) {
        SearchHits<NtQuoteProjectApproval> searchHits = elasticsearchTemplate.search(query, NtQuoteProjectApproval.class);
        List<NtQuoteProjectApproval> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteProjectApproval entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteProjectApproval.class);
    }
}
