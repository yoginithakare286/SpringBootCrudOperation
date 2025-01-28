package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteComments;
import com.yts.revaux.ntquote.repository.NtQuoteCommentsRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteComments} entity.
 */
public interface NtQuoteCommentsSearchRepository
    extends ElasticsearchRepository<NtQuoteComments, Long>, NtQuoteCommentsSearchRepositoryInternal {}

interface NtQuoteCommentsSearchRepositoryInternal {
    Page<NtQuoteComments> search(String query, Pageable pageable);

    Page<NtQuoteComments> search(Query query);

    @Async
    void index(NtQuoteComments entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteCommentsSearchRepositoryInternalImpl implements NtQuoteCommentsSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteCommentsRepository repository;

    NtQuoteCommentsSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, NtQuoteCommentsRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteComments> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteComments> search(Query query) {
        SearchHits<NtQuoteComments> searchHits = elasticsearchTemplate.search(query, NtQuoteComments.class);
        List<NtQuoteComments> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteComments entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteComments.class);
    }
}
