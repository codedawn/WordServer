package com.codedawn.word.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SynchronizeRecord {
    private Integer id;
    private String userId;
    private String url;
    private Long timestamp;
}
