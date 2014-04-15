package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QEmploye is a Querydsl query type for Employe
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QEmploye extends EntityPathBase<Employe> {

    private static final long serialVersionUID = 1195892624;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QEmploye employe = new QEmploye("employe");

    public final StringPath articlecode = createString("articlecode");

    public final QCompany company;

    public final StringPath flag = createString("flag");

    public final StringPath newusercode = createString("newusercode");

    public final DateTimePath<java.util.Date> passwdexpiredate = createDateTime("passwdexpiredate", java.util.Date.class);

    public final DateTimePath<java.util.Date> passwdsetdate = createDateTime("passwdsetdate", java.util.Date.class);

    public final StringPath password = createString("password");

    public final StringPath usercode = createString("usercode");

    public final StringPath username = createString("username");

    public final StringPath validstatus = createString("validstatus");

    public QEmploye(String variable) {
        this(Employe.class, forVariable(variable), INITS);
    }

    public QEmploye(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QEmploye(PathMetadata<?> metadata, PathInits inits) {
        this(Employe.class, metadata, inits);
    }

    public QEmploye(Class<? extends Employe> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

