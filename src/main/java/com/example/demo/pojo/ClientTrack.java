package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientTrack implements Serializable {
    private String httpMethod;
    private String url;
    private String duration;
    private int statusCode;
}
