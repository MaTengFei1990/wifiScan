package cn.ucai.networkscan;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.ucai.networkscan.Utils.WifiAdmin;

public class MainActivity extends AppCompatActivity {
    private TextView allNetWork;
    private Button scan;
    private Button start;
    private Button stop;
    private Button check;

    private WifiAdmin mWifiAdmin;


    private List<ScanResult>list;
    private ScanResult mScanResult;
    private StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiAdmin = new WifiAdmin(MainActivity.this);
        initView();
    }

    private void initView() {
        allNetWork = (TextView) findViewById(R.id.allNetWork);
        scan = (Button) findViewById(R.id.scan);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        check = (Button) findViewById(R.id.check);

        scan.setOnClickListener(new MyListener());
        start.setOnClickListener(new MyListener());
        stop.setOnClickListener(new MyListener());
        check.setOnClickListener(new MyListener());
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.scan:
                    getAllNetWorkList();
                    break;
                case R.id.start:
                    mWifiAdmin.openWifi();
                    break;
                case R.id.stop:
                    mWifiAdmin.closeWifi();
                    Toast.makeText(MainActivity.this, "当前wifi状态为："+mWifiAdmin.checkState(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.check:
                    Toast.makeText(MainActivity.this, "当前wifi状态为："+mWifiAdmin.checkState(), Toast.LENGTH_LONG).show();

                    break;
                default:
                    break;
            }
        }
    }

    private void getAllNetWorkList() {
        if (sb != null) {
            sb = new StringBuffer();
        }
        mWifiAdmin.startScan();
        list = mWifiAdmin.getWifiList();
        if (list != null) {
            for(int i=0;i<list.size();i++) {
                mScanResult = list.get(i);
                Log.e("maim", "mScanResult=" + mScanResult);
                sb = sb.append(mScanResult.BSSID + "1")
                        .append(mScanResult.SSID + "2")
                        .append(mScanResult.capabilities + "3")
                        .append(mScanResult.frequency + "4")
                        .append(mScanResult.level + "5");
            }
            allNetWork.setText("扫描到的wifi网络：\n"+sb.toString());
        }
    }
}
