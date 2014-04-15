package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QRoleDesignateId is a Querydsl query type for RoleDesignateId
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QRoleDesignateId extends BeanPath<RoleDesignateId> {

    private static final long serialVersionUID = 541480946;

    public static final QRoleDesignateId roleDesignateId = new QRoleDesignateId("roleDesignateId");

    public final StringPath comcode = createString("comcode");

    public final StringPath roleid = createString("roleid");

    public QRoleDesignateId(String variable) {
        super(RoleDesignateId.class, forVariable(variable));
    }

    public QRoleDesignateId(Path<? extends RoleDesignateId> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QRoleDesignateId(PathMetadata<?> metadata) {
        super(RoleDesignateId.class, metadata);
    }

}

