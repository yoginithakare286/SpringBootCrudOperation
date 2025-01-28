package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.repository.RfqDetailRepository;
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
 * Spring Data Elasticsearch repository for the {@link RfqDetail} entity.
 */
public interface RfqDetailSearchRepository extends ElasticsearchRepository<RfqDetail, Long>, RfqDetailSearchRepositoryInternal {}

interface RfqDetailSearchRepositoryInternal {
    Page<RfqDetail> search(String query, Pageable pageable);

    Page<RfqDetail> search(Query query);

    @Async
    void index(RfqDetail entity);

    @Async
    void deleteFromIndexById(Long id);
}

class RfqDetailSearchRepositoryInternalImpl implements RfqDetailSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final RfqDetailRepository repository;

    RfqDetailSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, RfqDetailRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<RfqDetail> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<RfqDetail> search(Query query) {
        SearchHits<RfqDetail> searchHits = elasticsearchTemplate.search(query, RfqDetail.class);
        List<RfqDetail> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(RfqDetail entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), RfqDetail.class);
    }
}
