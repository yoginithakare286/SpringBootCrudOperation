package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationVersionRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuotePartInformationVersion} entity.
 */
public interface NtQuotePartInformationVersionSearchRepository
    extends ElasticsearchRepository<NtQuotePartInformationVersion, Long>, NtQuotePartInformationVersionSearchRepositoryInternal {}

interface NtQuotePartInformationVersionSearchRepositoryInternal {
    Page<NtQuotePartInformationVersion> search(String query, Pageable pageable);

    Page<NtQuotePartInformationVersion> search(Query query);

    @Async
    void index(NtQuotePartInformationVersion entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuotePartInformationVersionSearchRepositoryInternalImpl implements NtQuotePartInformationVersionSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuotePartInformationVersionRepository repository;

    NtQuotePartInformationVersionSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuotePartInformationVersionRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuotePartInformationVersion> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuotePartInformationVersion> search(Query query) {
        SearchHits<NtQuotePartInformationVersion> searchHits = elasticsearchTemplate.search(query, NtQuotePartInformationVersion.class);
        List<NtQuotePartInformationVersion> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuotePartInformationVersion entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuotePartInformationVersion.class);
    }
}
