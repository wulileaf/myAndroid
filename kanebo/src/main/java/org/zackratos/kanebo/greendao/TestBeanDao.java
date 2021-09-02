package org.zackratos.kanebo.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "user".
*/
public class TestBeanDao extends AbstractDao<TestBean, Long> {

    public static final String TABLENAME = "user";

    /**
     * Properties of entity TestBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Sex = new Property(2, String.class, "sex", false, "SEX");
        public final static Property Age = new Property(3, int.class, "age", false, "AGE");
        public final static Property Salary = new Property(4, int.class, "salary", false, "SALARY");
    }


    public TestBeanDao(DaoConfig config) {
        super(config);
    }
    
    public TestBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"user\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"SEX\" TEXT," + // 2: sex
                "\"AGE\" INTEGER NOT NULL ," + // 3: age
                "\"SALARY\" INTEGER NOT NULL );"); // 4: salary
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"user\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TestBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
        stmt.bindLong(4, entity.getAge());
        stmt.bindLong(5, entity.getSalary());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TestBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
        stmt.bindLong(4, entity.getAge());
        stmt.bindLong(5, entity.getSalary());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TestBean readEntity(Cursor cursor, int offset) {
        TestBean entity = new TestBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sex
            cursor.getInt(offset + 3), // age
            cursor.getInt(offset + 4) // salary
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TestBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSex(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAge(cursor.getInt(offset + 3));
        entity.setSalary(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TestBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TestBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TestBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
