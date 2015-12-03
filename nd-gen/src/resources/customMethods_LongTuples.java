
    /**
     * Convert the given {@link LongTuple} into a {@link DoubleTuple}
     *
     * @param longTuple The {@link LongTuple}
     * @return The {@link MutableDoubleTuple}
     */
    public static MutableDoubleTuple toDoubleTuple(LongTuple longTuple)
    {
        int d = intTuple.getSize();
        MutableDoubleTuple doubleTuple = DoubleTuples.create(d);
        for (int i=0; i<d; i++)
        {
            doubleTuple.set(i, longTuple.get(i));
        }
        return doubleTuple;
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given value.
     *
     * @param x The x coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(long x)
    {
        return new DefaultLongTuple(new long[]{ x });
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given values.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(int x, int y)
    {
        return new DefaultLongTuple(new long[]{ x, y });
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given values.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(long x, long y, long z)
    {
        return new DefaultLongTuple(new long[]{ x, y, z });
    }

    /**
     * Creates a new {@link MutableLongTuple} with the given values.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param w The w coordinate
     * @return The new tuple
     */
    public static MutableLongTuple of(long x, long y, long z, long w)
    {
        return new DefaultLongTuple(new long[]{ x, y, z, w });
    }

    /**
     * Lexicographically increment the given tuple in the given range, and
     * store the result in the given result tuple. It is assumed that the
     * elements of the given tuple are
     * {@link #areElementsGreaterThanOrEqual(LongTuple, LongTuple) greater than
     * or equal to} the values in the given minimum tuple.<br>
     * <br>
     * Note that in contrast to most other methods in this class, the
     * given result tuple may <b>not</b> be <code>null</code> (but it
     * may be identical to the input tuple).
     *
     * @param t The input tuple
     * @param min The minimum values
     * @param max The maximum values
     * @param result The result tuple
     * @return Whether the tuple could be incremented without causing an
     * overflow
     */
    public static boolean incrementLexicographically(
        LongTuple t, LongTuple min, LongTuple max, MutableLongTuple result)
    {
        Utils.checkForEqualSize(t, min);
        Utils.checkForEqualSize(t, max);
        Utils.checkForEqualSize(t, result);
        if (result != t)
        {
            result.set(t);
        }
        return incrementLexicographically(
            result, min, max, result.getSize()-1);
    }

    /**
     * Recursively increment the given tuple lexicographically, starting at
     * the given index.
     *
     * @param current The tuple to increment
     * @param min The minimum values
     * @param max The maximum values
     * @param index The index
     * @return Whether the tuple could be incremented
     */
    private static boolean incrementLexicographically(
        MutableLongTuple current, LongTuple min, LongTuple max, int index)
    {
        if (index == -1)
        {
            return false;
        }
        long oldValue = current.get(index);
        long newValue = oldValue + 1;
        current.set(index, newValue);
        if (newValue >= max.get(index))
        {
            current.set(index, min.get(index));
            return incrementLexicographically(current, min, max, index-1);
        }
        return true;
    }


    /**
     * Colexicographically increment the given tuple in the given range, and
     * store the result in the given result tuple. It is assumed that the
     * elements of the given tuple are
     * {@link #areElementsGreaterThanOrEqual(LongTuple, LongTuple) greater than
     * or equal to} the values in the given minimum tuple.<br>
     * <br>
     * Note that in contrast to most other methods in this class, the
     * given result tuple may <b>not</b> be <code>null</code> (but it
     * may be identical to the input tuple).
     *
     * @param t The input tuple
     * @param min The minimum values
     * @param max The maximum values
     * @param result The result tuple
     * @return Whether the tuple could be incremented without causing an
     * overflow
     */
    public static boolean incrementColexicographically(
        LongTuple t, LongTuple min, LongTuple max, MutableLongTuple result)
    {
        Utils.checkForEqualSize(t, min);
        Utils.checkForEqualSize(t, max);
        Utils.checkForEqualSize(t, result);
        if (result != t)
        {
            result.set(t);
        }
        return incrementColexicographically(
            result, min, max, 0);
    }

    /**
     * Recursively increment the given tuple colexicographically, starting at
     * the given index.
     *
     * @param current The tuple to increment
     * @param min The minimum values
     * @param max The maximum values
     * @param index The index.
     * @return Whether the tuple could be incremented
     */
    static boolean incrementColexicographically(MutableLongTuple current,
        LongTuple min, LongTuple max, int index)
    {
        if (index == max.getSize())
        {
            return false;
        }
        long oldValue = current.get(index);
        long newValue = oldValue + 1;
        current.set(index, newValue);
        if (newValue >= max.get(index))
        {
            current.set(index, min.get(index));
            return incrementColexicographically(current, min, max, index+1);
        }
        return true;
    }
