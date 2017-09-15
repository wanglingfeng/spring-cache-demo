package com.joseph.demo.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lfwang on 2017/6/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private int id;
    private String name;
}
