package com.cookandroid.pinfo.Main;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cookandroid.pinfo.R;

import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userYear;
    private AlertDialog dialog;
    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_join);
        setTitle("PInfo 의약품 정보 알리미");
        
        spinner = (Spinner) findViewById(R.id.yearSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);

        final Button validateButton = (Button) findViewById(R.id.validateButton);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if(validate){
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("아이디가 빈칸입니다")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용 할 수 없는 아이디 입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();

                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();

                        }
                    }
                };

                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(validateRequest);

            }

        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userYear = spinner.getSelectedItem().toString();

                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("중복체크해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (userID.equals("") || userPassword.equals("") || userYear.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("빈칸을 전부 채워주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("회원가입 성공")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("회원등록실패")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                JoinRequest registerRequest = new JoinRequest(userID, userPassword, userYear, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(registerRequest);
            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
