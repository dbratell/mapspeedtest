package sarasas;

import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Bratell
 * Date: 2003-sep-21
 * Time: 10:55:49
 * To change this template use Options | File Templates.
 */
public class ArrayListMap implements Map
{
    private final ArrayList mKeys = new ArrayList();
    private final ArrayList mValues = new ArrayList();

    private final boolean mNoDuplicateInserts;

    public ArrayListMap()
    {
        this(false);
    }

    public ArrayListMap(boolean duplicateInsertsWontHappen)
    {
        mNoDuplicateInserts = duplicateInsertsWontHappen;
    }

    public int size()
    {
        return mKeys.size();
    }

    public void clear()
    {
        mKeys.clear();
        mValues.clear();
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public boolean containsKey(Object key)
    {
        return mKeys.indexOf(key) != -1;
    }

    public boolean containsValue(Object value)
    {
        return mValues.indexOf(value) != -1;
    }

    public Collection values()
    {
        return Collections.unmodifiableList(mValues);
    }

    public void putAll(Map t)
    {
        Set entries = t.entrySet();
        for (Iterator iterator = entries.iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    public Set entrySet()
    {
        throw new UnsupportedOperationException();
    }

    public Set keySet()
    {
        return new HashSet(mKeys);
    }

    public Object get(Object key)
    {
        int i = mKeys.indexOf(key);
        if (i == -1)
        {
            return null;
        }
        return mValues.get(i);
    }

    public Object remove(Object key)
    {
        int i = mKeys.indexOf(key);
        if (i == -1)
        {
            return null;
        }
        Object o = mValues.get(i);
        mKeys.remove(i);
        mValues.remove(i);
        return o;
    }

    public Object put(Object key, Object value)
    {
        if (mNoDuplicateInserts)
        {
            mKeys.add(key);
            mValues.add(value);
            return null;
        }
        else
        {
            int i = mKeys.indexOf(key);
            if (i == -1)
            {
                mKeys.add(key);
                mValues.add(value);
                return null;
            }
            Object o = mValues.get(i);
            mValues.set(i, value);
            return o;
        }
    }
}
