package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtProjectApproval;
import com.yts.revaux.ntquote.repository.NtProjectApprovalRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtProjectApproval} entity.
 */
public interface NtProjectApprovalSearchRepository
    extends ElasticsearchRepository<NtProjectApproval, Long>, NtProjectApprovalSearchRepositoryInternal {}

interface NtProjectApprovalSearchRepositoryInternal {
    Page<NtProjectApproval> search(String query, Pageable pageable);

    Page<NtProjectApproval> search(Query query);

    @Async
    void index(NtProjectApproval entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtProjectApprovalSearchRepositoryInternalImpl implements NtProjectApprovalSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtProjectApprovalRepository repository;

    NtProjectApprovalSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, NtProjectApprovalRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtProjectApproval> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtProjectApproval> search(Query query) {
        SearchHits<NtProjectApproval> searchHits = elasticsearchTemplate.search(query, NtProjectApproval.class);
        List<NtProjectApproval> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtProjectApproval entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtProjectApproval.class);
    }
}
