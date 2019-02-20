package com.blankj.utilcode.util.http;

import android.os.Build;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/20
 *     desc  :
 * </pre>
 */
public class SslConfig {

    SSLSocketFactory mSSLSocketFactory;
    HostnameVerifier mHostnameVerifier;

    

    static class DefaultSocketFactory extends SSLSocketFactory {

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

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DEFAULT_TRUST_MANAGERS = new TrustManager[]{
                        new X509ExtendedTrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) { /**/ }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) { /**/ }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) { /**/ }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) { /**/ }

                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) { /**/ }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) { /**/ }
                        }
                };
            } else {
                DEFAULT_TRUST_MANAGERS = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) { /**/ }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) { /**/ }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }
                        }
                };
            }

        }

        private SSLSocketFactory delegate;

        DefaultSocketFactory() {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, DEFAULT_TRUST_MANAGERS, new SecureRandom());
                delegate = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new AssertionError();
            }
        }

        public DefaultSocketFactory(SSLSocketFactory factory) {
            this.delegate = factory;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            Socket ssl = delegate.createSocket(s, host, port, autoClose);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            Socket ssl = delegate.createSocket(host, port);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            Socket ssl = delegate.createSocket(host, port, localHost, localPort);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            Socket ssl = delegate.createSocket(host, port);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            Socket ssl = delegate.createSocket(address, port, localAddress, localPort);
            setSupportProtocolAndCipherSuites(ssl);
            return ssl;
        }

        @Override
        public Socket createSocket() throws IOException {
            Socket ssl = delegate.createSocket();
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
