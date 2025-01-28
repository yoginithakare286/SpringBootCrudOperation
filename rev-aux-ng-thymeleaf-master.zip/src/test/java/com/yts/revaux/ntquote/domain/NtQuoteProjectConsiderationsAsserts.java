package com.yts.revaux.ntquote.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class NtQuoteProjectConsiderationsAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteProjectConsiderationsAllPropertiesEquals(
        NtQuoteProjectConsiderations expected,
        NtQuoteProjectConsiderations actual
    ) {
        assertNtQuoteProjectConsiderationsAutoGeneratedPropertiesEquals(expected, actual);
        assertNtQuoteProjectConsiderationsAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteProjectConsiderationsAllUpdatablePropertiesEquals(
        NtQuoteProjectConsiderations expected,
        NtQuoteProjectConsiderations actual
    ) {
        assertNtQuoteProjectConsiderationsUpdatableFieldsEquals(expected, actual);
        assertNtQuoteProjectConsiderationsUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteProjectConsiderationsAutoGeneratedPropertiesEquals(
        NtQuoteProjectConsiderations expected,
        NtQuoteProjectConsiderations actual
    ) {
        assertThat(expected)
            .as("Verify NtQuoteProjectConsiderations auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNtQuoteProjectConsiderationsUpdatableFieldsEquals(
        NtQuoteProjectConsiderations expected,
        NtQuoteProjectConsiderations actual
    ) {
        assertThat(expected)
            .as("Verify NtQuoteProjectConsiderations relevant properties")
            .satisfies(e -> assertThat(e.getSrNo()).as("check srNo").isEqualTo(actual.getSrNo()))
            .satisfies(e -> assertThat(e.getUid()).as("check uid").isEqualTo(actual.getUid()))
            .satisfies(e ->
                assertThat(e.getProjectConsideration()).as("check projectConsideration").isEqualTo(actual.getProjectConsideration())
            )
            .satisfies(e -> assertThat(e.getChoice()).as("check choice").isEqualTo(actual.getChoice()))
            .satisfies(e -> assertThat(e.getComments()).as("check comments").isEqualTo(actual.getComments()))
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
    public static void assertNtQuoteProjectConsiderationsUpdatableRelationshipsEquals(
        NtQuoteProjectConsiderations expected,
        NtQuoteProjectConsiderations actual
    ) {
        assertThat(expected)
            .as("Verify NtQuoteProjectConsiderations relationships")
            .satisfies(e -> assertThat(e.getNtQuote()).as("check ntQuote").isEqualTo(actual.getNtQuote()));
    }
}
