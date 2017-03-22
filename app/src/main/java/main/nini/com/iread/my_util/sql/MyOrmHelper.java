package main.nini.com.iread.my_util.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zyf on 2017/3/12.
 */

/***
 * 数据库助手类，用于管理数据库的创建和升级。<br />这个类通常也提供其他类使用的DAO。
 */
public class MyOrmHelper extends OrmLiteSqliteOpenHelper {

    public static MyOrmHelper instance;

    public static MyOrmHelper getInstance(Context context){
        if(instance == null){
            synchronized (MyOrmHelper.class){
                if (instance == null){
                    instance = new MyOrmHelper(context);
                }
            }
        }

        return instance;
    }


    /**
     * 数据库名字
     */
    private static final String DB_NAME = "native.db";

    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;

    /**
     * 操作表Account的DAO对象<br />
     * 泛型中添加的是表的类型，ID列的类型。<br />
     * 如果没有指定ID列，第二个参数可以填Object或Void
     */
    private Dao<Account, String> accountDao;

    private Dao<NativeBookTable, Integer> bookDao;

    /**
     * 我们只有一个context对象是需要自己填入的。<br />
     * 剩下的不是已经设置好的，就是可以使用默认的。<br />
     * 所以我们再定义一个构造方法。
     *
     * @param context
     */
    private MyOrmHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 有了上面那个，这个就可以弃用了，注掉或删掉都行。
     *
     * @param context
     * @param databaseName
     * @param factory
     * @param databaseVersion
     */
    @Deprecated
    public MyOrmHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    /**
     * 第一次创建时调用
     *
     * @param sqLiteDatabase   数据库对象
     * @param connectionSource 资源连接对象
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        //使用ORMLite提供的TableUtils类来创建数据库
        try {
            TableUtils.createTable(connectionSource, Account.class);
            TableUtils.createTable(connectionSource, NativeBookTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当版本号增加时，也就是数据库要升级或更新时调用
     *
     * @param sqLiteDatabase   数据库对象
     * @param connectionSource 资源连接对象
     * @param i                老版本
     * @param i1               新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
//        try {
        //删除原来的数据库
        // 不会保留数据
//            TableUtils.dropTable(connectionSource,Account.class,true);
        //调用创建方法
        // TODO 创建方法有两种参数列表，只有一个数据库对象的创建方法，底层也是调用的两个的，区别待测
//            onCreate(sqLiteDatabase,connectionSource);

        //更改表以添加名为“age”的新列
//            getAccountDao().executeRaw("ALTER TABLE 'accounts' ADD COLUMN age INTEGER;");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 获取操作Account表的DAO对象
     *
     * @return
     */
    public Dao getAccountDao() {
        if (accountDao == null) {
            try {
                accountDao = getDao(Account.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return accountDao;
    }

    public Dao getBookDao() {
        if (bookDao == null) {
            try {
                bookDao = getDao(NativeBookTable.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookDao;
    }

    public void insertBook(NativeBookTable bookTable) {
        try {
            getBookDao().create(bookTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean containBook(String bookName){
        Object obj = null;
        try {
            obj = getBookDao().queryForId(bookName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (obj == null){
            return false;
        }
        return true;
    }

    public List<NativeBookTable> queryBooks() {
        try {
            return getBookDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在关闭数据库连接的时候，将DAO对象都制空<br />
     * 有助于GC清理缓存
     */
    @Override
    public void close() {
        super.close();
        accountDao = null;
        bookDao = null;
    }
}
