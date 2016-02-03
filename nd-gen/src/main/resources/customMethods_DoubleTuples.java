
    /**
     * Fill the given tuple with random values in
     * the specified range, using the given random number generator
     *
     * @param t The tuple to fill
     * @param min The minimum value, inclusive
     * @param max The maximum value, exclusive
     * @param random The random number generator
     */
    public static void randomize(
        MutableDoubleTuple t, double min, double max, Random random)
    {
        double delta = max - min;
        for (int i=0; i<t.getSize(); i++)
        {
            t.set(i, min + random.nextDouble() * delta);
        }
    }

    /**
     * Fill the given tuple with random values in
     * [0,1), using the given random number generator
     *
     * @param t The tuple to fill
     * @param random The random number generator
     */
    public static void randomize(MutableDoubleTuple t, Random random)
    {
        randomize(t, 0.0, 1.0, random);
    }

    /**
     * Creates a tuple with the given size that
     * is filled with random values in [0,1)
     *
     * @param size The size
     * @param random The random number generator
     * @return The new tuple
     */
    public static MutableDoubleTuple createRandom(int size, Random random)
    {
       MutableDoubleTuple t = create(size);
       randomize(t, random);
       return t;
    }

    /**
     * Creates a tuple with the given size that
     * is filled with random values in the given range.
     *
     * @param size The size
     * @param min The minimum value, inclusive
     * @param max The maximum value, exclusive
     * @param random The random number generator
     * @return The new tuple
     */
    public static MutableDoubleTuple createRandom(
        int size, double min, double max, Random random)
    {
       MutableDoubleTuple t = create(size);
       randomize(t, min, max, random);
       return t;
    }



    /**
     * Randomize the given tuple with a gaussian
     * distribution with a mean of 0.0 and standard deviation of 1.0
     *
     * @param t The tuple to fill
     * @param random The random number generator
     */
    public static void randomizeGaussian(MutableDoubleTuple t, Random random)
    {
        for (int i=0; i<t.getSize(); i++)
        {
            double value = random.nextGaussian();
            t.set(i, value);
        }
    }

    /**
     * Creates a tuple with the given size
     * that was filled with values from a gaussian
     * distribution with mean 0.0 and standard deviation 1.0
     *
     * @param size The size
     * @param random The random number generator
     * @return The new tuple
     */
    public static MutableDoubleTuple createRandomGaussian(
        int size, Random random)
    {
       MutableDoubleTuple t = create(size);
       randomizeGaussian(t, random);
       return t;
    }


    /**
     * Normalize the given tuple and write the result into
     * the given result tuple. That is, the given tuple
     * is divided by its {@link #computeL2(DoubleTuple) L2 norm}.
     *
     * @param t The input tuple
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple normalize(
        DoubleTuple t, MutableDoubleTuple result)
    {
        result = validate(t, result);
        double inv = 1.0 / computeL2(t);
        return DoubleTupleFunctions.apply(
            t, (a)->(a * inv), result);
    }

    /**
     * Computes the L2 norm (euclidean length) of the given tuple
     *
     * @param t The tuple
     * @return The L2 norm
     */
    public static double computeL2(DoubleTuple t)
    {
        double sum = 0;
        for (int i=0; i<t.getSize(); i++)
        {
            double ti = t.get(i);
            sum += ti * ti;
        }
        return Math.sqrt(sum);
    }

    /**
     * Normalize the elements of the given tuple, so that its minimum and
     * maximum elements match the given minimum and maximum values.
     *
     * @param t The input tuple
     * @param min The minimum value
     * @param max The maximum value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple normalizeElements(
        DoubleTuple t, double min, double max, MutableDoubleTuple result)
    {
        return rescaleElements(t, min(t), max(t), min, max, result);
    }

    /**
     * Normalize the elements of the given tuple, so that each element
     * will be linearly rescaled to the interval defined by the corresponding
     * elements of the given minimum and maximum tuple.
     * Each element that is equal to the corresponding minimum element will
     * be 0.0 in the resulting tuple.
     * Each element that is equal to the corresponding maximum element will
     * be 1.0 in the resulting tuple.
     * Other values will be interpolated accordingly.
     *
     * @param t The input tuple
     * @param min The minimum value
     * @param max The maximum value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple normalizeElements(
        DoubleTuple t, DoubleTuple min, DoubleTuple max,
        MutableDoubleTuple result)
    {
        result = validate(t, result);
        for (int i=0; i<result.getSize(); i++)
        {
            double value = t.get(i);
            double minValue = min.get(i);
            double maxValue = max.get(i);
            double alpha = (value - minValue) / (maxValue - minValue);
            double newValue = alpha;
            result.set(i, newValue);
        }
        return result;
    }


    /**
     * Rescale the elements of the given tuple, so that the specified
     * old range is mapped to the specified new range.
     *
     * @param t The input tuple
     * @param oldMin The old minimum value
     * @param oldMax The old maximum value
     * @param newMin The new minimum value
     * @param newMax The new maximum value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple rescaleElements(
        DoubleTuple t, double oldMin, double oldMax,
        double newMin, double newMax, MutableDoubleTuple result)
    {
        double invDelta = 1.0 / (oldMax - oldMin);
        double newRange = newMax - newMin;
        double scaling = invDelta * newRange;
        return DoubleTupleFunctions.apply(
            t, (a)->(newMin + (a - oldMin) * scaling), result);
    }



    /**
     * Computes <code>t0+alpha*(t1-t0)</code>, and stores the result in
     * the given result tuple.
     *
     * @param t0 The first input tuple
     * @param t1 The second input tuple
     * @param alpha The interpolation value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple interpolate(
        DoubleTuple t0, DoubleTuple t1, double alpha,
        MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
            t0, t1, (a,b)->(a+alpha*(b-a)), result);
    }

    /**
     * Returns the geometric mean of the given tuple
     *
     * @param t The input tuple
     * @return The mean
     */
    public static double geometricMean(DoubleTuple t)
    {
        double product = DoubleTupleFunctions.reduce(t, 1.0, (a,b) -> (a*b));
        return Math.pow(product, 1.0 / t.getSize());
    }

    /**
     * Returns the harmonic mean of the given tuple
     *
     * @param t The input tuple
     * @return The mean
     */
    public static double harmonicMean(DoubleTuple t)
    {
        double s =
            DoubleTupleFunctions.reduce(t, 0.0, (a, b) -> (a + (1.0 / b)));
        return t.getSize() / s;
    }

    /**
     * Returns the standard deviation of the given variances (that is,
     * a tuple containing the square roots of the values in the given
     * tuple).
     *
     * @param variance The input tuple
     * @param result The result tuple
     * @return The standard deviation
     * @throws IllegalArgumentException If the given tuples do not have the
     * same size
     */
    public static MutableDoubleTuple standardDeviationFromVariance(
        DoubleTuple variance, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
            variance, Math::sqrt, result);
    }


    /**
     * Standardize the given tuple. This means that the mean of the elements
     * is subtracted from them, and they are divided by the standard
     * deviation.
     *
     * @param t The tuple
     * @param result The result tuple
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple standardize(
        DoubleTuple t, MutableDoubleTuple result)
    {
        result = DoubleTuples.validate(t, result);
        double mean = arithmeticMean(t);
        double standardDeviation = standardDeviationFromMean(t, mean);
        double invStandardDeviation = 1.0 / standardDeviation;
        return DoubleTupleFunctions.apply(
            t, (a) -> ((a - mean) * invStandardDeviation), result);
    }

    /**
     * Returns the arithmetic mean of the given tuple
     *
     * @param t The input tuple
     * @return The mean
     */
    public static double arithmeticMean(DoubleTuple t)
    {
        double sum = DoubleTupleFunctions.reduce(t, 0.0, (a,b) -> (a+b));
        return sum / t.getSize();
    }

    /**
     * Returns the bias-corrected sample variance of the given tuple. The
     * method will compute the arithmetic mean, and then compute the actual
     * result with the {@link #variance(DoubleTuple, double)} method.
     *
     * @param t The input tuple
     * @return The variance
     *
     * @see #variance(DoubleTuple, double)
     */
    public static double variance(DoubleTuple t)
    {
        double mean = arithmeticMean(t);
        return variance(t, mean);
    }

    /**
     * Returns the bias-corrected sample variance of the given tuple.
     *
     * @param t The input tuple
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean(DoubleTuple)}
     * @return The variance
     */
    public static double variance(DoubleTuple t, double mean)
    {
        int d = t.getSize();
        double variance = 0;
        for (int i=0; i<d; i++)
        {
            double difference = t.get(i) - mean;
            variance += difference * difference;
        }
        return variance / (d - 1);
    }


    /**
     * Returns the standard deviation of the given tuple.
     * The method will compute the arithmetic mean, and then compute the
     * actual result with the
     * {@link #standardDeviationFromMean(DoubleTuple, double)}
     * method.
     *
     * @param t The input tuple
     * @return The standard deviation
     */
    public static double standardDeviation(DoubleTuple t)
    {
        double mean = arithmeticMean(t);
        return standardDeviationFromMean(t, mean);
    }

    /**
     * Returns the standard deviation of the given tuple.
     * The method will compute {@link #variance(DoubleTuple)}, return
     * the square root of this value.
     *
     * @param t The input tuple
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean(DoubleTuple)}
     * @return The standard deviation
     */
    public static double standardDeviationFromMean(
        DoubleTuple t, double mean)
    {
        double variance = variance(t, mean);
        return Math.sqrt(variance);
    }

    /**
     * Returns whether the given tuple contains an element that
     * is Not A Number
     *
     * @param tuple The tuple
     * @return Whether the tuple contains a NaN element
     */
    public static boolean containsNaN(DoubleTuple tuple)
    {
        for (int i=0; i<tuple.getSize(); i++)
        {
            if (Double.isNaN(tuple.get(i)))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Replace all occurrences of "Not A Number" in the given tuple
     * with the given value, and store the result in the given 
     * result tuple
     * 
     * @param t The tuple
     * @param newValue The value that should replace the NaN value
     * @param result The tuple that will store the result
     * @return The result tuple
     * @throws IllegalArgumentException If the given tuples do not
     * have the same {@link Tuple#getSize() size}
     */
    public static MutableDoubleTuple replaceNaN(
        DoubleTuple t, double newValue, MutableDoubleTuple result)
    {
        return DoubleTupleFunctions.apply(
            t, d -> Double.isNaN(d) ? newValue : d, result);
    }
    

