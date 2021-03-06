
package com.example.itile;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;
        import com.bumptech.glide.request.RequestOptions;
        import com.example.itile.Util.HttpUtil;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.Response;

public class TaskActivity extends AppCompatActivity {

    private RelativeLayout back;
    private String task_id;
    private String project_id;
    private RelativeLayout relativeLayout;
    private String name;
    private String description;
    private String starttime;
    private String endtime;
    private String state;
    private String manager_id;
    private String manager_name;
    private String subtask_num;
    private String right;
    private String project_id_belong;
    private String project_name;

    private TextView Aname;
    private TextView Adescription;
    private TextView Astarttime;
    private TextView Aendtime;
    private TextView Amanager;
    private TextView Anumber;
    private TextView Aproject;
    private TextView tvstate;
    private RelativeLayout owner;
    private String manager_pic;
    private ImageView head;
    private TextView show_state;
    private String ifcreator;
    private RelativeLayout belong;
    private RelativeLayout relativeLayout1;
    private TextView change;

    private int i=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task);

        Intent intent = getIntent();
        task_id = intent.getStringExtra("task_id");
        project_id = intent.getStringExtra("project_id");

        belong = findViewById(R.id.belong);
        show_state = findViewById(R.id.state);
        head = findViewById(R.id.manager_pic);
        relativeLayout = findViewById(R.id.all_task);
        Aname = findViewById(R.id.name);
        Astarttime = findViewById(R.id.time1);
        Aendtime = findViewById(R.id.time2);
        Adescription = findViewById(R.id.description);
        Amanager = findViewById(R.id.manager);
        Anumber = findViewById(R.id.number);
        Aproject = findViewById(R.id.project);
        owner = findViewById(R.id.owner);
        tvstate = findViewById(R.id.state);

        belong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                RightWithOkHttp("http://118.190.245.170/worktile/project/"+project_id);



            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TaskActivity.this, ListSubTaskActivity.class);
                intent.putExtra("project_id",project_id);
                intent.putExtra("task_id",task_id);
                startActivity(intent);
            }
        });


//        DetailWithOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/task/"+task_id);

        relativeLayout1 = findViewById(R.id.re);

        relativeLayout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

//                if(ifcreator.equals("0"))
//                {
//                    Toast.makeText(TaskActivity.this, "只有任务负责人可以修改负责人！", Toast.LENGTH_SHORT).show();
//                }
//                else {
                    Intent intent = new Intent(TaskActivity.this,SeeTaskMemberActivity.class);
                    intent.putExtra("project_id",project_id);
                    intent.putExtra("task_id",task_id);
                    intent.putExtra("ifcreator",ifcreator);
                    startActivity(intent);
//                }
            }
        });

        owner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (ifcreator.equals("0"))
                {

                }

                else {
                    Intent intent = new Intent(TaskActivity.this,ChangeTaskOwnerActivity.class);
                    intent.putExtra("project_id",project_id);
                    intent.putExtra("task_id",task_id);
                    intent.putExtra("owner_id", manager_id);
                    startActivity(intent);
                }

            }
        });
                change = findViewById(R.id.change);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DetailWithOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/task/"+task_id);

        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TaskActivity.this,ChangeTaskInfoActivity.class);
                intent.putExtra("project_id",project_id);
                intent.putExtra("task_id",task_id);
                intent.putExtra("state",state);
                startActivity(intent);
            }
        });





    }

    private void GlideWithPictureUrl(String image,ImageView imageView){
        String picture_1 = image.replace("\\","");
        String picture_2 = picture_1.replace("\"","");
        String picture_3 = picture_2.replace("[","");
        String picture_4 = picture_3.replace("]","");


        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.glide)
                .error(R.drawable.glide)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(TaskActivity.this)
                .load(picture_4)
                .apply(options)
                .into(imageView);



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
                        name = jsonObject1.getString("task_name");
                        state = jsonObject1.getString("state");
                        description = jsonObject1.getString("description");
                        starttime = jsonObject1.getString("starttime");
                        endtime = jsonObject1.getString("endtime");
                        manager_id = jsonObject1.getString("manager_id");
                        manager_name = jsonObject1.getString("manager_name");
                        right = jsonObject1.getString("right");
                        project_id_belong = jsonObject1.getString("project_id");
                        project_name = jsonObject1.getString("project_name");
                        subtask_num = jsonObject1.getString("subtask_num");
                    manager_pic = jsonObject1.getString("manager_pic");
                    ifcreator = jsonObject1.getString("ifmanager");

                    Log.d("520",manager_pic);
                    String time1 = starttime.replace("T", " ");
                    String time2 = endtime.replace("T", " ");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Aname.setText(name);
                            Adescription.setText(description);

                            String time11 = time1.substring(0,time1.length()-3);
                            String time22 = time2.substring(0,time2.length()-3);

                            Astarttime.setText(time11);
                            Aendtime.setText(time22);
                            Amanager.setText(manager_name);
                            Anumber.setText(subtask_num);
                            Aproject.setText(project_name);
//                            tvstate.setText(state);
                            GlideWithPictureUrl("http://118.190.245.170/worktile/media/"+manager_pic,head);

                            if (state.equals("0")){
                                show_state.setText("未开始");

                                show_state.setTextColor(Color.parseColor("#f1263b"));
                            }
                            else if (state.equals("1"))
                            {
                                show_state.setText("进行中");

                                show_state.setTextColor(Color.parseColor("#ffdb5c"));
                            }
                            else
                            { show_state.setText("已完成");

                            show_state.setTextColor(Color.parseColor("#bddc8d"));}

                            if(ifcreator.equals("0"))
                            {
                                change.setVisibility(View.INVISIBLE);

                            }

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.i("zyr", responseData);
                }
            }
        });
    }


    public void RightWithOkHttp(String address) {
        HttpUtil.RightWithOkHttp(address, new Callback() {

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


                    right = jsonObject1.getString("right");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (right.equals("0"))i=0;
                            else i=1;

                            if (i==0)
                                Toast.makeText(TaskActivity.this, "您没有权限访问该页面！", Toast.LENGTH_SHORT).show();

                            else {
                                Intent intent = new Intent(TaskActivity.this, ProjectActivity.class);
                                intent.putExtra("project_id",project_id);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}