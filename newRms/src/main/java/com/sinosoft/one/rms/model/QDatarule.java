package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QDatarule is a Querydsl query type for Datarule
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QDatarule extends EntityPathBase<DataRule> {

    private static final long serialVersionUID = -985543219;

    public static final QDatarule datarule = new QDatarule("datarule");

    public final ListPath<BusPower, QBuspower> buspowers = this.<BusPower, QBuspower>createList("buspowers", BusPower.class, QBuspower.class);

    public final StringPath dataruleid = createString("dataruleid");

    public final StringPath des = createString("des");

    public final StringPath isvalidate = createString("isvalidate");

    public final StringPath rule = createString("rule");

    public final ListPath<BusDataInfo, QRuleBusdatainfo> ruleBusdatainfos = this.<BusDataInfo, QRuleBusdatainfo>createList("ruleBusdatainfos", BusDataInfo.class, QRuleBusdatainfo.class);

    public QDatarule(String variable) {
        super(DataRule.class, forVariable(variable));
    }

    public QDatarule(Path<? extends DataRule> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QDatarule(PathMetadata<?> metadata) {
        super(DataRule.class, metadata);
    }

}

