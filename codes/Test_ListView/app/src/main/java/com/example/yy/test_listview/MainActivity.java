package com.example.yy.test_listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.database.DBManager;
import com.example.database.Person;
import com.example.dialog.CreateUserDialog;

import java.util.List;

public class MainActivity extends Activity {

    private CreateUserDialog createUserDialog;

    Button btn;
    void init(){
        btn = (Button)findViewById(R.id.btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast=Toast.makeText(getApplicationContext(), "_t6ftd67yur6", Toast.LENGTH_SHORT);
                //toast.show();

//              List<Person> lp = dbMag.user_getAll();

               // createUserDialog = new CreateUserDialog(MainActivity.this, R.style.loading_dialog, onClickListener);
               // createUserDialog.show();

                 Intent intent = new Intent(MainActivity.this, MyListView.class);
                 startActivity(intent);
            }
        });

    }

//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            switch (v.getId()) {
//
//                case R.id.btn_save:
//
//                    String name1 = createUserDialog.text_name.getText().toString().trim();
//                    String mobile1 = createUserDialog.text_mobile.getText().toString().trim();
//                    String info1 = createUserDialog.text_info.getText().toString().trim();
//
//                    String tmp = name1+"——"+mobile1+"——"+info1;
//
//                    Toast toast=Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT);
//                    toast.show();
//
//                    createUserDialog.dismiss();
//                    break;
//            }
//        }
//    };

}
