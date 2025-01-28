package com.yts.revaux.ntquote.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.PermissionMaster} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PermissionMasterDTO implements Serializable {

    private Long id;

    private String permissionGroup;

    private String permission;

    private RevAuxUserDTO revAuxUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public RevAuxUserDTO getRevAuxUser() {
        return revAuxUser;
    }

    public void setRevAuxUser(RevAuxUserDTO revAuxUser) {
        this.revAuxUser = revAuxUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PermissionMasterDTO)) {
            return false;
        }

        PermissionMasterDTO permissionMasterDTO = (PermissionMasterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, permissionMasterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PermissionMasterDTO{" +
            "id=" + getId() +
            ", permissionGroup='" + getPermissionGroup() + "'" +
            ", permission='" + getPermission() + "'" +
            ", revAuxUser=" + getRevAuxUser() +
            "}";
    }
}
