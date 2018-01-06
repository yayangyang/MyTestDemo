package mobiesafe74.itheima.com.dongtaiquanxian2;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class duquActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duqu);

        Lutil.e(getCacheDir().getPath()+"wwwwwww");
        Lutil.e(getCacheDir().getAbsolutePath()+"wwwwwww");
    }

    private void insertDummyContact() {
        InputStream is=null;
        String str="";
        String fileName="a.txt";
        File file=new File(getCacheDir().getPath(),fileName);
        if(file.exists()){
            byte[] buf=new byte[128];
            FileInputStream fis=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            StringBuffer sf=new StringBuffer();
            try {
                fis=new FileInputStream(file);
                isr=new InputStreamReader(fis);
                br=new BufferedReader(isr);
                while(!TextUtils.isEmpty(str=br.readLine())){
                    Lutil.e("www");
                    sf.append(str);
                }
                Lutil.e(sf.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    br.close();
                    isr.close();
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = ActivityCompat.checkSelfPermission(duquActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        Log.e("lllll",shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)+"");
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(duquActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageOKCancel("You need to allow access to Contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("ww","onClick111");
                                ActivityCompat.requestPermissions(duquActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        }
                );
                return;
            }
            Lutil.e("运行了");
            ActivityCompat.requestPermissions(duquActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        insertDummyContact();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(duquActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(duquActivity.this, "WRITE_EXTERNAL_STORAGE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void duqu(View view){
        insertDummyContactWrapper();
    }

}
