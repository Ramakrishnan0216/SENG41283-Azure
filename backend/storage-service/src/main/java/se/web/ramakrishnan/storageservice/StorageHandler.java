package se.web.ramakrishnan.storageservice;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.AzureSpringBootRequestHandler;
import se.web.ramakrishnan.storageservice.model.Employee;
import se.web.ramakrishnan.storageservice.model.StorageResponse;

import java.util.Optional;

public class StorageHandler extends AzureSpringBootRequestHandler<Employee, StorageResponse> {

    @FunctionName("uploadtoaz")
    public HttpResponseMessage execute(
            @HttpTrigger(name = "request", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<Employee>> request,
            ExecutionContext context) {

        context.getLogger().info("Greeting user name: " + request.getBody().get().toString());
        return request
                .createResponseBuilder(HttpStatus.OK)
                .body(handleRequest(request.getBody().get(), context))
                .header("Content-Type", "application/json")
                .build();
    }

}
