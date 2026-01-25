package io.ningelschlingel.pca.post.core.domain;

import lombok.Getter;

@Getter
public class Post {
    
    private final PostId id;
    private String title;
    private String content;

    public Post(PostId id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
