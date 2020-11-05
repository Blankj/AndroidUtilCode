package com.blankj.subutil.util.http;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/20
 * </pre>
 */
public final class SSLConfig {

    SSLSocketFactory mSSLSocketFactory;
    HostnameVerifier mHostnameVerifier;

    public SSLConfig(@NonNull SSLSocketFactory factory, @NonNull HostnameVerifier verifier) {
        mSSLSocketFactory = factory;
        mHostnameVerifier = verifier;
    }

    public static final HostnameVerifier DEFAULT_VERIFIER           = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
    public static final SSLSocketFactory DEFAULT_SSL_SOCKET_FACTORY = new DefaultSSLSocketFactory();

    public static final SSLConfig DEFAULT_SSL_CONFIG = new SSLConfig(DEFAULT_SSL_SOCKET_FACTORY, DEFAULT_VERIFIER);

    private static class DefaultSSLSocketFactory extends SSLSocketFactory {

        private static final String[]       PROTOCOL_ARRAY;
        private static final TrustManager[] DEFAULT_TRUST_MANAGERS;

        static {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PROTOCOL_ARRAY = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"};
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                PROTOCOL_ARRAY = new String[]{"SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"};
            } else {
                PROTOCOL_ARRAY = new String[]{"SSLv3", "TLSv1"};
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DEFAULT_TRUST_MANAGERS = new TrustManager[]{
                        new X509ExtendedTrustManager() {
                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {/**/}

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) {/**/}

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) {/**/}

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) {/**/}

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {/**/}

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {/**/}
                        }
                };
            } else {
                DEFAULT_TRUST_MANAGERS = new TrustManager[]{
                        new X509TrustManager() {
                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {/**/}

                            @SuppressLint("TrustAllX509TrustManager")
                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) {/**/}

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }
                        }
                };
            }

        }

        private SSLSocketFactory mFactory;

        DefaultSSLSocketFactory() {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, DEFAULT_TRUST_MANAGERS, new SecureRandom());
                mFactory = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new AssertionError();
            }
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return mFactory.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return mFactory.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            Socket ssl = mFactory.createSocket(s, host, port, autoClose);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            Socket ssl = mFactory.createSocket(host, port);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            Socket ssl = mFactory.createSocket(host, port, localHost, localPort);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            Socket ssl = mFactory.createSocket(host, port);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            Socket ssl = mFactory.createSocket(address, port, localAddress, localPort);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket() throws IOException {
            Socket ssl = mFactory.createSocket();
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        private void setSupportProtocolAndCipherSuites(Socket socket) {
            if (socket instanceof SSLSocket) {
                ((SSLSocket) socket).setEnabledProtocols(PROTOCOL_ARRAY);
            }
        }
    }
}
