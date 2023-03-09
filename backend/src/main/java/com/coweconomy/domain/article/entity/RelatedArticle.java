package com.coweconomy.domain.article.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@DynamicInsert
public class RelatedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relatedArticleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @NotNull
    private Article article;

    @NotNull
    private Long recommend;

}
