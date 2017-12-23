package com.example.asif.filechooserdemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int FILE_REQUEST_CODE=1;


    Button btnSelectBgImage;
    LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSelectBgImage=findViewById(R.id.btnSelectBgImage);
        llMain=findViewById(R.id.llMain);
        btnSelectBgImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FILE_REQUEST_CODE:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    String[] projection={MediaStore.Images.Media.DATA};
                    Cursor c=getContentResolver().query(uri,projection,null,null,null);
                    c.moveToFirst();
                    int columnIndex=c.getColumnIndex(projection[0]);
                    String filePath=c.getString(columnIndex);
                    c.close();
                    Bitmap selectedImage= BitmapFactory.decodeFile(filePath);
                    Drawable d=new BitmapDrawable(selectedImage);
                    llMain.setBackground(d);
                }

                break;
        }
    }
}
