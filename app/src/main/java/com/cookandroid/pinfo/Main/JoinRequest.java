package com.cookandroid.pinfo.Main;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinRequest extends StringRequest {
    final  static  private String URL = "http://jeun123.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public JoinRequest(String userID, String userPassword, String userYear , Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userYear", userYear);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
