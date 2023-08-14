
package fi.csc.data;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

/*import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.core.internal.http.loader.DefaultSdkAsyncHttpClientBuilder;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;*/

/**
 * The module containing all dependencies required by the {@link App}.
 */
public class DependencyFactory {

    private DependencyFactory() {}

    /**
     * @return an instance of S3Client
     */
    public static S3Client s3Client() {
        return S3Client.builder()
                       .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
	    .region(Region.of("US"))
                       .httpClientBuilder(UrlConnectionHttpClient.builder())
	    .endpointOverride(URI.create("https://a3s.fi"))
                       .build();
    }

}
