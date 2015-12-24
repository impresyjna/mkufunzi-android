package pl.com.inzynierka.mkufunzi.database;

/**
 * Created by impresyjna on 23.12.15.
 */
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbController extends Activity{

    /** This field is responsible for init sqlite local base */
    private SQLiteDatabase myDatabase = openOrCreateDatabase("mkufunzi", MODE_PRIVATE, null);;
    private Cursor result;
    private static DbController instance;

    private DbController() {
    }

    public static DbController getInstance() {
        if (instance == null) {
            instance = new DbController();
        }
        return instance;
    }

    public Cursor getResult() {
        return result;
    }

    public void setResult(Cursor result) {
        this.result = result;
    }

    public SQLiteDatabase getMydatabase() {
        return myDatabase;
    }

    public void initDatabase(SQLiteDatabase database)
    {
        this.myDatabase = database;
    }

    /**
     * This method is used to create new table if not exists in sqlite3 database on device
     * @param name This is the name of table
     * @param columns This is the string describing columns in table. Convention is number INT, name STRING and so on
     */
    public void createTable(String name, String columns)
    {
        String query = "CREATE TABLE IF NOT EXISTS " + name + "(" + columns + "); ";
        Query(query);
    }

    /* This function executes SQL commands: select, insert */
    public void Query(String query)
    {
        myDatabase.execSQL(query);
    }

    public void readData(String query)
    {
        result = myDatabase.rawQuery(query, null);
    }

    public Cursor returnCursor(String query)
    {
        Cursor cursor = myDatabase.rawQuery(query, null);
        return cursor;
    }
}
