package com.yts.revaux.ntquote.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import com.yts.revaux.ntquote.repository.BuyerRfqPricesDetailRepository;
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
 * Spring Data Elasticsearch repository for the {@link BuyerRfqPricesDetail} entity.
 */
public interface BuyerRfqPricesDetailSearchRepository
    extends ElasticsearchRepository<BuyerRfqPricesDetail, Long>, BuyerRfqPricesDetailSearchRepositoryInternal {}

interface BuyerRfqPricesDetailSearchRepositoryInternal {
    Page<BuyerRfqPricesDetail> search(String query, Pageable pageable);

    Page<BuyerRfqPricesDetail> search(Query query);

    @Async
    void index(BuyerRfqPricesDetail entity);

    @Async
    void deleteFromIndexById(Long id);
}

class BuyerRfqPricesDetailSearchRepositoryInternalImpl implements BuyerRfqPricesDetailSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final BuyerRfqPricesDetailRepository repository;

    BuyerRfqPricesDetailSearchRepositoryInternalImpl(
        ElasticsearchTemplate elasticsearchTemplate,
        BuyerRfqPricesDetailRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<BuyerRfqPricesDetail> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<BuyerRfqPricesDetail> search(Query query) {
        SearchHits<BuyerRfqPricesDetail> searchHits = elasticsearchTemplate.search(query, BuyerRfqPricesDetail.class);
        List<BuyerRfqPricesDetail> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(BuyerRfqPricesDetail entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), BuyerRfqPricesDetail.class);
    }
}
