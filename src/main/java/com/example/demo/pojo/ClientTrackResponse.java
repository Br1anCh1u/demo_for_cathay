package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
public class ClientTrackResponse<T> implements Serializable {
    private String traceId;
    private List<ClientTrack> serviceResponseTime;
    private T result;
}
