package fi.csc.data;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;

import java.io.FileOutputStream;
import java.io.IOException;


/*import software.amazon.awssdk.transfer.s3.model.CompletedFileDownload;
import software.amazon.awssdk.transfer.s3.model.DownloadFileRequest;
import software.amazon.awssdk.transfer.s3.model.FileDownload;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.progress.LoggingTransferListener;*/
import org.jboss.logging.Logger;

/**

 */
public class App  {
    static private final S3Client s3 = DependencyFactory.s3Client();
    //static private final SdkAsyncHttpClient async =  DependencyFactory.s3AsyncClient();
    static private final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {

        String bucketName = args[0]; //"pj-2004842-pub";
        //String key = args[1]; //DSC08547.JPG
        /*ListBucketsResponse listBucketsResponse = s3.listBuckets();
         Bucket b = listBucketsResponse.buckets().stream().
                filter(x -> x.name().equals(bucketName)).findFirst().orElse(null)
;
        if (null != b) {
            log.info("Buketti löytyi: "+b.name()+ b.toString());*/
        ListObjectsV2Request listObjects = ListObjectsV2Request
                .builder()
                .bucket(bucketName)
                .build();

           ListObjectsV2Response res = s3.listObjectsV2(listObjects);
            S3Object file = res.contents().get(0);
            if (file.key().equals("DSC08547.JPG"))
                file = res.contents().get(1);
        long alku = System.currentTimeMillis() ;
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(file.key())
                .build();
        ResponseInputStream<GetObjectResponse> ris = s3.getObject( request);
         long bytes = 0;
        try {
            FileOutputStream fout = new FileOutputStream(file.key());
            bytes = ris.transferTo(fout);
            if (file.size() == bytes)
                log.info("Kopionti onnistui: " + bytes + " bytes");
            else
                log.info("Lataus epäonnistui: " + bytes + "!=" + file.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double aika = System.currentTimeMillis() - alku;
        log.info("Time ms: "+ aika + " kB/s: "+ (bytes/aika));

        //}

    }

}
