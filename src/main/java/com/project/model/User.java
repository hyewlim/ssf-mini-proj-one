package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

    private String userName;

}
