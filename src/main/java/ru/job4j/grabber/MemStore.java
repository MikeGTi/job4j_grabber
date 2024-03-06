package ru.job4j.grabber;

import java.util.*;

public class MemStore implements Store {
    Map<Integer, Post> store = new LinkedHashMap<>();

    private int id = 1;

    @Override
    public void save(Post post) {
        store.put(id++, post);
    }

    @Override
    public List<Post> getAll() {
        return store.values().stream().toList();
    }

    @Override
    public Post findById(int id) {
        return store.get(id);
    }
}
