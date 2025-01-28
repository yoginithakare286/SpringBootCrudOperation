package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.PermissionMaster;
import com.yts.revaux.ntquote.repository.PermissionMasterRepository;
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
 * Spring Data Elasticsearch repository for the {@link PermissionMaster} entity.
 */
public interface PermissionMasterSearchRepository
    extends ElasticsearchRepository<PermissionMaster, Long>, PermissionMasterSearchRepositoryInternal {}

interface PermissionMasterSearchRepositoryInternal {
    Page<PermissionMaster> search(String query, Pageable pageable);

    Page<PermissionMaster> search(Query query);

    @Async
    void index(PermissionMaster entity);

    @Async
    void deleteFromIndexById(Long id);
}

class PermissionMasterSearchRepositoryInternalImpl implements PermissionMasterSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final PermissionMasterRepository repository;

    PermissionMasterSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, PermissionMasterRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<PermissionMaster> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<PermissionMaster> search(Query query) {
        SearchHits<PermissionMaster> searchHits = elasticsearchTemplate.search(query, PermissionMaster.class);
        List<PermissionMaster> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(PermissionMaster entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), PermissionMaster.class);
    }
}
