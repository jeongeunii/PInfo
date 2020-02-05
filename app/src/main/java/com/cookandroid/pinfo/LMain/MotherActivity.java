package com.cookandroid.pinfo.LMain;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.cookandroid.pinfo.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MotherActivity extends AppCompatActivity {

    // 약국 조회 부분
    EditText editMother;

    ListView listView;
    List<String> list, list2;
    ArrayAdapter<String> adapter;
    ArrayList<Mother> motherList = new ArrayList<>();

    XmlPullParser xpp;
    String key="1cd67kYtfY1%2FFjczQJ8a3TxZxZe7dUZLZ35NEsdJNnstbBTYQRPUQe0pf231DdHgxQrQPwm5SHcZXbMfp190bw%3D%3D";

    String data1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmain_mother);
        setTitle("PInfo 의약품 정보 알리미");

        editMother = (EditText)findViewById(R.id.EditMother);
        listView = (ListView) findViewById(R.id.ListMother);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);

                AlertDialog.Builder dlg = new AlertDialog.Builder(MotherActivity.this);
                final String getName = motherList.get(i).getItemName();
                dlg.setTitle(getName);
                dlg.setMessage(list2.get(i).toString());
                dlg.setIcon(R.drawable.logo5);

                dlg.setPositiveButton("확인", null);
                dlg.show();
            }

        });
    }

    // 약국 조회 부분
    // Button을 클릭했을 때 자동으로 호출되는 callback method....
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.BtnMother: // 약 이름 검색 버튼
                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getXmlData(editMother.getText().toString());//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                    }
                }).start();
                break;
        }
    } // mOnClick method..

    // 약국 조회 부분
    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    ArrayList<Mother> getXmlData(String str){

        StringBuffer buffer = new StringBuffer();

        String location = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding.
        String Q0="";

        String queryUrl="http://apis.data.go.kr/1470000/DURPrdlstInfoService/getPwnmTabooInfoList?"//요청 URL
                +"itemName="+location +"&numOfRows=100&pageNo=1&ServiceKey=" + key;


        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부 터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            Mother mother = new Mother();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("ITEM_SEQ")){
                            mother = new Mother();
                            buffer.append("순번 : ");
                            xpp.next();
                            mother.setItemSeq(xpp.getText());
                        }
                        else if(tag.equals("ITEM_NAME")){
                            buffer.append("제품명 : ");
                            xpp.next();
                            mother.setItemName(xpp.getText());
                        }
                        else if(tag.equals("ENTP_NAME")){
                            buffer.append("업체명 : ");
                            xpp.next();
                            mother.setEntpName(xpp.getText());
                        }
                        else if(tag.equals("CLASS_NAME")){
                            buffer.append("카테고리 :");
                            xpp.next();
                            mother.setCategory(xpp.getText());
                        }
                        else if(tag.equals("MAIN_INGR")){
                            buffer.append("주성분 :");
                            xpp.next();
                            mother.setMainIngr(xpp.getText());
                        }
                        else if(tag.equals("PROHBT_CONTENT")){
                            buffer.append("금기 내용 :");
                            xpp.next();
                            mother.setProhbtCon(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) {
                            motherList.add(mother);
                            list.add(mother.toString());
                            list2.add(mother.listString());
                        }
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
        }

        buffer.append("검색 완료\n");
        return motherList;

    }//getXmlData method....
}
