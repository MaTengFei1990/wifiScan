package cn.ucai.networkscan.Utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by sks on 2016/7/17.
 */
public class WifiAdmin {
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private List<ScanResult> mWifiList;
    private List<WifiConfiguration> mWifiConfiguration;
    WifiManager.WifiLock mWifiLock;

    public WifiAdmin(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
    }

    public void openWifi() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }
    //关闭wifi
    public void closeWifi() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }
    //检查wifi的状态
    public int  checkState() {
        return wifiManager.getWifiState();
    }
    //锁定wifilock
    public void acquireWifiLock() {
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }
    //创建一个wifilock
    public void createwifiLock(){
        mWifiLock = wifiManager.createWifiLock("test");
    }
    public List<WifiConfiguration> getConfiguration(){
        return mWifiConfiguration;
    }

    //指定配置好的网络进行连接
    public void connetionConfiguration(int index) {
        if (index > mWifiConfiguration.size()) {
            return;
        }
        wifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);
        
    }
    
    
    public void startScan(){
        wifiManager.startScan();
        mWifiList = wifiManager.getScanResults();
        mWifiConfiguration = wifiManager.getConfiguredNetworks();
    }
    public List<ScanResult> getWifiList(){
        return mWifiList;
    }

    public StringBuffer lookUpScan(){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<mWifiList.size();i++) {
            sb.append("Index_" + new Integer(i + 1).toString() + ":");
            sb.append((mWifiList.get(i)).toString()).append("\n");
        }
        return sb;

    }
    public String getMacAddress(){
        return (wifiInfo==null)?null:wifiInfo.getMacAddress();
    }
    public String getBSSID(){
        return (wifiInfo==null)?null:wifiInfo.getBSSID();
    }
    public int getIpAddress(){
        return (wifiInfo == null) ? 0 : wifiInfo.getIpAddress();
    }

    public int getNetWorkId(){
         return (wifiInfo == null) ? 0 : wifiInfo.getNetworkId();
    }
    public String getWifiInfo(){
         return (wifiInfo == null) ? null : wifiInfo.toString();
    }


    public void addNetWork(WifiConfiguration configuration){
        int wcgId = wifiManager.addNetwork(configuration);
        wifiManager.enableNetwork(wcgId, true);

    }
    public void disConnectionWifi(int  netId){
         wifiManager.disableNetwork(netId);
        wifiManager.disconnect();

    }
}
