package com.dmitri.mobileclientservice.di.modules;

import com.dmitri.mobileclientservice.api.ApiConfig;
import com.dmitri.mobileclientservice.api.BasicAuthInterceptor;
import com.dmitri.mobileclientservice.api.MobileClientService;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Singleton
    @Provides
    public OkHttpClient providesOkhttp()  {
        // Disabling SSL verification for API
        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        };

        try {
            final TrustManager[] trustAllCerts = new TrustManager[] { x509TrustManager };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(new BasicAuthInterceptor(
                    ApiConfig.MOBILE_CLIENT_AUTH_USERNAME, ApiConfig.MOBILE_CLIENT_AUTH_PASSWORD
                ))
                .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.MOBILE_CLIENT_BASE_URL + "/" + ApiConfig.deviceId + "/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    MobileClientService providesApiService(Retrofit retrofit) {
        return retrofit.create(MobileClientService.class);
    }
}
