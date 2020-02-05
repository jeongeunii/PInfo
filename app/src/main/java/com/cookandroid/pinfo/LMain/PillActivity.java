package com.cookandroid.pinfo.LMain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.pinfo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation") // @annotation 설정
public class PillActivity extends AppCompatActivity {

    // 약 조회 부분
    EditText editPill;
    TextView textBox;
    Button btnVo;

    ListView listView;
    List<String> list, list2;
    ArrayAdapter<String> adapter;
    ArrayList<Pill> pillList = new ArrayList<>();

    // 피카소
    ImageView picassoImageView;

    // 약 정보 파싱
    XmlPullParser xpp;
    String key="1cd67kYtfY1%2FFjczQJ8a3TxZxZe7dUZLZ35NEsdJNnstbBTYQRPUQe0pf231DdHgxQrQPwm5SHcZXbMfp190bw%3D%3D";

    String data1;

    // 음성인식 부분
    String keyVoice="1cd67kYtfY1%2FFjczQJ8a3TxZxZe7dUZLZ35NEsdJNnstbBTYQRPUQe0pf231DdHgxQrQPwm5SHcZXbMfp190bw%3D%3D";

    boolean isImageShow = false;

    private static final int REQUEST_CODE = 1234;
    Button btnVoice;
    TextView textVoice;
    Dialog match_text_dialog;
    ListView textList;
    ArrayList<String> matches_text;

    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lmain_pill);
        setTitle("PInfo 의약품 정보 알리미");

        // 약 조회 부분
        editPill = (EditText)findViewById(R.id.EditPill);
        textBox = (TextView)findViewById(R.id.TextBox);

        // 피카소
        // picassoImageView = (ImageView) findViewById(R.id.Image);

        // 약 조회 후 리스트뷰
        listView = (ListView) findViewById(R.id.List);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);

                AlertDialog.Builder dlg = new AlertDialog.Builder(PillActivity.this);
                final String getName = pillList.get(i).getItemName();
                dlg.setTitle(getName);
                dlg.setMessage(list2.get(i).toString());
                dlg.setIcon(R.drawable.logo5);

                dlg.setPositiveButton("확인", null);
                /*dlg.setNeutralButton("보관함", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder box = new AlertDialog.Builder(PillActivity.this);
                        box.setTitle("보관함");
                        box.setIcon(R.drawable.ic_local_hospital_black_24dp);

                        // 라디오버튼 만들기
                        final String[] str = {"보관함 담기"};
                        box.setSingleChoiceItems(str, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textBox.setText(textBox.getText() + "\n" + getName);
                            }
                        });

                        box.setPositiveButton("확인", null);
                        box.show();
                    }
                });*/

                dlg.setNeutralButton("부가정보", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder info = new AlertDialog.Builder(PillActivity.this);
                        info.setTitle("부가정보");
                        info.setIcon(R.drawable.logo5);
                        if (getName.equals("타이레놀정160밀리그람(아세트아미노펜)")) {
                            info.setMessage("1.효능효과\n감기로 인한 발열 및 동통(통증), 두통, 신경통, 근육통, 월경통, 염좌통(삔 통증)\n" +
                                    "치통, 관절통, 류마티양 동통(통증)\n2.용법용량\n1회 권장용량을 4-6시간 마다 필요시 복용한다.\n" +
                                    "이 약은 가능한 최단기간동안 최소 유효용량으로 복용하며, 1일 5회 (75mg/kg)를 초과하여 복용하지 않는다.\n" +
                                    "몸무게를 아는 경우 몸무게에 따른 용량(10~15mg/kg)으로 복용하는 것이 더 적절하다.\n3.주의사항\n" +
                                    "매일 세잔 이상 정기적으로 술을 마시는 사람이 이 약이나 다른 해열 진통제를 복용해야 할 경우 반드시 의사 또는 약사와 상의해야 한다.\n" +
                                    "이러한 사람이 이 약을 복용하면 간손상이 유발될 수 있다.");
                        } else if (getName.equals("타이레놀콜드-에스정(수출명:TylenolColdTablet,TylenolColdCaplet)")) {
                            info.setMessage("1.효능효과\n감기의 제증상(여러 증상)(콧물, 코막힘, 재채기, 인후(목구멍)통, 기침, 오한(춥고 떨리는 증상), 발열, 두통, 관절통, 근육통)의 완화" +
                                    "\n2.용법용량\n다음 1회의 용량을 1일 3회 식후 30분에 복용하는 것으로 합니다.\n1회 용량 : 15세 이상 - 1정\n" +
                                    "3.주의사항\n매일 세잔 이상 정기적으로 술을 마시는 사람이 이 약이나 다른 해열 진통제를 복용해야 할 경우 반드시 의사 또는 약사와 상의해야 한다. 이러한 사람이 이 약을 복용하면 간손상이 유발될 수 있다.");
                        } else if (getName.equals("어린이용타이레놀정80밀리그람(아세트아미노펜)")) {
                            info.setMessage("1.효능효과\n감기로 인한 발열 및 동통(통증), 두통, 신경통, 근육통, 월경통, 염좌통(삔 통증)\n" +
                                    "치통, 관절통, 류마티양 동통(통증)\n2.용법용량\n1회 권장용량을 4-6시간 마다 필요시 복용한다.\n" +
                                    "이 약은 가능한 최단기간동안 최소 유효용량으로 복용하며, 1일 5회 (75mg/kg)를 초과하여 복용하지 않는다.\n" +
                                    "몸무게를 아는 경우 몸무게에 따른 용량(10~15mg/kg)으로 복용하는 것이 더 적절하다.\n3.주의사항\n" +
                                    "매일 세잔 이상 정기적으로 술을 마시는 사람이 이 약이나 다른 해열 진통제를 복용해야 할 경우 반드시 의사 또는 약사와 상의해야 한다.\n" +
                                    "이러한 사람이 이 약을 복용하면 간손상이 유발될 수 있다.");
                        } else if (getName.equals("이지엔6애니연질캡슐(이부프로펜)")) {
                            info.setMessage("1.효능효과\n감기로 인한 발열 및 동통(통증), 요통, 생리통, 류마티양 관절염, 연소성(어리거나 젊은나이에 나타나는) 류마티양 관절염골관절염(퇴행성 관절질환), 수술후 동통(통증)" +
                                    "\n두통, 편두통, 치통, 근육통, 신경통, 강직성 척추염, 급성통풍, 건선(마른비닐증)성 관절염, 연조직손상(염좌(삠), 좌상(타박상)), 비관절 류마티스질환(건염(힘줄염), 건초염(힘줄윤활막염), 활액(윤활)낭염)" +
                                    "\n2.용법용량\n경증(가벼운 증상) 및 중등도의 동통(통증), 감기: 성인 1회 200-400mg 1일 3-4회 경구투여한다(먹는다). 연령(나이), 증상에 따라 적절히 증감한다." +
                                    "\n편두통: 성인 1회 200-400mg을 경구투여한다(먹는다). 24시간 동안 2캅셀을 초과하지 않도록 한다.\n3.주의사항\n" +
                                    "매일 세잔 이상 정기적으로 술을 마시는 사람이 이 약이나 다른 해열진통제를 복용해야 할 경우 반드시 의사 또는 약사와 상의해야 한다. 이러한 사람이 이 약을 복용하면 위장출혈이 유발될 수 있다.");
                        } else if (getName.equals("이지엔6이브연질캡슐")) {
                            info.setMessage("1.효능효과\n두통, 치통, 발치후 동통(통증), 인후(목구멍)통, 귀의 통증, 관절통, 신경통, 요통, 근육통, 견통(어깨결림), 타박통, 골절통, 염좌통(삠통증), 월경통(생리통), 외상통의 진통\n" +
                                    "오한(춥고 떨리는 증상), 발열시의 해열\n2.용법용량\n만 8세 이상~만 15세 미만 : 1일 1~3회, 1회 1캡슐\n만 15세 이상 또는 성인 : 1일 1~3회, 1회 1~2캡슐\n" +
                                    "공복(빈 속)시를 피하여 복용한다.\n복용간격은 4시간 이상으로 한다.\n3.주의사항\n" +
                                    "매일 세잔 이상 정기적으로 술을 마시는 사람이 이 약이나 다른 해열진통제를 복용해야 할 경우 반드시 의사 또는 약사와 상의해야 한다. 이러한 사람이 이 약을 복용하면 위장출혈이 유발될 수 있다.");
                        }
                        info.setPositiveButton("확인", null);

                        info.setNeutralButton("이미지", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Drawable drawable = getResources().getDrawable(R.drawable.tylenol);
                                if (getName.equals("타이레놀정160밀리그람(아세트아미노펜)")) {
                                    drawable = getResources().getDrawable(R.drawable.tylenol);
                                } else if (getName.equals("타이레놀콜드-에스정(수출명:TylenolColdTablet,TylenolColdCaplet)")) {
                                    drawable = getResources().getDrawable(R.drawable.tylenolcold);
                                } else if (getName.equals("어린이용타이레놀정80밀리그람(아세트아미노펜)")) {
                                    drawable = getResources().getDrawable(R.drawable.tylenolchild);
                                } else if (getName.equals("이지엔6애니연질캡슐(이부프로펜)")) {
                                    drawable = getResources().getDrawable(R.drawable.eznany);
                                } else if (getName.equals("이지엔6이브연질캡슐")) {
                                    drawable = getResources().getDrawable(R.drawable.ezneve);
                                }
                                ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                                imageView.setImageDrawable(drawable);

                                isImageShow = true;
                            }
                        });
                        info.show();
                    }
                });

                dlg.show();

                // Picasso.with(getApplicationContext())
                //        .load(pillList.get(i).getEeDoc())
                //        .into(picassoImageView);
            }
        });

        // https://recipes4dev.tistory.com/59
        // 리스트뷰에서 보관함 담기
        Button btnBox = (Button)findViewById(R.id.BtnBox);
        btnBox.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count = adapter.getCount();

                // ArrayList<Pill> pillArray = new ArrayList<>();
                ArrayList<Pill> pillArray = getPillsItems();
                if(pillArray == null) {
                    pillArray = new ArrayList<>();
                }

                for (int i=count-1; i>=0; i--) {
                    if (checkedItems.get(i)) {
                        pillArray.add(pillList.get(i));
                        textBox.setText(textBox.getText() + "\n" + pillList.get(i).getItemName());
                    }
                }
                setPillsItems(pillArray);
            }
        });

        // 음성인식 부분
        btnVoice = (Button)findViewById(R.id.BtnVoice);
        textVoice = (TextView)findViewById(R.id.TextVoice);

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "인터넷 연결해주세요", Toast.LENGTH_LONG).show();
                }}

        });
    }

    // 약 조회 부분
    //Button을 클릭했을 때 자동으로 호출되는 callback method....
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.BtnPill: // 약 이름 검색 버튼
                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getXmlData(editPill.getText().toString());//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                    }
                }).start();
                break;
        }
    } // mOnClick method..

    // 약 조회 부분
    //XmlPullParser를 이용하여 XML 파일 파싱하기(parsing)
    ArrayList<Pill> getXmlData(String str){
        // 약 정보 파싱
        StringBuffer buffer = new StringBuffer();

        String location = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding.
        String Q0="";

        String queryUrl="http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?"//요청 URL
                +"item_name="+location +"&numOfRows=100&pageNo=1&ServiceKey=" + key;

        try {
            // 약 정보 파싱
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부 터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            Pill pill = new Pill();
            while( eventType != XmlPullParser.END_DOCUMENT){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");

                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")); // 첫번째 검색결과
                        else if(tag.equals("ITEM_SEQ")){
                            pill = new Pill();
                            buffer.append("순번 : ");
                            xpp.next();
                            pill.setItemSeq(xpp.getText());
                            // getText(seq 값)으로 이미지 호출
                        }
                        else if(tag.equals("ITEM_NAME")){
                            buffer.append("제품명 : ");
                            xpp.next();
                            pill.setItemName(xpp.getText());
                        }
                        else if(tag.equals("ENTP_NAME")){
                            buffer.append("업체명 : ");
                            xpp.next();
                            pill.setEntpName(xpp.getText());
                        }
                        else if(tag.equals("STORAGE_METHOD")){
                            buffer.append("저장 방법:");
                            xpp.next();
                            pill.setStoMeth(xpp.getText());
                        }
                        else if(tag.equals("VALID_TERM")){
                            buffer.append("유효기간 :");
                            xpp.next();
                            pill.setValidTerm(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) {
                            pillList.add(pill);
                            list.add(pill.toString());
                            list2.add(pill.listString());
                        }
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
        }

        buffer.append("검색 완료\n");
        // Log.d("PillActivity", buffer.toString());
        return pillList;

    }//getXmlData method....

    // 리스트뷰 체크 항목 보관함에 담기
    public ArrayList<Pill> getPillsItems() {
        String json = this
                .getSharedPreferences("pinfo", Context.MODE_PRIVATE)
                .getString("pills", "");

        if(json.equals("")) {
            // 저장된 값이 없으면 새로운 리스트를 만들어서 리턴
            return new ArrayList<Pill>();
        }

        Type type = new TypeToken<ArrayList<Pill>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public void setPillsItems(ArrayList<Pill> items) {
        this.getSharedPreferences("pinfo", Context.MODE_PRIVATE)
                .edit()
                .putString("pills", new Gson().toJson(items).toString())
                .apply();
    }

    // 음성 인식 부분
    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if(isImageShow) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            imageView.setImageResource(0);
            isImageShow = false;
        } else {
            super.onBackPressed();
        }
    }

    // 음성 인식 검색 부분
    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE) {
            // 음성이 실행된 후에 호출되는 상황
            if (resultCode == RESULT_OK) {

                match_text_dialog = new Dialog(PillActivity.this);
                match_text_dialog.setContentView(R.layout.dialog_matches_frag);
                match_text_dialog.setTitle("검색 결과에 맞는 단어를 선택하세요");
                textList = (ListView)match_text_dialog.findViewById(R.id.list);
                matches_text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, matches_text);
                textList.setAdapter(adapter);
                textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {

                        textVoice.setText("\"" + matches_text.get(position) + "\"");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getXmlData(matches_text.get(position));
                            }
                        }).start();
                        match_text_dialog.hide();
                    }
                });
                match_text_dialog.show();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
