package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QRuleBusdatainfo is a Querydsl query type for RuleBusdatainfo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRuleBusdatainfo extends EntityPathBase<BusDataInfo> {

    private static final long serialVersionUID = 1270068021;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QRuleBusdatainfo ruleBusdatainfo = new QRuleBusdatainfo("ruleBusdatainfo");

    public final StringPath busdatacolumn = createString("busdatacolumn");

    public final StringPath busdatainfo = createString("busdatainfo");

    public final StringPath busdatamodel = createString("busdatamodel");

    public final StringPath busdatatable = createString("busdatatable");

    public final QDatarule datarule;

    public QRuleBusdatainfo(String variable) {
        this(BusDataInfo.class, forVariable(variable), INITS);
    }

    public QRuleBusdatainfo(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRuleBusdatainfo(PathMetadata<?> metadata, PathInits inits) {
        this(BusDataInfo.class, metadata, inits);
    }

    public QRuleBusdatainfo(Class<? extends BusDataInfo> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.datarule = inits.isInitialized("datarule") ? new QDatarule(forProperty("datarule")) : null;
    }

}

