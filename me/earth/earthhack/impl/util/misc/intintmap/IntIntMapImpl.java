// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc.intintmap;

public class IntIntMapImpl implements IntIntMap
{
    private static final int FREE_KEY = 0;
    public static final int NO_VALUE = 0;
    private int[] m_data;
    private boolean m_hasFreeKey;
    private int m_freeValue;
    private final float m_fillFactor;
    private int m_threshold;
    private int m_size;
    private int m_mask;
    private int m_mask2;
    
    public IntIntMapImpl(final int size, final float fillFactor) {
        if (fillFactor <= 0.0f || fillFactor >= 1.0f) {
            throw new IllegalArgumentException("FillFactor must be in (0, 1)");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive!");
        }
        final int capacity = Tools.arraySize(size, fillFactor);
        this.m_mask = capacity - 1;
        this.m_mask2 = capacity * 2 - 1;
        this.m_fillFactor = fillFactor;
        this.m_data = new int[capacity * 2];
        this.m_threshold = (int)(capacity * fillFactor);
    }
    
    @Override
    public int get(final int key) {
        int ptr = (Tools.phiMix(key) & this.m_mask) << 1;
        if (key == 0) {
            return this.m_hasFreeKey ? this.m_freeValue : 0;
        }
        int k = this.m_data[ptr];
        if (k == 0) {
            return 0;
        }
        if (k == key) {
            return this.m_data[ptr + 1];
        }
        do {
            ptr = (ptr + 2 & this.m_mask2);
            k = this.m_data[ptr];
            if (k == 0) {
                return 0;
            }
        } while (k != key);
        return this.m_data[ptr + 1];
    }
    
    @Override
    public int put(final int key, final int value) {
        if (key == 0) {
            final int ret = this.m_freeValue;
            if (!this.m_hasFreeKey) {
                ++this.m_size;
            }
            this.m_hasFreeKey = true;
            this.m_freeValue = value;
            return ret;
        }
        int ptr = (Tools.phiMix(key) & this.m_mask) << 1;
        int k = this.m_data[ptr];
        if (k == 0) {
            this.m_data[ptr] = key;
            this.m_data[ptr + 1] = value;
            if (this.m_size >= this.m_threshold) {
                this.rehash(this.m_data.length * 2);
            }
            else {
                ++this.m_size;
            }
            return 0;
        }
        if (k == key) {
            final int ret2 = this.m_data[ptr + 1];
            this.m_data[ptr + 1] = value;
            return ret2;
        }
        do {
            ptr = (ptr + 2 & this.m_mask2);
            k = this.m_data[ptr];
            if (k == 0) {
                this.m_data[ptr] = key;
                this.m_data[ptr + 1] = value;
                if (this.m_size >= this.m_threshold) {
                    this.rehash(this.m_data.length * 2);
                }
                else {
                    ++this.m_size;
                }
                return 0;
            }
        } while (k != key);
        final int ret2 = this.m_data[ptr + 1];
        this.m_data[ptr + 1] = value;
        return ret2;
    }
    
    @Override
    public int remove(final int key) {
        if (key == 0) {
            if (!this.m_hasFreeKey) {
                return 0;
            }
            this.m_hasFreeKey = false;
            --this.m_size;
            return this.m_freeValue;
        }
        else {
            int ptr = (Tools.phiMix(key) & this.m_mask) << 1;
            int k = this.m_data[ptr];
            if (k == key) {
                final int res = this.m_data[ptr + 1];
                this.shiftKeys(ptr);
                --this.m_size;
                return res;
            }
            if (k == 0) {
                return 0;
            }
            do {
                ptr = (ptr + 2 & this.m_mask2);
                k = this.m_data[ptr];
                if (k == key) {
                    final int res = this.m_data[ptr + 1];
                    this.shiftKeys(ptr);
                    --this.m_size;
                    return res;
                }
            } while (k != 0);
            return 0;
        }
    }
    
    private void shiftKeys(int pos) {
        final int[] data = this.m_data;
        int last = 0;
    Label_0006:
        while (true) {
            pos = ((last = pos) + 2 & this.m_mask2);
            int k;
            while ((k = data[pos]) != 0) {
                final int slot = (Tools.phiMix(k) & this.m_mask) << 1;
                Label_0089: {
                    if (last <= pos) {
                        if (last >= slot) {
                            break Label_0089;
                        }
                        if (slot > pos) {
                            break Label_0089;
                        }
                    }
                    else if (last >= slot && slot > pos) {
                        break Label_0089;
                    }
                    pos = (pos + 2 & this.m_mask2);
                    continue;
                }
                data[last] = k;
                data[last + 1] = data[pos + 1];
                continue Label_0006;
            }
            break;
        }
        data[last] = 0;
    }
    
    @Override
    public int size() {
        return this.m_size;
    }
    
    private void rehash(final int newCapacity) {
        this.m_threshold = (int)(newCapacity / 2 * this.m_fillFactor);
        this.m_mask = newCapacity / 2 - 1;
        this.m_mask2 = newCapacity - 1;
        final int oldCapacity = this.m_data.length;
        final int[] oldData = this.m_data;
        this.m_data = new int[newCapacity];
        this.m_size = (this.m_hasFreeKey ? 1 : 0);
        for (int i = 0; i < oldCapacity; i += 2) {
            final int oldKey = oldData[i];
            if (oldKey != 0) {
                this.put(oldKey, oldData[i + 1]);
            }
        }
    }
}
