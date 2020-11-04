package work.admin.example.com.accoutingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, CategoryRecyclerAdapter.OnCategoryClickListener{

    private EditText editText;
    private static String TAG = "AddRecordActivity";
    private TextView amountText;
      //初始化

    private String userInput = "";

    //实现category adapter

    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter adapter;

    //界面数据交互
    private String category = "General";
    private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
    private String remark = category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);

        amountText = (TextView) findViewById(R.id.textView_amount);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(remark);

        handleDot();
        handleTypeChange();
        handleBackspace();
        handleDone();

        //初始化recycleview
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new CategoryRecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),4);//传入4排
        recyclerView.setLayoutManager(gridLayoutManager);  //set
        adapter.notifyDataSetChanged();

        adapter.setOnCategoryClickListener(this);
    }

    private void handleDot() {
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userInput.contains(".")) {
                    userInput += ".";
                }

            }
        });
    }

    private void handleTypeChange() {
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener() {
            @Override
            //改变支出或收入的view
            public void onClick(View view) {
                if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
                    type = RecordBean.RecordType.RECORD_TYPE_INCOM;
                }else{
                    type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                }
                adapter.changeType(type);
                category = adapter.getSelected();
            }
        });
    }

    private void handleBackspace() {
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userInput.length() > 0){
                    userInput = userInput.substring(0,userInput.length() -1);

                }
                //删除点在最后
                if (userInput.length() > 0 && userInput.charAt(userInput.length() - 1) == '.'){
                    userInput = userInput.substring(0,userInput.length() -1);
                }

                updateAmountText();
            }
        });
    }

    private void handleDone() {
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userInput.equals("")){
                    double amount = Double.valueOf(userInput);

                    RecordBean record = new RecordBean();
                    record.setAmount(amount);

                    if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
                        record.setType(1);
                    }else{
                        record.setType(2);
                    }

                    //消费类别,adapter中有category
                    record.setCategory(category);
                    record.setRemark(editText.getText().toString());

                    //插入数据库
                    GlobalUtil.getInstance().databaseHelper.addRecord(record);
                    finish();


                   // Log.d(TAG, "final amount is" + amount);
                }else {
                    Toast.makeText(getApplicationContext(),"添加项目为0！",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;  //v即是讲解的view
        String input = button.getText().toString();


        if (userInput.contains(".")) {

            if (userInput.split("\\.").length == 1 || userInput.split("\\.")[1].length() < 2){         //不能写反
                userInput += input;                             //可以输入
            }
            //0. 0.5 0.05



        } else {
            userInput += input;  //实现输入裸机“123”
        }

        updateAmountText();
    }

    private void updateAmountText(){

        if (userInput.contains(".")){
            //50.
            if (userInput.split("\\.").length ==1){
                amountText.setText(userInput + "00");
            }else if (userInput.split("\\.")[1].length() == 1){
                amountText.setText(userInput + "0");
                }else if (userInput.split("\\.")[1].length() == 2){
                amountText.setText(userInput);

            }
        }else {
            if (userInput.equals("")){
                amountText.setText("0.00");
            }else {
                amountText.setText(userInput + ".00");
            }
        }


    }

    @Override
    public void onClikc(String category) {

        this.category = category;

        editText.setText(category);
       // Log.d(TAG,"ca:"+category);测试类别选择功能
    }
}