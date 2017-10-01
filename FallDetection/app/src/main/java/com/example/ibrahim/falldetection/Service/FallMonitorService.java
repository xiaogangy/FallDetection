package com.example.ibrahim.falldetection.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.hardware.SensorEventListener;
import android.telephony.SmsManager;

import com.example.ibrahim.falldetection.FileService;
import com.example.ibrahim.falldetection.MainActivity;
import com.example.ibrahim.falldetection.R;

public class FallMonitorService extends Service implements SensorEventListener{


    private FileService fileService = null;
    private String[] phoneList = null;
    private SensorManager accManager;
    private Sensor accSensor;
    private float gravity[] = new float[3];
    private float linear_acceleration[] = new float[3];
    private NotificationCompat.Builder builder;

    public FallMonitorService() {
    }


    public void setPhoneList(String[] phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    private final BinderService binder= new BinderService();
    public class BinderService extends Binder {
        public FallMonitorService getService(){
            return FallMonitorService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化通讯录列表
        fileService = new FileService(this);
        String phone = fileService.readContentFromFile("Phone.txt");
        if (phone.length()>0){
            phoneList = phone.split("\\.");
        }

        // 系统通知栏的设置
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("HealthCare System")
                .setContentText("HealthCare System is running")
                .setSmallIcon(R.mipmap.ic_launcher);
        //点击通知栏返回应用程序主界面
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this,0,notifyIntent,0);//PendingIntent.FLAG_UPDATE_CURRENT
        builder.setContentIntent(notifyPendingIntent);
        //创建notification,启动前台服务
        Notification notification = builder.build();
        startForeground(1, notification);

        accManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = accManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);

        System.out.println("---------------------->"+"Service start");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accManager.unregisterListener(this, accSensor);
        System.out.println("---------------------->"+"Service destroy");

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float alpha = 0.8f;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

        linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

        if(checkFall(linear_acceleration)){
            sendNotification();
//            Intent i = new Intent(FallMonitorService.this, MainActivity.class);
//            startActivity(i);

            if(phoneList.length>0){
                for (String phoneNumber : phoneList){
                    sendMessage(phoneNumber);
                }
            }
            System.out.println("have sent message");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public boolean checkFall(float[] data) {

        if (Math.abs(data[0]) > 9.8) {
            System.out.println("x:"+ data[0]);
            return true;
        }
        if (Math.abs(data[1]) > 9.8) {
            System.out.println("y:" + data[1]);
            return true;
        }
        if (Math.abs(data[2]) > 9.8) {
            System.out.println("z:" + data[2]);
            return true;
        }
        return false;
    }

    public void sendMessage(String phoneNumber){

        //get location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        String context = "I fell at ----- "+ "longitude:" + loc.getLatitude()+"\nlatitude:"
                + loc.getLongitude()+"\nPlease help me !";
        System.out.println(context);

        //send message
        PendingIntent paIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, context, paIntent,null);
    }


    private void sendNotification(){

        builder.setContentTitle("HealthCare System")
                .setContentText("Fall Detected!")
                .setSmallIcon(R.mipmap.ic_launcher);

//        Intent notifyIntent = new Intent(FallMonitorService.this, MainActivity.class);
//        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(notifyPendingIntent);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        Notification notification = builder.build();
//        notification.defaults = Notification.DEFAULT_SOUND;

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}
