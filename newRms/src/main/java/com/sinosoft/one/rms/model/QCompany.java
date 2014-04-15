package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = -524521610;

    public static final QCompany company = new QCompany("company");

    public final StringPath accountant = createString("accountant");

    public final StringPath acntunit = createString("acntunit");

    public final StringPath addresscname = createString("addresscname");

    public final StringPath addressename = createString("addressename");

    public final StringPath articlecode = createString("articlecode");

    public final StringPath comcname = createString("comcname");

    public final StringPath comcode = createString("comcode");

    public final StringPath comename = createString("comename");

    public final StringPath comtype = createString("comtype");

    public final ListPath<Employe, QEmploye> employes = this.<Employe, QEmploye>createList("employes", Employe.class, QEmploye.class);

    public final StringPath faxnumber = createString("faxnumber");

    public final StringPath flag = createString("flag");

    public final StringPath insurername = createString("insurername");

    public final StringPath isinsured = createString("isinsured");

    public final StringPath manager = createString("manager");

    public final StringPath newcomcode = createString("newcomcode");

    public final StringPath phonenumber = createString("phonenumber");

    public final StringPath postcode = createString("postcode");

    public final StringPath remark = createString("remark");

    public final StringPath uppercomcode = createString("uppercomcode");

    public final StringPath validstatus = createString("validstatus");

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QCompany(PathMetadata<?> metadata) {
        super(Company.class, metadata);
    }

}

