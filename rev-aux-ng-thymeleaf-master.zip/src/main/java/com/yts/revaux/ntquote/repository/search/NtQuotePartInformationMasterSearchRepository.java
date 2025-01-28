package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationMasterRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuotePartInformationMaster} entity.
 */
public interface NtQuotePartInformationMasterSearchRepository
    extends ElasticsearchRepository<NtQuotePartInformationMaster, Long>, NtQuotePartInformationMasterSearchRepositoryInternal {}

interface NtQuotePartInformationMasterSearchRepositoryInternal {
    Page<NtQuotePartInformationMaster> search(String query, Pageable pageable);

    Page<NtQuotePartInformationMaster> search(Query query);

    @Async
    void index(NtQuotePartInformationMaster entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuotePartInformationMasterSearchRepositoryInternalImpl implements NtQuotePartInformationMasterSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuotePartInformationMasterRepository repository;

    NtQuotePartInformationMasterSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuotePartInformationMasterRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuotePartInformationMaster> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuotePartInformationMaster> search(Query query) {
        SearchHits<NtQuotePartInformationMaster> searchHits = elasticsearchTemplate.search(query, NtQuotePartInformationMaster.class);
        List<NtQuotePartInformationMaster> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuotePartInformationMaster entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuotePartInformationMaster.class);
    }
}
