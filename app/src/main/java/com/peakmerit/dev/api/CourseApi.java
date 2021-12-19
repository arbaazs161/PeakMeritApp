package com.peakmerit.dev.api;

import com.peakmerit.dev.model.ContentDataClass;
import com.peakmerit.dev.model.CourseDataClass;
import com.peakmerit.dev.model.UserDataClass;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CourseApi {

    @GET("course/getAll")
    Call<List<CourseDataClass>> getAllCourseId(@Query("user") String userId);

    @GET("course/getAll")
    Call<List<CourseDataClass>> getAllCourses();

    @FormUrlEncoded
    @POST("/user/register")
    Call<ResponseBody> register(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/user/login")
    Call<ResponseBody> login(

            @Field("email") String email,
            @Field("password") String password
    );

    @GET("/course/byUser")
    Call<List<CourseDataClass>> getUserCourse(@Query("user") String userId);

    @GET("/user/courselist")
    Call<List<String>> getCourseList(@Query("user") String userId);

    @GET("/content/getByCourse")
    Call<List<ContentDataClass>> getContents(@Query("id") String courseId);

    @GET("/content/getForVideoView")
    Call<List<ContentDataClass>> getContentForVideoView(@Query("id") String courseId, @Query("priority") int priority);

    @GET("/course/byId")
    Call<CourseDataClass> fetchCourseDetail(@Query("id") String courseId);

    @FormUrlEncoded
    @POST("/course/enroll")
    Call<ResponseBody> enroll(
            @Field("user") String userId,
            @Field("course") String courseId
    );

    @GET("/user/getuser")
    Call<UserDataClass> fetchUser(@Query("id") String userId);
}
