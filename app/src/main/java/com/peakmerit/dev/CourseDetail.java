package com.peakmerit.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.peakmerit.dev.model.CourseDataClass;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.time.Duration;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetail extends AppCompatActivity implements PaymentResultListener {

    Button btnEnroll, btnGoto;
    CourseDataClass courseData;
    String courseId, userId;

    TextView textTitle, textTags, textAuthor, textDetails;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        btnEnroll = findViewById(R.id.btnEnroll);
        btnGoto = findViewById(R.id.btnGotoContent);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        textTitle = findViewById(R.id.courseDetailTitle);
        textAuthor = findViewById(R.id.courseDetailAuthor);
        textTags = findViewById(R.id.courseDetailTags);
        textDetails = findViewById(R.id.courseDetailDesc);

        String EnrollStatus = getIntent().getStringExtra("isEnrolled");
        courseId = getIntent().getStringExtra("courseId");

        Log.d("Enrolled Status", EnrollStatus);

        if(!EnrollStatus.equals("Enrolled")){
            btnGoto.setVisibility(View.GONE);
        }
        else{
            btnEnroll.setVisibility(View.GONE);
            btnGoto.setVisibility(View.VISIBLE);
        }

        Call<CourseDataClass> fetchData = RetrofitClient.getInstance().getApi().fetchCourseDetail(courseId);

        fetchData.enqueue(new Callback<CourseDataClass>() {
            @Override
            public void onResponse(Call<CourseDataClass> call, Response<CourseDataClass> response) {
                Log.d("Name", response.body().toString());
                courseData = response.body();

                textTitle.setText(courseData.getName());
                textAuthor.setText(courseData.getAuthor());
                textDetails.setText(courseData.getDescription());
                if(courseData.getCharge().equals("Free")){
                    btnEnroll.setText("Enroll Free");
                }
                else{
                    btnEnroll.setText(courseData.getCharge());
                }

                ArrayList<String> tags= courseData.getTags();

                for(int i = 0; i<tags.size(); i++){
                    textTags.append(tags.get(i) + " ");
                }
            }

            @Override
            public void onFailure(Call<CourseDataClass> call, Throwable t) {
                Log.d("DetailError", t.getMessage());
                Toast.makeText(CourseDetail.this, "Error Occurred, Try again Later", Toast.LENGTH_SHORT).show();
            }
        });

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnEnroll.getText().toString().equals("Enroll Free")){
                    Call<ResponseBody> enroll = RetrofitClient.getInstance().getApi().enroll(userId, courseId);
                    enroll.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String result = response.body().toString();
                            Log.d("Enroll Success", result);

                            if(result.equals("Done")){
                                Intent i = new Intent(CourseDetail.this, ContentActivity.class);
                                i.putExtra("courseId", courseId);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(CourseDetail.this, "Some Error Occurred, Please Try Later", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    String charge = btnEnroll.getText().toString();

                    int amount = Math.round(Float.parseFloat(charge) * 100);

                    Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_test_jfowCQVRriHIpx");

                    JSONObject object = new JSONObject();
                    try{
                        object.put("name", "Peak Merit");
                        object.put("description", "Test payment");

                        // to set theme color
                        object.put("theme.color", "");

                        // put the currency
                        object.put("currency", "INR");

                        // put amount
                        object.put("amount", amount);

                        // put mobile number
                        object.put("prefill.contact", "9284064503");

                        // put email
                        object.put("prefill.email", "chaitanyamunje@gmail.com");

                        // open razorpay to checkout activity
                        checkout.open(CourseDetail.this, object);
                    }
                    catch(Exception ex){

                    }
                }

            }
        });




        btnGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ContentActivity.class);
                i.putExtra("courseId", courseId);
                v.getContext().startActivity(i);
            }
        });
    }


    @Override
    public void onPaymentSuccess(String s) {


        Call<ResponseBody> enroll = RetrofitClient.getInstance().getApi().enroll(userId, courseId);
        enroll.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = response.body().toString();
                Log.d("Enroll Success", result);

                if(result.equals("Done")){
                    Intent i = new Intent(CourseDetail.this, ContentActivity.class);
                    i.putExtra("courseId", courseId);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CourseDetail.this, "Some Error Occurred, Please Try Later", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Some error occurred, Please try again later!", Toast.LENGTH_SHORT).show();
    }
}