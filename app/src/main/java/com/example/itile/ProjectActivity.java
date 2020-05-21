package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.AllProjectAdapter;
import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProjectActivity extends AppCompatActivity {

    private String project_id;
    private TextView Aname;
    private TextView Adescription;

    private String name;
    private String state;
    private String description;
    private RelativeLayout relativeLayout;
    private List<Map<String, Object>> list = new ArrayList<>();
    private ImageView back;
    private String message;

    private RelativeLayout relativeLayout1;
    private TextView change;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_project);

        Intent intent = getIntent();
        project_id = intent.getStringExtra("project_id");

        relativeLayout = findViewById(R.id.all_task);
        back = findViewById(R.id.back);
        Aname = findViewById(R.id.name);
        Adescription = findViewById(R.id.description);



        DetailWithOkHttp("http://118.190.245.170/worktile/project/"+project_id);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProjectActivity.this, ListTaskActivity.class);
                intent.putExtra("project_id",project_id);
                startActivity(intent);

            }
        });

        change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProjectActivity.this,ChangeProjectInfoActivity.class);
                intent.putExtra("project_id",project_id);
                intent.putExtra("state",state);
                startActivity(intent);

            }
        });


        relativeLayout1 = findViewById(R.id.member);

        relativeLayout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProjectActivity.this, SeeProjectMemberActivity.class);
                intent.putExtra("project_id",project_id);
                startActivity(intent);



            }
        });

    }


    public void DetailWithOkHttp(String address) {
        HttpUtil.ShowAllProjectWithOkHttp(address, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                //       Toast.makeText(getActivity(),"获取图书信息失败，请检查您的网络",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                        name = jsonObject1.getString("name");
                        state = jsonObject1.getString("state");
                        description = jsonObject1.getString("description");

////                        message = jsonObject1.getString("message");
//
//
//                        Log.d("nameqsh",name);
//                        Log.d("nameme",message);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Aname.setText(name);
                            Adescription.setText(description);


                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }

}