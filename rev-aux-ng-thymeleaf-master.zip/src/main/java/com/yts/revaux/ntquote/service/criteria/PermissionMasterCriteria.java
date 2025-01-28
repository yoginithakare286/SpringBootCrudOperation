package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.PermissionMaster} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.PermissionMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /permission-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PermissionMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter permissionGroup;

    private StringFilter permission;

    private LongFilter revAuxUserId;

    private Boolean distinct;

    public PermissionMasterCriteria() {}

    public PermissionMasterCriteria(PermissionMasterCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.permissionGroup = other.optionalPermissionGroup().map(StringFilter::copy).orElse(null);
        this.permission = other.optionalPermission().map(StringFilter::copy).orElse(null);
        this.revAuxUserId = other.optionalRevAuxUserId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PermissionMasterCriteria copy() {
        return new PermissionMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPermissionGroup() {
        return permissionGroup;
    }

    public Optional<StringFilter> optionalPermissionGroup() {
        return Optional.ofNullable(permissionGroup);
    }

    public StringFilter permissionGroup() {
        if (permissionGroup == null) {
            setPermissionGroup(new StringFilter());
        }
        return permissionGroup;
    }

    public void setPermissionGroup(StringFilter permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public StringFilter getPermission() {
        return permission;
    }

    public Optional<StringFilter> optionalPermission() {
        return Optional.ofNullable(permission);
    }

    public StringFilter permission() {
        if (permission == null) {
            setPermission(new StringFilter());
        }
        return permission;
    }

    public void setPermission(StringFilter permission) {
        this.permission = permission;
    }

    public LongFilter getRevAuxUserId() {
        return revAuxUserId;
    }

    public Optional<LongFilter> optionalRevAuxUserId() {
        return Optional.ofNullable(revAuxUserId);
    }

    public LongFilter revAuxUserId() {
        if (revAuxUserId == null) {
            setRevAuxUserId(new LongFilter());
        }
        return revAuxUserId;
    }

    public void setRevAuxUserId(LongFilter revAuxUserId) {
        this.revAuxUserId = revAuxUserId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PermissionMasterCriteria that = (PermissionMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(permissionGroup, that.permissionGroup) &&
            Objects.equals(permission, that.permission) &&
            Objects.equals(revAuxUserId, that.revAuxUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionGroup, permission, revAuxUserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PermissionMasterCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPermissionGroup().map(f -> "permissionGroup=" + f + ", ").orElse("") +
            optionalPermission().map(f -> "permission=" + f + ", ").orElse("") +
            optionalRevAuxUserId().map(f -> "revAuxUserId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
