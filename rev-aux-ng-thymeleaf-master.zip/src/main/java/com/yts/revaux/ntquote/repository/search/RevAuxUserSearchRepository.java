package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.RevAuxUser;
import com.yts.revaux.ntquote.repository.RevAuxUserRepository;
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
 * Spring Data Elasticsearch repository for the {@link RevAuxUser} entity.
 */
public interface RevAuxUserSearchRepository extends ElasticsearchRepository<RevAuxUser, Long>, RevAuxUserSearchRepositoryInternal {}

interface RevAuxUserSearchRepositoryInternal {
    Page<RevAuxUser> search(String query, Pageable pageable);

    Page<RevAuxUser> search(Query query);

    @Async
    void index(RevAuxUser entity);

    @Async
    void deleteFromIndexById(Long id);
}

class RevAuxUserSearchRepositoryInternalImpl implements RevAuxUserSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final RevAuxUserRepository repository;

    RevAuxUserSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, RevAuxUserRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<RevAuxUser> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<RevAuxUser> search(Query query) {
        SearchHits<RevAuxUser> searchHits = elasticsearchTemplate.search(query, RevAuxUser.class);
        List<RevAuxUser> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(RevAuxUser entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), RevAuxUser.class);
    }
}
