package com.yts.revaux.ntquote.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class NtQuoteVendorQuoteAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteVendorQuoteAllPropertiesEquals(NtQuoteVendorQuote expected, NtQuoteVendorQuote actual) {
        assertNtQuoteVendorQuoteAutoGeneratedPropertiesEquals(expected, actual);
        assertNtQuoteVendorQuoteAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteVendorQuoteAllUpdatablePropertiesEquals(NtQuoteVendorQuote expected, NtQuoteVendorQuote actual) {
        assertNtQuoteVendorQuoteUpdatableFieldsEquals(expected, actual);
        assertNtQuoteVendorQuoteUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteVendorQuoteAutoGeneratedPropertiesEquals(NtQuoteVendorQuote expected, NtQuoteVendorQuote actual) {
        assertThat(expected)
            .as("Verify NtQuoteVendorQuote auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteVendorQuoteUpdatableFieldsEquals(NtQuoteVendorQuote expected, NtQuoteVendorQuote actual) {
        assertThat(expected)
            .as("Verify NtQuoteVendorQuote relevant properties")
            .satisfies(e -> assertThat(e.getSrNo()).as("check srNo").isEqualTo(actual.getSrNo()))
            .satisfies(e -> assertThat(e.getUid()).as("check uid").isEqualTo(actual.getUid()))
            .satisfies(e -> assertThat(e.getVendorName()).as("check vendorName").isEqualTo(actual.getVendorName()))
            .satisfies(e -> assertThat(e.getQuoteDate()).as("check quoteDate").isEqualTo(actual.getQuoteDate()))
            .satisfies(e -> assertThat(e.getFileName()).as("check fileName").isEqualTo(actual.getFileName()))
            .satisfies(e -> assertThat(e.getCountry()).as("check country").isEqualTo(actual.getCountry()))
            .satisfies(e -> assertThat(e.getBrowse()).as("check browse").isEqualTo(actual.getBrowse()))
            .satisfies(e -> assertThat(e.getCreatedBy()).as("check createdBy").isEqualTo(actual.getCreatedBy()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()))
            .satisfies(e -> assertThat(e.getUpdatedBy()).as("check updatedBy").isEqualTo(actual.getUpdatedBy()))
            .satisfies(e -> assertThat(e.getUpdatedDate()).as("check updatedDate").isEqualTo(actual.getUpdatedDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteVendorQuoteUpdatableRelationshipsEquals(NtQuoteVendorQuote expected, NtQuoteVendorQuote actual) {
        assertThat(expected)
            .as("Verify NtQuoteVendorQuote relationships")
            .satisfies(e -> assertThat(e.getNtQuote()).as("check ntQuote").isEqualTo(actual.getNtQuote()));
    }
}
