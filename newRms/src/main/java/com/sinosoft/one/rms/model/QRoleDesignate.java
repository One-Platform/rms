package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QRoleDesignate is a Querydsl query type for RoleDesignate
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRoleDesignate extends EntityPathBase<RoleDesignate> {

    private static final long serialVersionUID = -2010607497;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QRoleDesignate roleDesignate = new QRoleDesignate("roleDesignate");

    public final DateTimePath<java.util.Date> createtime = createDateTime("createtime", java.util.Date.class);

    public final StringPath createuser = createString("createuser");

    public final StringPath flag = createString("flag");

    public final QRoleDesignateId id;

    public final DateTimePath<java.util.Date> operatetime = createDateTime("operatetime", java.util.Date.class);

    public final StringPath operateuser = createString("operateuser");

    public final QRole role;

    public QRoleDesignate(String variable) {
        this(RoleDesignate.class, forVariable(variable), INITS);
    }

    public QRoleDesignate(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoleDesignate(PathMetadata<?> metadata, PathInits inits) {
        this(RoleDesignate.class, metadata, inits);
    }

    public QRoleDesignate(Class<? extends RoleDesignate> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QRoleDesignateId(forProperty("id")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

