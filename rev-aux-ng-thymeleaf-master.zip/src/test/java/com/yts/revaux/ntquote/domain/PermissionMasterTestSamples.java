package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PermissionMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PermissionMaster getPermissionMasterSample1() {
        return new PermissionMaster().id(1L).permissionGroup("permissionGroup1").permission("permission1");
    }

    public static PermissionMaster getPermissionMasterSample2() {
        return new PermissionMaster().id(2L).permissionGroup("permissionGroup2").permission("permission2");
    }

    public static PermissionMaster getPermissionMasterRandomSampleGenerator() {
        return new PermissionMaster()
            .id(longCount.incrementAndGet())
            .permissionGroup(UUID.randomUUID().toString())
            .permission(UUID.randomUUID().toString());
    }
}
