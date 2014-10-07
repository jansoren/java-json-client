package no.bouvet.jsonclient.builders;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

public class HttpSSLClientBuilder implements Builder<HttpClient> {

    private String username;
    private String password;

    public HttpClient build() {
        SSLConnectionSocketFactory sslConnectionSocketFactory = createSSLConnectionSocketFactory();
        CredentialsProvider credentialsProvider = createCredentialsProvider(username, password);
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).setDefaultCredentialsProvider(credentialsProvider).build();
    }

    public HttpSSLClientBuilder withAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    private CredentialsProvider createCredentialsProvider(String username, String password) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        return credentialsProvider;
    }

    private SSLConnectionSocketFactory createSSLConnectionSocketFactory() {
        return new SSLConnectionSocketFactory(createSSLContext(), new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }

    private SSLContext createSSLContext() {
        try {
            return SSLContexts.custom().loadTrustMaterial(createKeyStore(), new TrustSelfSignedStrategy()).build();
        } catch (Exception e) {
            throw new RuntimeException("Could not create SSL context", e);
        }
    }

    private KeyStore createKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            return keyStore;
        } catch (Exception e) {
            throw new RuntimeException("Could not create key store", e);
        }
    }
}
