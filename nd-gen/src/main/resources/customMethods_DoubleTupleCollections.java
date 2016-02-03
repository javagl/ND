
    /**
     * Standardize the given input tuples. This means that the mean of
     * the given tuples is subtracted from them, and they are divided
     * by the standard deviation. <br>
     * <br>
     * The results will be written into the given result tuples.
     * If the given results list is <code>null</code>, then a new
     * list will be created and returned. <br>
     * <br>
     * If the result list is not <code>null</code>, then it must have the
     * same size as the input collection, otherwise an
     * <code>IllegalArgumentException</code> will be thrown.<br>
     * <br>
     * If the input collection contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * If the result list contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * The input collection and the results list may contain
     * pairwise identical elements.<br>
     * <br>
     * The input collection and the results list may be identical.
     * <br>
     * @param inputs The input tuples
     * @param results The result tuples
     * @return The result tuples
     * @throws IllegalArgumentException If the result list is not
     * <code>null</code> and has a size that is different from the size
     * of the input collection
     * @throws NullPointerException If the inputs list is <code>null</code>
     * or contains <code>null</code> elements, or if the output list is
     * non-<code>null</code> and contains <code>null</code> elements
     */
    public static List<MutableDoubleTuple> standardize(
        Collection<? extends DoubleTuple> inputs,
        List<MutableDoubleTuple> results)
    {
        results = validate(inputs, results);
        MutableDoubleTuple mean = arithmeticMean(inputs, null);
        MutableDoubleTuple standardDeviation =
            standardDeviationFromMean(inputs, mean, null);
        int index = 0;

        for (DoubleTuple input : inputs)
        {
            MutableDoubleTuple result = results.get(index);
            DoubleTuples.subtract(input, mean, result);
            DoubleTuples.divide(result, standardDeviation, result);
            index++;
        }

        boolean printResults = false;
        //printResults = true;
        if (printResults)
        {
            MutableDoubleTuple newMean = arithmeticMean(results, null);
            MutableDoubleTuple newStandardDeviation =
                standardDeviationFromMean(results, newMean, null);
            DoubleTuple min = min(results, null);
            DoubleTuple max = max(results, null);
            System.out.println("After standardization:");
            System.out.println("mean              : "+newMean);
            System.out.println("standard deviation: "+newStandardDeviation);
            System.out.println("min               : "+min);
            System.out.println("max               : "+max);
        }

        return results;
    }


    /**
     * Validate the given results list. If the given results list
     * is <code>null</code>, then a new results list will be created
     * and returned, that contains one MutableDoubleTuple instance
     * for each tuple from the inputs list, with the same size
     * as the corresponding tuple from the input collection.<br>
     * <br>
     * If the result list is not <code>null</code>, then it must have the
     * same size as the input collection, otherwise an
     * <code>IllegalArgumentException</code> will be thrown.<br>
     * <br>
     *
     * @param inputs The inputs
     * @param results The results
     * @return The results
     * @throws IllegalArgumentException If the given results list is not
     * <code>null</code>, and has a different size than the given inputs
     * collection
     */
    private static List<MutableDoubleTuple> validate(
        Collection<? extends DoubleTuple> inputs,
        List<MutableDoubleTuple> results)
    {
        if (results == null)
        {
            results = new ArrayList<MutableDoubleTuple>(inputs.size());
            for (DoubleTuple input : inputs)
            {
                results.add(DoubleTuples.create(input.getSize()));
            }
        }
        else
        {
            if (inputs.size() != results.size())
            {
                throw new IllegalArgumentException(
                    "The number of inputs (" + inputs.size() + ") must " +
                    "be the same as the number of results " +
                    "(" + results.size() + ")");
            }
        }
        return results;
    }

    /**
     * Normalize the given collection of {@link MutableDoubleTuple}s.<br>
     * <br>
     * The results will be written into the given result tuples.
     * If the given results list is <code>null</code>, then a new
     * list will be created and returned. <br>
     * <br>
     * If the result list is not <code>null</code>, then it must have the
     * same size as the input collection, otherwise an
     * <code>IllegalArgumentException</code> will be thrown.<br>
     * <br>
     * If the input collection contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * If the result list contains <code>null</code> elements, then a
     * <code>NullPointerException</code> will be thrown. <br>
     * <br>
     * The input collection and the results list may contain
     * pairwise identical elements.<br>
     * <br>
     * The input collection and the results list may be identical.
     * <br>
     *
     * @param inputs The tuples to normalize
     * @param results The result tuples
     * @return The result tuples
     * @throws IllegalArgumentException If the result list is not
     * <code>null</code> and has a size that is different from the size
     * of the input collection
     * @throws NullPointerException If the inputs list is <code>null</code>
     * or contains <code>null</code> elements, or if the output list is
     * non-<code>null</code> and contains <code>null</code> elements
     * @see DoubleTuples#normalize(DoubleTuple, MutableDoubleTuple)
     */
    public static List<MutableDoubleTuple> normalize(
        Collection<? extends DoubleTuple> inputs,
        List<MutableDoubleTuple> results)
    {
        results = validate(inputs, results);
        int index = 0;
        for (DoubleTuple input : inputs)
        {
            MutableDoubleTuple result = results.get(index);
            DoubleTuples.normalize(input, result);
            index++;
        }
        return results;
    }


    /**
     * Computes the component-wise arithmetic mean of the given collection
     * of {@link DoubleTuple} objects.
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple arithmeticMean(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        result = add(tuples, result);
        return DoubleTuples.multiply(result, 1.0 / tuples.size(), result);
    }

    /**
     * Computes the component-wise variance of the given collection
     * of {@link DoubleTuple} objects. The method will compute
     * the arithmetic mean, and then compute the actual result
     * with the {@link #variance(Collection, DoubleTuple, MutableDoubleTuple)}
     * method.
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple variance(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        DoubleTuple mean = arithmeticMean(tuples, null);
        return variance(tuples, mean, result);
    }

    /**
     * Computes the component-wise variance of the given collection
     * of {@link DoubleTuple} objects.
     *
     * @param tuples The input tuples
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean}
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple variance(
        Collection<? extends DoubleTuple> tuples, DoubleTuple mean,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        DoubleTuple first = tuples.iterator().next();
        result = DoubleTuples.validate(first, result);
        DoubleTuples.set(result, 0.0);
        int d = result.getSize();
        for (DoubleTuple tuple : tuples)
        {
            for (int i=0; i<d; i++)
            {
                double difference = tuple.get(i) - mean.get(i);
                double v = result.get(i);
                result.set(i, v + difference * difference);
            }
        }
        return DoubleTuples.multiply(result, 1.0 / tuples.size(), result);
    }

    /**
     * Returns the component-wise standard deviation of the given collection
     * of {@link DoubleTuple} objects. The method will compute
     * the arithmetic mean, and then compute the actual result
     * with the {@link #standardDeviationFromMean} method.
     *
     * @param tuples The input tuples
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple standardDeviation(
        Collection<? extends DoubleTuple> tuples,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        DoubleTuple mean = arithmeticMean(tuples, null);
        return standardDeviationFromMean(tuples, mean, result);
    }

    /**
     * Returns the component-wise standard deviation of the given collection
     * of {@link DoubleTuple} objects.
     * The method will compute {@link #variance}, and then compute
     * the actual result with the
     * {@link DoubleTuples#standardDeviationFromVariance}
     * method.
     *
     * @param tuples The input tuples
     * @param mean The mean, which may have been computed before with
     * {@link #arithmeticMean}
     * @param result The result tuple
     * @return The result, or <code>null</code> if the given collection is empty
     * @throws IllegalArgumentException If the given result is not
     * <code>null</code>, and has a {@link Tuple#getSize() size}
     * that is different from that of the input tuples.
     */
    public static MutableDoubleTuple standardDeviationFromMean(
        Collection<? extends DoubleTuple> tuples, DoubleTuple mean,
        MutableDoubleTuple result)
    {
        if (tuples.isEmpty())
        {
            return null;
        }
        result = variance(tuples, mean, result);
        return DoubleTuples.standardDeviationFromVariance(result, result);
    }
