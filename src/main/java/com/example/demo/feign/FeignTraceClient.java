package com.example.demo.feign;

import com.example.demo.util.ClientTrackDataUtil;
import feign.Client;
import feign.Request;
import feign.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FeignTraceClient implements Client {

    private final Client delegate;

    public FeignTraceClient() {
        this.delegate = new Client.Default(null, null);
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        long start = System.currentTimeMillis();
        Response response = delegate.execute(request, options);
        long end = System.currentTimeMillis();

        ClientTrackDataUtil.addTrace(
                request.url(),
                request.httpMethod().name(),
                end - start,
                response.status()
        );

        return response;
    }
}
