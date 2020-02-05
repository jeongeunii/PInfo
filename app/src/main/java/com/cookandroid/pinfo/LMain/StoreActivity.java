package com.cookandroid.pinfo.LMain;


import android.app.AlertDialog;
import android.os.Bundle;
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

@SuppressWarnings("deprecation")
public class StoreActivity extends AppCompatActivity {

    // 약국 조회 부분
    EditText editStore;

    ListView listView;
    List<String> list, list2;
    ArrayAdapter<String> adapter;
    ArrayList<Store> storeList = new ArrayList<>();

    XmlPullParser xpp;
    String key="1cd67kYtfY1%2FFjczQJ8a3TxZxZe7dUZLZ35NEsdJNnstbBTYQRPUQe0pf231DdHgxQrQPwm5SHcZXbMfp190bw%3D%3D";
    //서비스 키

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmain_store);
        setTitle("PInfo 의약품 정보 알리미");

        /* 약국 조회 부분*/
        editStore = (EditText) findViewById(R.id.EditStore);

        /* 약국 조회 후 리스트뷰 */
        listView = (ListView) findViewById(R.id.ListS);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);

                AlertDialog.Builder dlg = new AlertDialog.Builder(StoreActivity.this);
                final String getName = storeList.get(i).getDutyName();
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
            case R.id.BtnStore: // 약 이름 검색 버튼
                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getXmlData(editStore.getText().toString());//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                    }
                }).start();
                break;
        }
    } // mOnClick method..

    // 약국 조회 부분
    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    ArrayList<Store> getXmlData(String str){

        StringBuffer buffer = new StringBuffer();

        String location = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String query = "%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C"; //Q0의 값

        String queryUrl="http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?"//요청 URL
                +"&Q0="+location
                +"&pageNo=1&numOfRows=1000&ServiceKey=" + key;

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부 터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            Store store = new Store();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("dutyAddr")){
                            store = new Store();
                            buffer.append("주소 : ");
                            xpp.next();
                            store.setDutyAddr(xpp.getText());
                        }
                        else if(tag.equals("dutyName")){
                            buffer.append("약국 이름 : ");
                            xpp.next();
                            store.setDutyName(xpp.getText());
                        }
                        else if(tag.equals("dutyTel1")){
                            buffer.append("전화 번호 : ");
                            xpp.next();
                            store.setDutyTel(xpp.getText());
                        }
                        else if(tag.equals("dutyTime1c")){
                            buffer.append("월요일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime1(xpp.getText());
                        }
                        else if(tag.equals("dutyTime2c")){
                            buffer.append("화요일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime2(xpp.getText());
                        }
                        else if(tag.equals("dutyTime3c")){
                            buffer.append("수요일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime3(xpp.getText());
                        }
                        else if(tag.equals("dutyTime4c")){
                            buffer.append("목요일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime4(xpp.getText());
                        }
                        else if(tag.equals("dutyTime5c")){
                            buffer.append("금요일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime5(xpp.getText());
                        }
                        else if(tag.equals("dutyTime6c")){
                            buffer.append("토요일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime6(xpp.getText());
                        }
                        else if(tag.equals("dutyTime7c")){
                            buffer.append("일요일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime7(xpp.getText());
                        }
                        else if(tag.equals("dutyTime8c")){
                            buffer.append("공휴일 영업 종료 :");
                            xpp.next();
                            store.setDutyTime8(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) {
                            storeList.add(store);
                            list.add(store.toString());
                            list2.add(store.listString());
                        }
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
        }

        buffer.append("검색 완료\n");
        return storeList;

    }//getXmlData method....

}//lmain_store class..
