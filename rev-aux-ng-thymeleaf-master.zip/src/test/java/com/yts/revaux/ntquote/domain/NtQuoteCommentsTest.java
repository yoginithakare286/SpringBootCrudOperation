package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteCommentsTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCommentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteComments.class);
        NtQuoteComments ntQuoteComments1 = getNtQuoteCommentsSample1();
        NtQuoteComments ntQuoteComments2 = new NtQuoteComments();
        assertThat(ntQuoteComments1).isNotEqualTo(ntQuoteComments2);

        ntQuoteComments2.setId(ntQuoteComments1.getId());
        assertThat(ntQuoteComments1).isEqualTo(ntQuoteComments2);

        ntQuoteComments2 = getNtQuoteCommentsSample2();
        assertThat(ntQuoteComments1).isNotEqualTo(ntQuoteComments2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteComments ntQuoteComments = getNtQuoteCommentsRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteComments.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteComments.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteComments.ntQuote(null);
        assertThat(ntQuoteComments.getNtQuote()).isNull();
    }
}
