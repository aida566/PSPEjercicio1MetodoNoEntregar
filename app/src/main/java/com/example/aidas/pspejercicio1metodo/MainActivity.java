package com.example.aidas.pspejercicio1metodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;


public class MainActivity extends AppCompatActivity {

    TextView tvIP;
    TextView tvCon;
    Runtime rt;
    Process proceso;

    public static final String LOG = "MITAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIP = findViewById(R.id.tvIP);
        tvCon = findViewById(R.id.tvCon);

        if(haveNetworkConnection()){
            tvCon.setText(R.string.conectado);

        }else if(!haveNetworkConnection()){
            tvCon.setText(R.string.desconectado);

        }

        String[] s = getLocalIpAddress();

        for(String i: s){

            tvIP.setText(tvIP.getText() + "\n" + i);
        }
    }

    private boolean haveNetworkConnection(){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {

            if (ni.getTypeName().equalsIgnoreCase("WIFI")){

                if (ni.isConnected()){
                    haveConnectedWifi = true;
                }

            }

            if (ni.getTypeName().equalsIgnoreCase("MOBILE")){

                if (ni.isConnected()){
                    haveConnectedMobile = true;
                }

            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public String[] getLocalIpAddress() {

        ArrayList<String> addresses = new ArrayList<String>();

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {

                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress()) {

                        addresses.add(inetAddress.getHostAddress().toString());
                    }
                }
            }

        } catch (SocketException ex) {

            Log.v(LOG, ex.toString());

        }

        return addresses.toArray(new String[0]);
    }
}

