package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerProject;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerProjectRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteCustomerProject} entity.
 */
public interface NtQuoteCustomerProjectSearchRepository
    extends ElasticsearchRepository<NtQuoteCustomerProject, Long>, NtQuoteCustomerProjectSearchRepositoryInternal {}

interface NtQuoteCustomerProjectSearchRepositoryInternal {
    Page<NtQuoteCustomerProject> search(String query, Pageable pageable);

    Page<NtQuoteCustomerProject> search(Query query);

    @Async
    void index(NtQuoteCustomerProject entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteCustomerProjectSearchRepositoryInternalImpl implements NtQuoteCustomerProjectSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteCustomerProjectRepository repository;

    NtQuoteCustomerProjectSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteCustomerProjectRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteCustomerProject> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteCustomerProject> search(Query query) {
        SearchHits<NtQuoteCustomerProject> searchHits = elasticsearchTemplate.search(query, NtQuoteCustomerProject.class);
        List<NtQuoteCustomerProject> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteCustomerProject entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteCustomerProject.class);
    }
}
