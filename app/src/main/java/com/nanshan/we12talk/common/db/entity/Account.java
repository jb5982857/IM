package com.nanshan.we12talk.common.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.nanshan.we12talk.common.db.DaoSession;
import com.nanshan.we12talk.common.db.AccountDao;

import java.util.Date;


/**
 * Created by jiangbo on 2018/9/17.
 * 用户类
 * schema：告知GreenDao当前实体属于哪个 schema
 * schema active：标记一个实体处于活跃状态，活动实体有更新、删除和刷新方法
 * nameInDb：在数据库中使用的别名，默认使用的是实体的类名，
 * indexes：定义索引，可以跨越多个列
 * createInDb：标记创建数据库表（默认：true）
 * generateConstructors 自动创建全参构造方法（同时会生成一个无参构造方法）（默认：true）
 * generateGettersSetters 自动生成 getters and setters 方法（默认：true）
 */

@Entity(
        active = true,
        nameInDb = "account",
        generateConstructors = false
)
public class Account {
    @Id
    private Long id;
    @Unique
    //如果不写这个属性，在db字段会被大写
    @Property(nameInDb = "username")
    private String username;
    //用于快速登录
    @Property(nameInDb = "token")
    private String token;
    //0:非手机号    1:手机号
    @NotNull
    @Property(nameInDb = "phone")
    private boolean phone;
    //预留字段
    @Property(nameInDb = "session")
    private String session;

    @Property(nameInDb = "update_time")
    private Date updateTime;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 335469827)
    private transient AccountDao myDao;

    public Account() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getPhone() {
        return this.phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public String getSession() {
        return this.session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1812283172)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAccountDao() : null;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
