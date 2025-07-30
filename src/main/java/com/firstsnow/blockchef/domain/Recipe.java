package com.firstsnow.blockchef.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recipes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    private String id;

    private String title;
    private String description;
    private String ownerEmail;
    private String blockStructure;
    private List<String> tags;
    private LocalDateTime updatedAt;
}

