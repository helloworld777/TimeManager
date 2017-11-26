package com.tm.lyw.timemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tm.lyw.timemanager.adapter.LuAdapter;
import com.tm.lyw.timemanager.adapter.base.ViewHolder;
import com.tm.lyw.timemanager.db.MonthBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ListView listView;
    private LuAdapter<MonthBean> monthBeanLuAdapter;
    List<MonthBean> monthBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView=findViewById(R.id.listView);
        monthBeans= new ArrayList<>(12);
        for (int i=1;i<=12;i++){
            MonthBean monthBean=new MonthBean();
            monthBean.month=i;
            monthBeans.add(monthBean);
        }

        monthBeanLuAdapter=new LuAdapter<MonthBean>(this,monthBeans,R.layout.item_month) {
            @Override
            public void convert(ViewHolder helper, int position) {

            }

            @Override
            public void convert(ViewHolder helper,final MonthBean item) {
                helper.setText(R.id.tvTime,item.month+"月");
                if (item.isExspan){
                    helper.getView(R.id.monthListView).setVisibility(View.VISIBLE);
                }else{
                    helper.getView(R.id.monthListView).setVisibility(View.GONE);
                }
                ListView listView=helper.getView(R.id.monthListView);
                final List<String> days=new ArrayList<>(31);
                int day=(DateUtil.dayOfMonth(item.month-1,DateUtil.getCurrentYear()));
                for (int i=1;i<=day;i++){
                    days.add(String.valueOf(i));
                }
                LuAdapter<String> adapter=new LuAdapter<String>(HistoryActivity.this,days,R.layout.item_recyc) {
                    @Override
                    public void convert(ViewHolder helper, int position) {

                    }

                    @Override
                    public void convert(ViewHolder helper, String item) {
                        helper.setText(R.id.tvTime,item);
                    }
                };
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Snackbar.make(view, item.month+"月"+days.get(position), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        long time=Long.valueOf(DateUtil.getCurrentYear()+String.format("%02d",item.month)+String.format("%02d",Integer.parseInt(days.get(position))));
                        Log.d(HistoryActivity.class.getSimpleName(),"time:"+time);
                        Intent intent=new Intent(HistoryActivity.this,MainActivity.class);
                        intent.putExtra("time",time);
                        startActivity(intent);

                    }
                });

                helper.getView(R.id.rlItem).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.isExspan=!item.isExspan;
                        monthBeanLuAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        listView.setAdapter(monthBeanLuAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                monthBeans.get(position).isExspan=!monthBeans.get(position).isExspan;
//                monthBeanLuAdapter.notifyDataSetChanged();
//            }
//        });
    }

}
