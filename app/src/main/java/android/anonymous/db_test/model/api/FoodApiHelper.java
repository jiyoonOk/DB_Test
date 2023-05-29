package android.anonymous.db_test.model.api;

import static android.anonymous.db_test.model.constants.Constants.*;

import android.anonymous.db_test.model.constants.Constants;
import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

//test api 호출 및 data 받아옴. retrofit2 사용
public class FoodApiHelper {
        private ApiService apiService;

    private static FoodApiHelper instance = null;

    public static FoodApiHelper getInstance(Context context) {
        if(instance == null) {
            synchronized(FoodApiHelper.class) {
                if(instance == null) {
                    instance = new FoodApiHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    //test 생성자 : Retrofit 객체 생성. apiService 인터페이스 초기화.
    public FoodApiHelper(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    //test api 호출 메소드. 음식의 영양성분 정보를 Call<ResponseClass> 타입으로 리턴.
    public Call<ResponseClass> getFoodNtrItdntList(String foodName) {
        return apiService.getFoodNtrItdntList(SERVICE_KEY, foodName, null, null, null, null, "json");
    }

    public ApiService getApiService() {
        return this.apiService;
    }

    //test API 요청을 정의.
    public interface ApiService {

        // GET 요청을 할 URL 구성. GET 요청을 할 메소드를 정의. 메소드의 리턴 타입은 Call<응답 클래스>로 지정.
        @GET("getFoodNtrItdntList1")  // 어노테이션 추가. getFoodNtrItdntList1은 API 메소드 이름

        // 메소드의 매개변수는 쿼리 매개변수로 변환. 쿼리 매개변수는 @Query 어노테이션을 사용하여 정의. 쿼리 매개변수란 URL에 포함되는 매개변수를 의미.
        // Call<응답 클래스>의 응답 클래스는 API 응답을 가진 클래스를 지정. 이 클래스는 JSON 응답을 자동으로 POJO로 변환할 때 사용.
        // Call이란 Retrofit에서 제공하는 인터페이스. 비동기나 동기로 요청을 실행하고 응답을 받을 수 있음.
        Call<ResponseClass> getFoodNtrItdntList(
                @Query("serviceKey") String serviceKey, // string 형식의 쿼리 매개변수 추가. 왜? serviceKey는 string 형식이기 때문. api에서 확인 가능
                @Query("desc_kor") String desc_kor,
                @Query("pageNo") String pageNo,
                @Query("numOfRows") String numOfRows,
                @Query("bgn_year") String bgn_year,
                @Query("animal_plant") String animal_plant,
                @Query("type") String type
        );
    }


    public class ResponseClass {

        // 각 필드에 대응하는 프로퍼티를 정의
        // JSON 필드 이름과 프로퍼티 이름이 같다면, @SerializedName 어노테이션은 생략가능.
        @SerializedName("header")
        private Header header;

        @SerializedName("body")
        private Body body;

        public Body getBody() {
            return body;
        }

        public class Header {
            @SerializedName("resultCode")
            private String resultCode;

            @SerializedName("resultMsg")
            private String resultMsg;

            // getters and setters...
        }

        public class Body {
            @SerializedName("totalCount")
            private int totalCount;

            @SerializedName("items")
            private List<Item> items;

            // getters and setters...
            public List<Item> getItems() {
                return items;
            }

            public class Item {
                @SerializedName("DESC_KOR")
                private String food_name;

                @SerializedName("SERVING_WT")
                private float food_1serving;

                @SerializedName("NUTR_CONT1")
                private float food_kcal;

                @SerializedName("NUTR_CONT2")
                private float food_carbohydrates;

                @SerializedName("NUTR_CONT3")
                private float food_protein;

                @SerializedName("NUTR_CONT4")
                private float food_fat;

                @SerializedName("ANIMAL_PLANT")
                private String food_company;

                // getters and setters...
                public String getFood_name() {
                    return food_name;
                }
                public float getFood_1serving() {
                    return food_1serving;
                }
                public float getFood_kcal() {
                    return food_kcal;
                }
                public float getFood_carbohydrates() {
                    return food_carbohydrates;
                }
                public float getFood_protein() {
                    return food_protein;
                }
                public float getFood_fat() {
                    return food_fat;
                }
                public String getFood_company() {
                    return food_company;
                }

            }
        }
    }
}
