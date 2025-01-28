package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteComponentDetail;
import com.yts.revaux.ntquote.repository.NtQuoteComponentDetailRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteComponentDetail} entity.
 */
public interface NtQuoteComponentDetailSearchRepository
    extends ElasticsearchRepository<NtQuoteComponentDetail, Long>, NtQuoteComponentDetailSearchRepositoryInternal {}

interface NtQuoteComponentDetailSearchRepositoryInternal {
    Page<NtQuoteComponentDetail> search(String query, Pageable pageable);

    Page<NtQuoteComponentDetail> search(Query query);

    @Async
    void index(NtQuoteComponentDetail entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteComponentDetailSearchRepositoryInternalImpl implements NtQuoteComponentDetailSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteComponentDetailRepository repository;

    NtQuoteComponentDetailSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteComponentDetailRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteComponentDetail> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteComponentDetail> search(Query query) {
        SearchHits<NtQuoteComponentDetail> searchHits = elasticsearchTemplate.search(query, NtQuoteComponentDetail.class);
        List<NtQuoteComponentDetail> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteComponentDetail entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteComponentDetail.class);
    }
}
