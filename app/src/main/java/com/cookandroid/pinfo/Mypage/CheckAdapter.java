package com.cookandroid.pinfo.Mypage;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.pinfo.R;

import java.util.List;

public class CheckAdapter extends ArrayAdapter<CheckDBStructure> {

    Context mCtx;
    int listLayoutRes;
    List<CheckDBStructure> checkDbStructureList;
    SQLiteDatabase mDatabase;

    public CheckAdapter(Context mCtx, int listLayoutRes, List<CheckDBStructure> checkDbStructureList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, checkDbStructureList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.checkDbStructureList = checkDbStructureList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final CheckDBStructure checkDbStructure = checkDbStructureList.get(position);


        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewDept = (TextView) view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = (TextView) view.findViewById(R.id.textViewSalary);
        TextView textViewJoiningDate = (TextView) view.findViewById(R.id.textViewJoiningDate);


        textViewName.setText(checkDbStructure.getName());
        textViewDept.setText(checkDbStructure.getDept());
        textViewSalary.setText(String.valueOf(checkDbStructure.getSalary()));
        textViewJoiningDate.setText(checkDbStructure.getJoiningDate());


        Button buttonDelete = (Button) view.findViewById(R.id.buttonDeleteEmployee);
        Button buttonEdit = (Button) view.findViewById(R.id.buttonEditEmployee);

        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(checkDbStructure);
            }
        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("정말 삭제하십니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM employees WHERE id = ?";
                        mDatabase.execSQL(sql, new Integer[]{checkDbStructure.getId()});
                        reloadEmployeesFromDatabase();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void updateEmployee(final CheckDBStructure checkDbStructure) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.update_check, null);
        builder.setView(view);


        final EditText editTextName = (EditText) view.findViewById(R.id.editTextName);
        final EditText editTextSalary = (EditText) view.findViewById(R.id.editTextSalary);
        // final Spinner spinnerDepartment = (Spinner) view.findViewById(R.id.spinnerDepartment);
        final RadioGroup radio = (RadioGroup) view.findViewById(R.id.radio);


        editTextName.setText(checkDbStructure.getName());
        editTextSalary.setText(String.valueOf(checkDbStructure.getSalary()));

        final AlertDialog dialog = builder.create();
        dialog.show();


        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String salary = editTextSalary.getText().toString().trim();
                // String dept = spinnerDepartment.getSelectedItem().toString();
                int a =  radio.getCheckedRadioButtonId();
                final RadioButton rb = view.findViewById(a);
                String dept = rb.getText().toString();


                if (name.isEmpty()) {
                    editTextName.setError("이름이 빈칸입니다");
                    editTextName.requestFocus();
                    return;
                }


                String sql = "UPDATE employees \n" +
                        "SET name = ?, \n" +
                        "department = ?, \n" +
                        "salary = ? \n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{name, dept, salary, String.valueOf(checkDbStructure.getId())});
                Toast.makeText(mCtx, "저장되었습니다", Toast.LENGTH_SHORT).show();
                reloadEmployeesFromDatabase();

                dialog.dismiss();
            }
        });
    }

    private void reloadEmployeesFromDatabase() {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees", null);
        if (cursorEmployees.moveToFirst()) {
            checkDbStructureList.clear();
            do {
                checkDbStructureList.add(new CheckDBStructure(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4)
                ));
            } while (cursorEmployees.moveToNext());
        }
        cursorEmployees.close();
        notifyDataSetChanged();
    }

}