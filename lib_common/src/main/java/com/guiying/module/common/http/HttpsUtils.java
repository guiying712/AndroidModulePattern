package com.guiying.module.common.http;

import android.content.Context;
import android.support.annotation.RawRes;
import android.text.TextUtils;

import com.guiying.module.common.utils.CloseUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * <p>Https证书校验工具类</p>
 *
 * @author 张华洋 2017/5/11 16:14
 * @version V1.2.0
 * @name HttpsUtils
 */
public class HttpsUtils {


    public static class SSLParams {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }

    /**
     * @param context   上下文
     * @param bksFileId "XXX.bks"文件(文件位置res/raw/XXX.bks)
     * @param password  The certificate's password.
     * @return SSLParams
     */
    public static SSLParams getSslSocketFactory(Context context, @RawRes int bksFileId, String password, String alias) {
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(alias)) {
            throw new NullPointerException("password == null or alias == null!");
        }
        SSLParams sslParams = new SSLParams();
        try {
            // 创建一个BKS类型的KeyStore，存储我们信任的证书
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(context.getResources().openRawResource(bksFileId), password.toCharArray());
            //通过alias直接从密钥库中读取证书
            Certificate rootCA = clientKeyStore.getCertificate(alias);
            // Turn it to X509 format.
            InputStream certInput = new ByteArrayInputStream(rootCA.getEncoded());
            X509Certificate serverCert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(certInput);
            //关闭流
            CloseUtils.closeIO(certInput);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //用我们之前的keyStore实例初始化TrustManagerFactory，这样trustManagerFactory就会信任keyStore中的证书
            trustManagerFactory.init(clientKeyStore);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());

            X509TrustManager x509TrustManager = new SafeTrustManager(serverCert);

            //创建TLS类型的SSLContext对象，that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");

            //用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            //Android 4.X 对TLS1.1、TLS1.2的支持
            sslParams.sSLSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
            sslParams.trustManager = x509TrustManager;
            return sslParams;
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | UnrecoverableKeyException | IOException | CertificateException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * 不做证书校验，信任所有证书
     */
    public static SSLParams getSslSocketFactoryUnsafe() {
        SSLParams sslParams = new SSLParams();
        try {
            X509TrustManager x509TrustManager = new UnSafeTrustManager();

            //创建TLS类型的SSLContext对象，that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");

            //用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);

            //Android 4.X 对TLS1.1、TLS1.2的支持
            sslParams.sSLSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
            sslParams.trustManager = x509TrustManager;
            return sslParams;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new AssertionError(e);
        }
    }


    /**
     * 主机名校验方法，请把”192.168.0.10”换成你们公司的主机IP：
     */
    public static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if ("192.168.0.10".equals(hostname)) {
                    return true;
                } else {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify(hostname, session);
                }
            }
        };
    }


    /**
     * 对服务器证书域名进行强校验
     */
    private static class SafeTrustManager implements X509TrustManager {
        private X509Certificate mCertificate;

        private SafeTrustManager(X509Certificate serverCert) {
            mCertificate = serverCert;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
            if (x509Certificates == null) {
                throw new IllegalArgumentException("Check Server x509Certificates is null");
            }

            if (x509Certificates.length < 0) {
                throw new IllegalArgumentException("Check Server x509Certificates is empty");
            }

            try {
                for (X509Certificate cert : x509Certificates) {
                    // Make sure that it hasn't expired.
                    cert.checkValidity();
                    //和App预埋的证书做对比
                    cert.verify(mCertificate.getPublicKey());
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
                e.printStackTrace();
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    /**
     * 客户端不对证书做任何验证的做法有很大的安全漏洞。
     */
    private static class UnSafeTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }


    /**
     * 自定义SSLSocketFactory ，实现Android 4.X 对TLSv1.1、TLSv1.2的支持
     */
    private static class Tls12SocketFactory extends SSLSocketFactory {

        private static final String[] TLS_SUPPORT_VERSION = {"TLSv1.1", "TLSv1.2"};

        final SSLSocketFactory delegate;

        private Tls12SocketFactory(SSLSocketFactory base) {
            this.delegate = base;
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
            return patch(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return patch(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return patch(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket patch(Socket s) {
            //代理SSLSocketFactory在创建一个Socket连接的时候，会设置Socket的可用的TLS版本。
            if (s instanceof SSLSocket) {
                ((SSLSocket) s).setEnabledProtocols(TLS_SUPPORT_VERSION);
            }
            return s;
        }
    }

}
