package com.yonyou.mde.web.utils;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/*
* 笛卡尔积
* */
public class MuiltCross<T> implements Iterator<T[]> {
    private int[] elementSizes;
    private List<T[]> element;
    private int[] next;
    private boolean isFlush = false;
    private boolean hasNext;
    private final int length;
    private int tail = 0;
    private int size = 1;
    public MuiltCross(List<T[]> element) {
        if (element == null) {
            throw new NullPointerException();
        }
        length = element.size();
        if (length < 1) {
            size = 0;
            isFlush = true;
            return;
        }
        this.elementSizes = new int[length];
        this.element = element;
        next = new int[length];
        for (int i = 0; i < length; i++) {
            if (element == null) {
                return;
            }
            this.elementSizes[i] = element.get(i).length;
            next[i] = -1;
            size *= this.elementSizes[i];
        }
    }

    @Override
    public boolean hasNext() {
        if (isFlush) {
            return hasNext;
        }
        isFlush = true;
        hasNext = findNext();
        return hasNext;
    }

    public int cartesianSize() {
        return size;
    }

    @Override
    public T[] next() {
        if (isFlush) {
            if (hasNext) {
                isFlush = false;
                return jointElement();
            }
        } else {
            hasNext = findNext();
            if (hasNext) {
                return jointElement();
            }
        }
        throw new NoSuchElementException();
    }

    private T[] jointElement() {
        T[] tmp = (T[]) newArrays(element.get(tail).getClass(), length);
        for (int i = 0; i < next.length; i++) {
            tmp[i] = element.get(i)[next[i]];
        }
        return tmp;
    }

    private <T> T[] newArrays(Class<T> cls, int length) {
        try {
            return cls.isArray() ? (T[]) (Array.newInstance(cls.getComponentType(), length)) : (T[]) Array.newInstance(cls, length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected boolean findNext() {
        for (; ; ) {
            next[tail]++;
            if (next[tail] >= elementSizes[tail]) {
                tail--;
                if (tail < 0) {
                    return false;
                }
            } else if (tail < length - 1) {
                next[++tail] = -1;
            } else {
                return true;
            }
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
