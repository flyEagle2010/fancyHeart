package com.doteyplay.game.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Serializable
{
    static final long serialVersionUID = 2797971902374557567L;
    
	protected final ConcurrentHashMap<E, Boolean> map;

	public ConcurrentHashSet(int initialCapacity, float loadFactor)
	{
		map = new ConcurrentHashMap<E, Boolean>(initialCapacity, loadFactor);
	}
	
	public ConcurrentHashSet()
	{
		map = new ConcurrentHashMap<E, Boolean>();
	}

	public ConcurrentHashSet(Collection<E> objs)
	{
		map = new ConcurrentHashMap<E, Boolean>();
		addAll(objs);
	}

	@Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public boolean add(E o) {
        Boolean answer = map.putIfAbsent(o, Boolean.TRUE);
        return answer == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
