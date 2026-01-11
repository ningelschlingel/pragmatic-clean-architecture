package io.ningelschlingel.pca.post.core.domain;

import io.ningelschlingel.pca.post.core.application.createpost.CreatePostCommand;
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

    public Post(CreatePostCommand command){
        this.id = command.id();
        this.title = command.title();
        this.content = command.content();
    }
}
