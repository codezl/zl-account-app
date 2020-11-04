package work.admin.example.com.accoutingapp;

import android.icu.text.AlphabeticIndex;
import android.util.Log;

import java.util.UUID;

public class RecordBean {

    private static  String TAG = "RecordBean";
    public  enum RecordType{
        RECORD_TYPE_EXPENSE,RECORD_TYPE_INCOM
    }



    private double amount;
    private RecordType type;
    private String category;
    private String remark;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String date;

    private long timeStamp;
    private String uuid;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        if(this.type == RecordType.RECORD_TYPE_EXPENSE){
            return 1;
        }

        else {
        return 2;
        }
    }

    public void setType(int type) {
       if(type==1){
           this.type = RecordType.RECORD_TYPE_EXPENSE;}

           else{
        this.type = RecordType.RECORD_TYPE_INCOM;
       }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public RecordBean(){
        uuid = UUID.randomUUID().toString();
        timeStamp = System.currentTimeMillis();//返回1970到现在时间
        date = Datefor.getFormattedDate();

    }

}
