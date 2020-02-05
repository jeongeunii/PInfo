package com.cookandroid.pinfo.LMain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.pinfo.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QrActivity extends AppCompatActivity {

    // QR코드 부분
    //view Objects
    private Button btnScan;
    private TextView tvName, tvAddress, tvResult;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmain_qr);
        setTitle("PInfo 의약품 정보 알리미");

        // QR코드 부분
        //View Objects
        btnScan = (Button) findViewById(R.id.BtnScan);
        tvName = (TextView) findViewById(R.id.TvName);
        tvAddress = (TextView) findViewById(R.id.TvAddress);
        tvResult = (TextView)  findViewById(R.id.TvResult);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //scan option
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });
    }

    // QR코드 검색 부분
    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            // qr코드가 실행된 후에 호출되는 상황
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                //qrcode 가 없으면
                if (result.getContents() == null) {
                    Toast.makeText(QrActivity.this, "취소!", Toast.LENGTH_SHORT).show();
                } else {
                    //qrcode 결과가 있으면
                    Toast.makeText(QrActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                    try {
                        //data를 json으로 변환
                        JSONObject obj = new JSONObject(result.getContents());
                        tvName.setText(obj.getString("name"));
                        tvAddress.setText(obj.getString("address"));
                        String url = obj.getString("address");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(QrActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                        tvResult.setText(result.getContents());
                        String url = (result.getContents());
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                }
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
