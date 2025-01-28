package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation;
import com.yts.revaux.ntquote.repository.NtQuoteContractReviewInformationRepository;
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
 * Spring Data Elasticsearch repository for the {@link NtQuoteContractReviewInformation} entity.
 */
public interface NtQuoteContractReviewInformationSearchRepository
    extends ElasticsearchRepository<NtQuoteContractReviewInformation, Long>, NtQuoteContractReviewInformationSearchRepositoryInternal {}

interface NtQuoteContractReviewInformationSearchRepositoryInternal {
    Page<NtQuoteContractReviewInformation> search(String query, Pageable pageable);

    Page<NtQuoteContractReviewInformation> search(Query query);

    @Async
    void index(NtQuoteContractReviewInformation entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NtQuoteContractReviewInformationSearchRepositoryInternalImpl implements NtQuoteContractReviewInformationSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NtQuoteContractReviewInformationRepository repository;

    NtQuoteContractReviewInformationSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        NtQuoteContractReviewInformationRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<NtQuoteContractReviewInformation> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<NtQuoteContractReviewInformation> search(Query query) {
        SearchHits<NtQuoteContractReviewInformation> searchHits = elasticsearchTemplate.search(
            query,
            NtQuoteContractReviewInformation.class
        );
        List<NtQuoteContractReviewInformation> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(NtQuoteContractReviewInformation entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NtQuoteContractReviewInformation.class);
    }
}
