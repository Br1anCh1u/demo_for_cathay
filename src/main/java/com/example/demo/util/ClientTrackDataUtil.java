package com.example.demo.util;

import com.example.demo.pojo.ClientTrack;

import java.util.ArrayList;
import java.util.List;

public class ClientTrackDataUtil {

    private static final ThreadLocal<List<ClientTrack>> TRACE_HOLDER = ThreadLocal.withInitial(ArrayList::new);

    public static void addTrace(String url, String method, long duration, int statusCode) {
        ClientTrack clientTrack = new ClientTrack();
        clientTrack.setHttpMethod(method);
        clientTrack.setUrl(url);
        clientTrack.setDuration(duration + " ms");
        clientTrack.setStatusCode(statusCode);
        TRACE_HOLDER.get().add(clientTrack);

    }

    public static List<ClientTrack> getTrace() {
        return TRACE_HOLDER.get();
    }

    public static void clear() {
        TRACE_HOLDER.remove();
    }
}
