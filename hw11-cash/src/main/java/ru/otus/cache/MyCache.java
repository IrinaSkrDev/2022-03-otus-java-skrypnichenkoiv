package ru.otus.cache;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements ru.otus.cache.HwCache<K, V> {
    //Надо реализовать эти методы
    private final WeakHashMap<K, V> map = new WeakHashMap<>();
    private final List<HwListener<K, V>> listners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        map.put(key, value);
        generateNotify(key, value, "save");
    }

    @Override
    public void remove(K key) {
        key = null;
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void addListener(ru.otus.cache.HwListener<K, V> listener) {
        listners.add(listener);

    }

    @Override
    public void removeListener(ru.otus.cache.HwListener<K, V> listener) {
        listners.remove(listener);
    }

    private void generateNotify(K key, V value, String action) {
        listners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception ex) {
                //логирование исключения
            }
        });
    }
}
