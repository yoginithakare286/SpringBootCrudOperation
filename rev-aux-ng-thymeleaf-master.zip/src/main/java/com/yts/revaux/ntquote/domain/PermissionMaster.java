package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PermissionMaster.
 */
@Entity
@Table(name = "permission_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "permissionmaster")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PermissionMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "permission_group")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String permissionGroup;

    @Column(name = "permission")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "internalUser", "permissions" }, allowSetters = true)
    private RevAuxUser revAuxUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PermissionMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionGroup() {
        return this.permissionGroup;
    }

    public PermissionMaster permissionGroup(String permissionGroup) {
        this.setPermissionGroup(permissionGroup);
        return this;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public String getPermission() {
        return this.permission;
    }

    public PermissionMaster permission(String permission) {
        this.setPermission(permission);
        return this;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public RevAuxUser getRevAuxUser() {
        return this.revAuxUser;
    }

    public void setRevAuxUser(RevAuxUser revAuxUser) {
        this.revAuxUser = revAuxUser;
    }

    public PermissionMaster revAuxUser(RevAuxUser revAuxUser) {
        this.setRevAuxUser(revAuxUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PermissionMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((PermissionMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PermissionMaster{" +
            "id=" + getId() +
            ", permissionGroup='" + getPermissionGroup() + "'" +
            ", permission='" + getPermission() + "'" +
            "}";
    }
}
