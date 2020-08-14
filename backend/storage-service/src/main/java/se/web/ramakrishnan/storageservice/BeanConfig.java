package se.web.ramakrishnan.storageservice;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Configuration
public class BeanConfig {
    @Autowired
    private Environment env;
    @Bean
    public CloudStorageAccount cloudStorageAccount(){
        String property = env.getProperty("azure.storage.ConnectionString");
        CloudStorageAccount account = null;
        try {
            account = CloudStorageAccount.parse(env.getProperty("azure.storage.ConnectionString"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Bean
    public CloudBlobClient cloudBlobClient(){
        return cloudStorageAccount().createCloudBlobClient();
    }

    @Bean
    public CloudBlobContainer cloudBlobContainer(){
        String containerName = env.getProperty("azure.storage.container.name");
        CloudBlobContainer container = null;
        try {
            container = cloudBlobClient().getContainerReference(containerName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }

        return container;
    }
}
