package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = -1323771875;

    public static final QRole role = new QRole("role");

    public final StringPath comcode = createString("comcode");

    public final DateTimePath<java.util.Date> createtime = createDateTime("createtime", java.util.Date.class);

    public final StringPath createuser = createString("createuser");

    public final StringPath des = createString("des");

    public final StringPath flag = createString("flag");

    public final ListPath<GroupRole, QGrouprole> grouproles = this.<GroupRole, QGrouprole>createList("grouproles", GroupRole.class, QGrouprole.class);

    public final StringPath isvalidate = createString("isvalidate");

    public final StringPath name = createString("name");

    public final DateTimePath<java.util.Date> operatetime = createDateTime("operatetime", java.util.Date.class);

    public final StringPath operateuser = createString("operateuser");

    public final ListPath<RoleDesignate, QRoleDesignate> roleDesignates = this.<RoleDesignate, QRoleDesignate>createList("roleDesignates", RoleDesignate.class, QRoleDesignate.class);

    public final StringPath roleid = createString("roleid");

    public final ListPath<RoleTask, QRoletask> roletasks = this.<RoleTask, QRoletask>createList("roletasks", RoleTask.class, QRoletask.class);

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QRole(PathMetadata<?> metadata) {
        super(Role.class, metadata);
    }

}

