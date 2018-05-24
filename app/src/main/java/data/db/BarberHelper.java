package data.db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BarberHelper extends SQLiteOpenHelper{

    //Static variables used to create the database
    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "barber";
    private static final String TABLE_NAME = "";

    public BarberHelper(Context context){
        super(context,"Barber", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) throws SQLException {
        try {
            String sql = "CREATE TABLE";
            sqLiteDatabase.execSQL(sql);
        }catch (SQLException sqlException){
            throw new SQLException("Error creating table." + sqlException.toString());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) throws SQLException{
        try {
            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;

        }catch (SQLException sqlException){
            throw new SQLException("Error to updating " + TABLE_NAME + sqlException.toString());
        }
    }
}
