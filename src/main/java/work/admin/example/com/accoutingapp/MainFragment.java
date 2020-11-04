package work.admin.example.com.accoutingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

//忽略错误提示
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    private View rootView;
    private TextView textView;
    private ListView listView;
    private ListViewAdapter listViewAdapter;

    private LinkedList<RecordBean> records = new LinkedList<>();


    private String date = "";


    //@SuppressLint("ValidFragment")//貌似可以不需要，Fragment方法如果有参数用这个修复
    public MainFragment(String date){
        this.date = date;
        records = GlobalUtil.getInstance().databaseHelper.readRecords(date);//可能有错


       /* records.add(new RecordBean());
        records.add(new RecordBean());
        records.add(new RecordBean());测试代码*/
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);腹泻方法
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
        initView();
        return rootView;
    }

    public void reload(){
        records = GlobalUtil.getInstance().databaseHelper.readRecords(date);
        listViewAdapter.setData(records);
    }

    private void initView(){

        textView = (TextView) rootView.findViewById(R.id.day_text);//调用view的find方法
        listView = (ListView) rootView.findViewById(R.id.listview);
        textView.setText(date);
        //初始化listview
        listViewAdapter = new ListViewAdapter(getContext());
        listViewAdapter.setData(records);
        listView.setAdapter(listViewAdapter);  //适配器绑定才能传数据

        if(listViewAdapter.getCount()>0){
            rootView.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
        }
    }

    public int getTotalCost() {
        double totalCost = 0;
        for (RecordBean record:records){
            if (record.getType()==1){
                totalCost += record.getAmount();
            }
        }
           return (int)totalCost;
    }


}

