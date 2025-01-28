package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.RevAuxUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RevAuxUser entity.
 */
@Repository
public interface RevAuxUserRepository extends JpaRepository<RevAuxUser, Long>, JpaSpecificationExecutor<RevAuxUser> {
    default Optional<RevAuxUser> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<RevAuxUser> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<RevAuxUser> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select revAuxUser from RevAuxUser revAuxUser left join fetch revAuxUser.internalUser",
        countQuery = "select count(revAuxUser) from RevAuxUser revAuxUser"
    )
    Page<RevAuxUser> findAllWithToOneRelationships(Pageable pageable);

    @Query("select revAuxUser from RevAuxUser revAuxUser left join fetch revAuxUser.internalUser")
    List<RevAuxUser> findAllWithToOneRelationships();

    @Query("select revAuxUser from RevAuxUser revAuxUser left join fetch revAuxUser.internalUser where revAuxUser.id =:id")
    Optional<RevAuxUser> findOneWithToOneRelationships(@Param("id") Long id);
}
