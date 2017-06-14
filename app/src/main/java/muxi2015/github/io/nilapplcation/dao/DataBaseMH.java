package muxi2015.github.io.nilapplcation.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

import muxi2015.github.io.nilapplcation.entity.Question;

/**
 * 连接数据库
 * Created by muxi on 6/13/2017.
 */

public class DataBaseMH {
    //数据库位置
    private final static String DBPOSTION = "/data/data/muxi2015.github.io.nilapplcation/databases/";
    //数据库对象
    private SQLiteDatabase db = null;
    //游标对象
    Cursor cursor = null;

    public DataBaseMH(String dbname) {
        db = SQLiteDatabase.openDatabase(DBPOSTION + dbname + ".db", null, SQLiteDatabase.OPEN_READONLY);
        cursor = db.rawQuery("select * from " + dbname, null);
    }

    //获取数据库数据
    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();

            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);

                //ID
                int ID = cursor.getInt(cursor.getColumnIndex("Field1"));
                //问题
                String questionDetail = cursor.getString(cursor.getColumnIndex("Field2"));
                //四个选择
                String optionA = cursor.getString(cursor.getColumnIndex("Field3"));
                String optionB = cursor.getString(cursor.getColumnIndex("Field4"));
                String optionC = cursor.getString(cursor.getColumnIndex("Field5"));
                String optionD = cursor.getString(cursor.getColumnIndex("Field6"));
                //答案
                int answer = cursor.getInt(cursor.getColumnIndex("Field7"));
                //解析
                String ruslt = cursor.getString(cursor.getColumnIndex("Field8"));
                //设置为没有选择任何选项
                int selectedAnswer = -1;
                Question question = new Question(ID, optionA, optionB, optionC, optionD, questionDetail, answer, ruslt, selectedAnswer);
                list.add(question);
            }
        }
        return list;
    }


}
