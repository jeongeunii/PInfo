package com.cookandroid.pinfo.Main;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    final  static  private String URL = "http://jeun123.cafe24.com/UserVaildate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);

    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}
