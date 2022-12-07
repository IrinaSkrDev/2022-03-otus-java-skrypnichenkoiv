package ru.otus.cache;


import java.util.WeakHashMap;

public class MyCache<K, V> implements ru.otus.cache.HwCache<K, V> {
    //Надо реализовать эти методы
   private final  WeakHashMap<K, V> map = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void remove(K key) {
        key = null;
        System.gc();
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void addListener(ru.otus.cache.HwListener<K, V> listener) {

    }

    @Override
    public void removeListener(ru.otus.cache.HwListener<K, V> listener) {

    }
}
