package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.VendorProfile;
import com.yts.revaux.ntquote.repository.VendorProfileRepository;
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
 * Spring Data Elasticsearch repository for the {@link VendorProfile} entity.
 */
public interface VendorProfileSearchRepository
    extends ElasticsearchRepository<VendorProfile, Long>, VendorProfileSearchRepositoryInternal {}

interface VendorProfileSearchRepositoryInternal {
    Page<VendorProfile> search(String query, Pageable pageable);

    Page<VendorProfile> search(Query query);

    @Async
    void index(VendorProfile entity);

    @Async
    void deleteFromIndexById(Long id);
}

class VendorProfileSearchRepositoryInternalImpl implements VendorProfileSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final VendorProfileRepository repository;

    VendorProfileSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, VendorProfileRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<VendorProfile> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<VendorProfile> search(Query query) {
        SearchHits<VendorProfile> searchHits = elasticsearchTemplate.search(query, VendorProfile.class);
        List<VendorProfile> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(VendorProfile entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), VendorProfile.class);
    }
}
