package com.example.yy.test_listview;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DBManager;
import com.example.database.Person;
import com.example.dialog.CreateUserDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyListView extends AppCompatActivity {

    ListView lv;
    Button btn_add;

    DBManager dbMag;

    String[] from={"name","id"};              // 这里是ListView显示内容每一列的列名
    int[] to={R.id.user_name,R.id.user_id};   // item中控件的id

    private List<Map<String, Object>> data;
    MyAdapter adapter = null;

    private CreateUserDialog createUserDialog;

    private List<Map<String, Object>> getData()
    {
        // 初始化的数据从数据库中读取
        List<Person> lp = dbMag.user_getAll();
        int len = lp.size();
        String[] userName = new String[0];
        String[] userId = new String[0];
        if(len > 0) {
            userName = new String[len];
            userId = new String[len];

            for (int i = 0; i < len; i++) {
                Person ptmp = lp.get(i);

                userName[i] = ptmp.sname;
                userId[i] = ptmp.sid;
            }
        }

        // 根据数据库读取的内容，去初始化list
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<len;i++)
        {
            map = new HashMap<String, Object>();
            map.put(from[0], userName[i]);
            map.put(from[1], userId[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);

        dbMag = new DBManager(getApplicationContext()); // 数据库manager初始new出来
        lv = (ListView)findViewById(R.id.abc_lv);
        btn_add = (Button)findViewById(R.id.btn_add);

        data = getData();

        adapter = new MyAdapter(this);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _name = (String)data.get(position).get("name");
                String _id = (String)data.get(position).get("id");

                String _text = "选中的数据项ID是：" + _id + "，名称是：" + _name;

                Toast toast=Toast.makeText(getApplicationContext(), _text, Toast.LENGTH_SHORT);
                toast.show();

                // 删除数据后，同时还要从数据库中删除
                dbMag.user_deleteBySid(_id);

                data.remove(position);// 删除数据后，需要刷新数据
                adapter.notifyDataSetChanged();
            }
        });

        // 弹框
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(), "xxx_xxx_xxx", Toast.LENGTH_SHORT).show();
                createUserDialog = new CreateUserDialog(MyListView.this, R.style.loading_dialog, onClickListener);
                createUserDialog.show();

            }
        });
/*
        ArrayList<HashMap<String,String>> listData = null;
        listData = new ArrayList<HashMap<String,String>>();
        for(int i=0; i<4; i++){
            HashMap<String,String> map=null;
            map=new HashMap<String,String>();       //为避免产生空指针异常，有几列就创建几个map对象
            map.put(from[0], userName[i]);
            map.put(from[1], userId[i]);
            listData.add(map);
        }
        // 创建一个SimpleAdapter对象
        SimpleAdapter adapter = new SimpleAdapter(this, listData, R.layout.item, from, to);

        // 为ListView设置适配器
        lv.setAdapter(adapter);
*/
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_save:

                    // Toast.makeText(getApplicationContext(), "确定数据添加", Toast.LENGTH_SHORT).show();

                    String name1 = createUserDialog.text_name.getText().toString().trim();
                    String mobile1 = createUserDialog.text_mobile.getText().toString().trim();
                    String info1 = createUserDialog.text_info.getText().toString().trim();

                    //String tmp = name1+"——"+mobile1+"——"+info1;

                    // 数据库更新
                    Person per = new Person(name1,mobile1);
                    dbMag.user_insertUser(per);
                    dbMag.user_insertUser(per);

                    // 数据源data更新，同时adapter.notifyDataSetChanged();
                    Map<String, Object> map1;
                    map1 = new HashMap<String, Object>();
                    map1.put(from[0], name1);
                    map1.put(from[1], mobile1);
                    data.add(map1);

                    adapter.notifyDataSetChanged();

                    createUserDialog.dismiss();
                    break;

            }
        }
    };


    static class ViewHolder
    {
        public TextView name;
        public TextView id;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private Context context;
        public MyAdapter(Context context)
        {
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
            return data.size();
        }
        @Override
        public Object getItem(int position) {
            // Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
            return data.get(position);
        }
        @Override
        public long getItemId(int position) {
            // Get the row id associated with the specified position in the list.(取在列表中与指定索引对应的行id)
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if(convertView == null)
            {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.item, null);

                holder.name = (TextView)convertView.findViewById(R.id.user_name);
                holder.id = (TextView)convertView.findViewById(R.id.user_id);

                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.name.setText((String)data.get(position).get("name"));
            holder.id.setText((String)data.get(position).get("id"));

            return convertView;
        }
    }

}
