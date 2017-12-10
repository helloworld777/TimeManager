package com.tm.lyw.timemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tm.lyw.timemanager.adapter.CommonAdapter;
import com.tm.lyw.timemanager.adapter.LuAdapter;
import com.tm.lyw.timemanager.adapter.MultiItemTypeAdapter;
import com.tm.lyw.timemanager.adapter.base.ViewHolder;
import com.tm.lyw.timemanager.db.DaoMaster;
import com.tm.lyw.timemanager.db.DaoSession;
import com.tm.lyw.timemanager.db.TimeBean;
import com.tm.lyw.timemanager.db.TimeBeanDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView recyclerView;
    private List<TimeBean> timeBeanList=new ArrayList<>();
    private int timeFormat=0;
    private final int NONE=0,NORMAL=1,WARN=2,GREAT=3;
    DaoMaster.DevOpenHelper openHelper;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private int selecteIndex;

    AlertDialog alertDialog;
    CommonAdapter commonAdapter;
    private LuAdapter<TimeBean> adapter;
    TextView tvDate;
    private long time=DateUtil.getToday();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         time=getIntent().getLongExtra("time",0);
        if (time==0){
            time=DateUtil.getToday();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,HistoryActivity.class));
            }
        });

        recyclerView=findViewById(R.id.recyclerView);

        commonAdapter=new CommonAdapter<TimeBean>(this,R.layout.item_recyc,timeBeanList) {
            @Override
            protected void convert(ViewHolder holder, TimeBean o, int position) {
                holder.setText(R.id.tvTime,o.getTime());
                View view=holder.getView(R.id.tvColor   );
                switch (o.getType()) {
                    case NONE:
                        view.setBackgroundColor(Color.WHITE);
                        break;
                    case NORMAL:
                        view.setBackgroundColor(Color.BLUE);
                        break;
                    case WARN:
                        view.setBackgroundColor(Color.RED);
                        break;
                    case GREAT:
                        view.setBackgroundColor(Color.GREEN);
                        break;
                }

            }
        };
        adapter=new LuAdapter<TimeBean>(this,timeBeanList,R.layout.item_recyc) {
            @Override
            public void convert(ViewHolder holder, int position) {

                TimeBean o=timeBeanList.get(position);
                holder.setText(R.id.tvTime,o.getTime());
                View view=holder.getView(R.id.tvColor   );
                switch (o.getType()) {
                    case NONE:
                        view.setBackgroundColor(Color.WHITE);
                        break;
                    case NORMAL:
                        view.setBackgroundColor(Color.BLUE);
                        break;
                    case WARN:
                        view.setBackgroundColor(Color.RED);
                        break;
                    case GREAT:
                        view.setBackgroundColor(Color.GREEN);
                        break;
                }
            }
        };
        recyclerView.setAdapter(adapter);


        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecteIndex=position;
                alertDialog.show();
            }
        });


        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                selecteIndex=position;
                alertDialog.show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        tvDate=findViewById(R.id.tvDate);
        findViewById(R.id.tvNext).setOnClickListener(this);
        findViewById(R.id.tvPre).setOnClickListener(this);

        openHelper=new DaoMaster.DevOpenHelper(this,"time_manage_db");
        if (daoMaster==null){
            daoMaster=new DaoMaster(openHelper.getWritableDb());
        }
        daoSession=daoMaster.newSession();
        initDialog();
        initData();
    }
    private void initDialog(){
        String[] items={"非常好","没啥事","正常咯"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                TimeBean timeBean=timeBeanList.get(selecteIndex);
                switch (which){
                    case 0:
                        timeBeanList.get(selecteIndex).setType(GREAT);
                        break;
                    case 1:
                        timeBeanList.get(selecteIndex).setType(WARN);
                        break;
                    case 2:
                        timeBeanList.get(selecteIndex).setType(NORMAL);
                        break;
                }
                commonAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                WhereCondition whereCondition= TimeBeanDao.Properties.Date.eq(timeBean.date);
                WhereCondition whereConditio2n=TimeBeanDao.Properties.SortIndex.eq(timeBean.sortIndex);
                Log.d(getClass().getSimpleName(),""+timeBean.toString());
//                daoSession.getTimeBeanDao().insert(timeBean);
            }
        });

        alertDialog=builder.create();
    }
    private void initData(){
        //20171210
        tvDate.setText(time%10000/100+"-"+time%100);

        WhereCondition whereCondition= TimeBeanDao.Properties.Date.eq(time);
        timeBeanList.clear();
        timeBeanList.addAll(daoSession.getTimeBeanDao().queryBuilder().where(whereCondition).build().list());

        Log.d(this.getClass().getSimpleName(),timeBeanList.toString());
        Log.d(this.getClass().getSimpleName(),daoSession.getTimeBeanDao().queryBuilder().build().list().toString());

        Log.d(this.getClass().getSimpleName(),"-------------------------------");
        StringBuffer stringBuffer=new StringBuffer();
        for (int i=8;i<=23;i++){
            TimeBean timeBean=new TimeBean();
            stringBuffer.delete(0,stringBuffer.length());
            stringBuffer.append(i);
            stringBuffer.append(":00~");
            stringBuffer.append(i+1);
            stringBuffer.append(":00");
            timeBean.setTime(stringBuffer.toString());
            timeBean.date=(DateUtil.getToday());
            timeBean.sortIndex=i;
            if (!timeBeanList.contains(timeBean)){
                timeBeanList.add(timeBean);
            }
        }
        Log.d(this.getClass().getSimpleName(),timeBeanList.toString());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(),"onDestroy");
        if (time==DateUtil.getToday()){

//            daoSession.getTimeBeanDao().deleteAll();
            daoSession.getTimeBeanDao().queryBuilder().where(TimeBeanDao.Properties.Date.eq(DateUtil.getToday())).buildDelete().executeDeleteWithoutDetachingEntities();
            Log.d(getClass().getSimpleName(),""+timeBeanList.toString());
            daoSession.getTimeBeanDao().insertInTx(timeBeanList);
        }

    }

    @Override
    public void onClick(View v) {
        Calendar calendar=Calendar.getInstance();
        int year=(int) time/10000;
        int month=(int)time%10000/100;
        int day=(int)time%100;
        Log.d(getClass().getSimpleName(),"year:"+year+",month:"+month+",day:"+day);
        calendar.set(year,month-1,day);
        switch (v.getId()){
            case R.id.tvPre:
                calendar.add(Calendar.DATE,-1);
                break;
            case R.id.tvNext:
                calendar.add(Calendar.DATE,1);
                break;
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        time=Integer.parseInt(format.format(calendar.getTime()));
        initData();
    }
}
