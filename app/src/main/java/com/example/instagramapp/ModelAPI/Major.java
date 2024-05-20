package com.example.instagramapp.ModelAPI;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Major implements Serializable {
    private int id;
    private String name;
    @Override
    public String toString() {
        return name;
    }
}
