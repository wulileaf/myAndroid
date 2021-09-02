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
 * DAO for table "product".
*/
public class ProductBeanDao extends AbstractDao<ProductBean, Long> {

    public static final String TABLENAME = "product";

    /**
     * Properties of entity ProductBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ServerId = new Property(1, String.class, "serverId", false, "SERVER_ID");
        public final static Property ProductId = new Property(2, String.class, "productId", false, "PRODUCT_ID");
        public final static Property ProductCode = new Property(3, String.class, "productCode", false, "PRODUCT_CODE");
        public final static Property FullName = new Property(4, String.class, "fullName", false, "FULL_NAME");
        public final static Property IsNew = new Property(5, String.class, "isNew", false, "IS_NEW");
        public final static Property BigClass = new Property(6, String.class, "bigClass", false, "BIG_CLASS");
        public final static Property SmallClass = new Property(7, String.class, "smallClass", false, "SMALL_CLASS");
        public final static Property SafeStock = new Property(8, String.class, "safeStock", false, "SAFE_STOCK");
        public final static Property Str1 = new Property(9, String.class, "str1", false, "STR1");
    }


    public ProductBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ProductBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"product\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SERVER_ID\" TEXT," + // 1: serverId
                "\"PRODUCT_ID\" TEXT," + // 2: productId
                "\"PRODUCT_CODE\" TEXT," + // 3: productCode
                "\"FULL_NAME\" TEXT," + // 4: fullName
                "\"IS_NEW\" TEXT," + // 5: isNew
                "\"BIG_CLASS\" TEXT," + // 6: bigClass
                "\"SMALL_CLASS\" TEXT," + // 7: smallClass
                "\"SAFE_STOCK\" TEXT," + // 8: safeStock
                "\"STR1\" TEXT);"); // 9: str1
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"product\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ProductBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(2, serverId);
        }
 
        String productId = entity.getProductId();
        if (productId != null) {
            stmt.bindString(3, productId);
        }
 
        String productCode = entity.getProductCode();
        if (productCode != null) {
            stmt.bindString(4, productCode);
        }
 
        String fullName = entity.getFullName();
        if (fullName != null) {
            stmt.bindString(5, fullName);
        }
 
        String isNew = entity.getIsNew();
        if (isNew != null) {
            stmt.bindString(6, isNew);
        }
 
        String bigClass = entity.getBigClass();
        if (bigClass != null) {
            stmt.bindString(7, bigClass);
        }
 
        String smallClass = entity.getSmallClass();
        if (smallClass != null) {
            stmt.bindString(8, smallClass);
        }
 
        String safeStock = entity.getSafeStock();
        if (safeStock != null) {
            stmt.bindString(9, safeStock);
        }
 
        String str1 = entity.getStr1();
        if (str1 != null) {
            stmt.bindString(10, str1);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ProductBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(2, serverId);
        }
 
        String productId = entity.getProductId();
        if (productId != null) {
            stmt.bindString(3, productId);
        }
 
        String productCode = entity.getProductCode();
        if (productCode != null) {
            stmt.bindString(4, productCode);
        }
 
        String fullName = entity.getFullName();
        if (fullName != null) {
            stmt.bindString(5, fullName);
        }
 
        String isNew = entity.getIsNew();
        if (isNew != null) {
            stmt.bindString(6, isNew);
        }
 
        String bigClass = entity.getBigClass();
        if (bigClass != null) {
            stmt.bindString(7, bigClass);
        }
 
        String smallClass = entity.getSmallClass();
        if (smallClass != null) {
            stmt.bindString(8, smallClass);
        }
 
        String safeStock = entity.getSafeStock();
        if (safeStock != null) {
            stmt.bindString(9, safeStock);
        }
 
        String str1 = entity.getStr1();
        if (str1 != null) {
            stmt.bindString(10, str1);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ProductBean readEntity(Cursor cursor, int offset) {
        ProductBean entity = new ProductBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // serverId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // productId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // productCode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // fullName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // isNew
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // bigClass
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // smallClass
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // safeStock
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // str1
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ProductBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setServerId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setProductId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setProductCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFullName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIsNew(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBigClass(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSmallClass(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSafeStock(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setStr1(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ProductBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ProductBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ProductBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}