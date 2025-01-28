package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteProjectApprovalTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NtQuoteProjectApprovalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteProjectApproval.class);
        NtQuoteProjectApproval ntQuoteProjectApproval1 = getNtQuoteProjectApprovalSample1();
        NtQuoteProjectApproval ntQuoteProjectApproval2 = new NtQuoteProjectApproval();
        assertThat(ntQuoteProjectApproval1).isNotEqualTo(ntQuoteProjectApproval2);

        ntQuoteProjectApproval2.setId(ntQuoteProjectApproval1.getId());
        assertThat(ntQuoteProjectApproval1).isEqualTo(ntQuoteProjectApproval2);

        ntQuoteProjectApproval2 = getNtQuoteProjectApprovalSample2();
        assertThat(ntQuoteProjectApproval1).isNotEqualTo(ntQuoteProjectApproval2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteProjectApproval ntQuoteProjectApproval = getNtQuoteProjectApprovalRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteProjectApproval.addNtQuote(ntQuoteBack);
        assertThat(ntQuoteProjectApproval.getNtQuotes()).containsOnly(ntQuoteBack);
        assertThat(ntQuoteBack.getNtQuoteProjectApproval()).isEqualTo(ntQuoteProjectApproval);

        ntQuoteProjectApproval.removeNtQuote(ntQuoteBack);
        assertThat(ntQuoteProjectApproval.getNtQuotes()).doesNotContain(ntQuoteBack);
        assertThat(ntQuoteBack.getNtQuoteProjectApproval()).isNull();

        ntQuoteProjectApproval.ntQuotes(new HashSet<>(Set.of(ntQuoteBack)));
        assertThat(ntQuoteProjectApproval.getNtQuotes()).containsOnly(ntQuoteBack);
        assertThat(ntQuoteBack.getNtQuoteProjectApproval()).isEqualTo(ntQuoteProjectApproval);

        ntQuoteProjectApproval.setNtQuotes(new HashSet<>());
        assertThat(ntQuoteProjectApproval.getNtQuotes()).doesNotContain(ntQuoteBack);
        assertThat(ntQuoteBack.getNtQuoteProjectApproval()).isNull();
    }
}
