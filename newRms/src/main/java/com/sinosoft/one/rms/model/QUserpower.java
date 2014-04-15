package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QUserpower is a Querydsl query type for Userpower
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUserpower extends EntityPathBase<UserPower> {

    private static final long serialVersionUID = 213818579;

    public static final QUserpower userpower = new QUserpower("userpower");

    public final ListPath<BusPower, QBuspower> buspowers = this.<BusPower, QBuspower>createList("buspowers", BusPower.class, QBuspower.class);

    public final StringPath comcode = createString("comcode");

    public final ListPath<ExcPower, QExcpower> excpowers = this.<ExcPower, QExcpower>createList("excpowers", ExcPower.class, QExcpower.class);

    public final StringPath flag = createString("flag");

    public final StringPath isvalidate = createString("isvalidate");

    public final StringPath usercode = createString("usercode");

    public final ListPath<UserGroup, QUsergroup> usergroups = this.<UserGroup, QUsergroup>createList("usergroups", UserGroup.class, QUsergroup.class);

    public final StringPath userpowerid = createString("userpowerid");

    public QUserpower(String variable) {
        super(UserPower.class, forVariable(variable));
    }

    public QUserpower(Path<? extends UserPower> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QUserpower(PathMetadata<?> metadata) {
        super(UserPower.class, metadata);
    }

}

