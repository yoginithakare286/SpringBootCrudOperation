package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMaster;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputMasterRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteCustomerInputOutputMaster} entity.
 */
public interface NtQuoteCustomerInputOutputMasterSearchRepository
    extends ElasticsearchRepository<NtQuoteCustomerInputOutputMaster, Long>, NtQuoteCustomerInputOutputMasterSearchRepositoryInternal {}

interface NtQuoteCustomerInputOutputMasterSearchRepositoryInternal {
    Page<NtQuoteCustomerInputOutputMaster> search(String query, Pageable pageable);

    Page<NtQuoteCustomerInputOutputMaster> search(Query query);

    @Async
    void index(NtQuoteCustomerInputOutputMaster entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteCustomerInputOutputMasterSearchRepositoryInternalImpl implements NtQuoteCustomerInputOutputMasterSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteCustomerInputOutputMasterRepository repository;

    NtQuoteCustomerInputOutputMasterSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteCustomerInputOutputMasterRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteCustomerInputOutputMaster> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteCustomerInputOutputMaster> search(Query query) {
        SearchHits<NtQuoteCustomerInputOutputMaster> searchHits = elasticsearchTemplate.search(
            query,
            NtQuoteCustomerInputOutputMaster.class
        );
        List<NtQuoteCustomerInputOutputMaster> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteCustomerInputOutputMaster entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteCustomerInputOutputMaster.class);
    }
}
