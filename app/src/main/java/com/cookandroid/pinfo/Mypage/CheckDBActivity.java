package com.cookandroid.pinfo.Mypage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.cookandroid.pinfo.R;

import java.util.ArrayList;
import java.util.List;

public class CheckDBActivity extends AppCompatActivity {

    List<CheckDBStructure> employeeList;
    SQLiteDatabase mDatabase;
    ListView listViewEmployees;
    CheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_list);

        listViewEmployees = (ListView) findViewById(R.id.listViewEmployees);
        employeeList = new ArrayList<>();

        //opening the database
        mDatabase = openOrCreateDatabase(CheckActivity.DATABASE_NAME, MODE_PRIVATE, null);

        //this method will display the employees in the list
        showEmployeesFromDatabase();
    }

    private void showEmployeesFromDatabase() {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees", null);

        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                employeeList.add(new CheckDBStructure(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4)
                ));
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();

        //creating the adapter object
        adapter = new CheckAdapter(this, R.layout.check_list_layout, employeeList, mDatabase);

        //adding the adapter to listview
        listViewEmployees.setAdapter(adapter);
    }

}