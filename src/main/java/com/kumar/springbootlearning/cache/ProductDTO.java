package com.kumar.springbootlearning.cache;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private String id;
    private String name;
    private String description;
    private String category;
}
