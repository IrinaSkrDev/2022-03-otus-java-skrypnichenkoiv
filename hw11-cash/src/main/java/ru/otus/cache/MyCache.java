package ru.otus.cache;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements ru.otus.cache.HwCache<K, V> {
    //Надо реализовать эти методы
    private final WeakHashMap<K, V> map = new WeakHashMap<>();
    private final List<HwListener<K, V>> listner = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        map.put(key, value);
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
        listner.add(listener);

    }

    @Override
    public void removeListener(ru.otus.cache.HwListener<K, V> listener) {
        listner.remove(listener);
    }

    public void generateNotify(K key, V value, String action) {
        listner.forEach(listener -> {
            try {
                listener.notify(key,value, action);
            } catch (Exception ex) {
                //логирование исключения
            }
        });
    }
}
