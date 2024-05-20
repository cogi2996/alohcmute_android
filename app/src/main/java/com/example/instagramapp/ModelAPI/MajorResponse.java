package com.example.instagramapp.ModelAPI;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MajorResponse {
    private String message;
    private List<Major> data;
}
