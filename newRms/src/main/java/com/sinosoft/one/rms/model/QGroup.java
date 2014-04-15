package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = 1902678968;

    public static final QGroup group = new QGroup("group1");

    public final StringPath comcode = createString("comcode");

    public final DateTimePath<java.util.Date> createtime = createDateTime("createtime", java.util.Date.class);

    public final StringPath createuser = createString("createuser");

    public final StringPath des = createString("des");

    public final StringPath flag = createString("flag");

    public final StringPath groupid = createString("groupid");

    public final ListPath<GroupRole, QGrouprole> grouproles = this.<GroupRole, QGrouprole>createList("grouproles", GroupRole.class, QGrouprole.class);

    public final StringPath isvalidate = createString("isvalidate");

    public final StringPath name = createString("name");

    public final DateTimePath<java.util.Date> operatetime = createDateTime("operatetime", java.util.Date.class);

    public final StringPath operateuser = createString("operateuser");

    public final ListPath<UserGroup, QUsergroup> usergroups = this.<UserGroup, QUsergroup>createList("usergroups", UserGroup.class, QUsergroup.class);

    public QGroup(String variable) {
        super(Group.class, forVariable(variable));
    }

    public QGroup(Path<? extends Group> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QGroup(PathMetadata<?> metadata) {
        super(Group.class, metadata);
    }

}

