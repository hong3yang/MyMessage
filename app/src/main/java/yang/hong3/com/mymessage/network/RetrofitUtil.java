package yang.hong3.com.mymessage.network;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by hong3 on 2017-1-10.
 */

public class RetrofitUtil {

    public static <T>T create(Class<T> service,String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody responseBody) throws IOException {
                                return responseBody.string();
                            }
                        };
                    }
                })
                .build();

        return retrofit.create(service);
    }

//    public static <T>T createMusic(Class<T> service,String baseUrl){
//        final Retrofit.Builder builder = new Retrofit.Builder();
//        builder.baseUrl(baseUrl);
//        builder.callFactory(new Call.Factory() {
//            @Override
//            public Call newCall(Request request) {
//                request.headers().newBuilder().add("apikey", "97cc98fd7d48f26f7a781f1acaf2ace9").build();
//
//                return this.newCall(request);
//            }
//        });
//        builder.client(new OkHttpClient.Builder()
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .build());
//        builder.addConverterFactory(new Converter.Factory() {
//            @Override
//            public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//                return new Converter<ResponseBody, String>() {
//                    @Override
//                    public String convert(ResponseBody responseBody) throws IOException {
//                        return responseBody.string();
//                    }
//                };
//            }
//        });
//        Retrofit retrofit = builder.build();
//
//        return retrofit.create(service);
//    }


}
