package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String link;
    private String text;
    private LocalDateTime created;

    public Post(int id, String name, String link, String text) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.text = text;
    }

    public Post(String name, String link, String text, LocalDateTime created) {
        this.name = name;
        this.link = link;
        this.text = text;
        this.created = created;
    }

    public Post(int id, String name, String link, String text, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.text = text;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post post)) {
            return false;
        }
        return getId() == post.getId() && Objects.equals(getLink(), post.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLink());
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", link='" + link + '\''
                + ", text='" + text + '\''
                + ", created=" + created
                + '}';
    }
}
