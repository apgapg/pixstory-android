package com.jullae.retrofit;


import com.jullae.BuildConfig;
import com.jullae.constant.AppConstant;
import com.jullae.ui.fragments.HomeFeedFragments;
import com.jullae.utils.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Developer: Saurabh Verma
 * Dated: 27-09-2016.
 */
public final class RestClient {
    private static final Integer BKS_KEYSTORE_RAW_FILE_ID = 0;
    private static final Integer SSL_KEY_PASSWORD_STRING_ID = 0;
    private static final int TIMEOUT = 40;

    private static Retrofit retrofit = null;
    private static Retrofit retrofitGoogle = null;
    private static Retrofit retrofitPaystack = null;

    /**
     * Empty Constructor
     * not called
     */
    private RestClient() {
    }


//    /**
//     * Returns the instance of Retrofit client
//     *
//     * @return returns the RetrofitClient instance
//     */
//    public static ApiInterface getGoogleApiInterface() {
//        if (retrofitGoogle == null) {
//            retrofitGoogle = new Retrofit.Builder()
//                    .baseUrl(BuildConfig.GOOGLE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient().build())
//                    //.client(secureConnection().build())
//                    .build();
//        }
//        return retrofitGoogle.create(ApiInterface.class);
//    }
//
//
//    /**
//     * Returns the instance of Retrofit client
//     *
//     * @return returns the RetrofitClient instance
//     */
//    public static ApiInterface getPayStackApiInterface() {
//        if (retrofitPaystack == null) {
//            retrofitPaystack = new Retrofit.Builder()
//                    .baseUrl(BuildConfig.PAYSTACK_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClientPayStack().build())
//                    .build();
//        }
//        return retrofitPaystack.create(ApiInterface.class);
//    }

    /**
     * Gets api interface.
     *
     * @return object of ApiInterface
     */
    public static ApiInterface getApiInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://54.254.240.3:80/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    //.client(secureConnection().build())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


    /**
     * Gets retrofit builder.
     *
     * @return object of Retrofit
     */
    static Retrofit getRetrofitBuilder() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    .build();
        }
        return retrofit;
    }

//    /**
//     * @return object of OkHttpClient.Builder
//     */
//    private static OkHttpClient.Builder httpClient() {
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS);
//        httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
//        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
//
//        // add your other interceptors …
//        // add logging as last interceptor
//        httpClient.addInterceptor(getLoggingInterceptor());
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(final Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("content-language", "en")
//                        .addHeader("token", "cxcxcxcxcxcxcxcxc")
//                        .build();
//                return chain.proceed(request);
//            }
//        });
//        return httpClient;
//    }


    /**
     * @return object of OkHttpClient.Builder
     */
    private static OkHttpClient.Builder httpClient() {

        OkHttpClient.Builder httpClient = httpClientPayStack();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(final Chain chain) throws IOException {
                String basic = "%s";
                String authToken = "BEARER "+ HomeFeedFragments.token;
                Request request = chain.request().newBuilder()
                        .header(AppConstant.AUTH, String.format(basic, authToken))
                        .build();
                return chain.proceed(request);

            }
        });
        return httpClient;
    }

    /**
     * @return object of OkHttpClient.Builder
     */
    private static OkHttpClient.Builder httpClientPayStack() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClient.addInterceptor(getLoggingInterceptor());
        return httpClient;
    }

    /**
     * Method to get object of HttpLoggingInterceptor
     *
     * @return object of HttpLoggingInterceptor
     */
    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if (Log.PRINT) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return logging;
    }

//    /**
//     * Method to create secure connection of api call with out own certificate
//     *
//     * @return object of OkHttpClient.Builder
//     * @throws KeyStoreException        throws exception related to key store
//     * @throws CertificateException     throws exception related to certificate
//     * @throws NoSuchAlgorithmException throws exception if also not found
//     * @throws IOException              throws IO exception
//     * @throws KeyManagementException   throws key related exception
//     */
//    private static OkHttpClient.Builder secureConnection() throws
//            KeyStoreException,
//            CertificateException,
//            NoSuchAlgorithmException,
//            IOException,
//            KeyManagementException {
//        InputStream certificateInputStream = null;
//        certificateInputStream = MyApplication.getAppContext().getResources().openRawResource(BKS_KEYSTORE_RAW_FILE_ID);
//        KeyStore trustStore = KeyStore.getInstance("BKS");
//        try {
//            trustStore.load(certificateInputStream,
//                    MyApplication.getAppContext().getString(SSL_KEY_PASSWORD_STRING_ID).toCharArray());
//        } finally {
//            certificateInputStream.close();
//        }
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
//        tmf.init(trustStore);
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);

        /*

        retrofit 1.9.x

        OkHttpClient client = new OkHttpClient();

        client.setSslSocketFactory(sslContext.getSocketFactory());

        client.setWriteTimeout(15, TimeUnit.MINUTES);

        client.setConnectTimeout(15, TimeUnit.MINUTES); // connect

        timeout

        client.setReadTimeout(15, TimeUnit.MINUTES);

        return new OkClient(client);*/

    //Retrofit 2.0.x

//        TrustManager[] trustManagers = tmf.getTrustManagers();
//        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
//            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
//        }
//        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
//        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//        OkHttpClient.Builder client3 = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager);
//        client3.addInterceptor(getLoggingInterceptor());
//        return client3;
//    }
}
