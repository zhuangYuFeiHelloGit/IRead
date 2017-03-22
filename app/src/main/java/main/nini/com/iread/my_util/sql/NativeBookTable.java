package main.nini.com.iread.my_util.sql;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zyf on 2017/3/12.
 */

@DatabaseTable(tableName = "nativeBook")
public class NativeBookTable implements Parcelable{

    /**
     * 自然增长的主键
     */
//    @DatabaseField(generatedId = true)
//    private int id;

    /**
     * 以书名作为主键id
     */
    @DatabaseField(id = true)
    private String bookName;

    @DatabaseField
    private String bookFile;

    public NativeBookTable() {
    }

    public NativeBookTable(String bookName, String bookFile) {
        this.bookName = bookName;
        this.bookFile = bookFile;
    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    protected NativeBookTable(Parcel in) {
        bookName = in.readString();
        bookFile = in.readString();
    }

    public static final Creator<NativeBookTable> CREATOR = new Creator<NativeBookTable>() {
        @Override
        public NativeBookTable createFromParcel(Parcel in) {
            return new NativeBookTable(in);
        }

        @Override
        public NativeBookTable[] newArray(int size) {
            return new NativeBookTable[size];
        }
    };

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookFile() {
        return bookFile;
    }

    public void setBookFile(String bookFile) {
        this.bookFile = bookFile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(bookFile);
    }
}
