package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerInputOutputMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerInputOutputMaster.class);
        NtQuoteCustomerInputOutputMaster ntQuoteCustomerInputOutputMaster1 = getNtQuoteCustomerInputOutputMasterSample1();
        NtQuoteCustomerInputOutputMaster ntQuoteCustomerInputOutputMaster2 = new NtQuoteCustomerInputOutputMaster();
        assertThat(ntQuoteCustomerInputOutputMaster1).isNotEqualTo(ntQuoteCustomerInputOutputMaster2);

        ntQuoteCustomerInputOutputMaster2.setId(ntQuoteCustomerInputOutputMaster1.getId());
        assertThat(ntQuoteCustomerInputOutputMaster1).isEqualTo(ntQuoteCustomerInputOutputMaster2);

        ntQuoteCustomerInputOutputMaster2 = getNtQuoteCustomerInputOutputMasterSample2();
        assertThat(ntQuoteCustomerInputOutputMaster1).isNotEqualTo(ntQuoteCustomerInputOutputMaster2);
    }
}
