package com.example.ItE03.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

public class MyPreconditions {

    public static void checkNotNullOrEmpty(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkArgument(collection.size() > 0);
    }

    public static void checkIfOdd(Collection<?> collection) {
        checkIfOdd(collection, collection + " must have odd number of elements");
    }

    public static void checkIfOdd(Collection<?> collection, String msg) {
        Preconditions.checkArgument(collection.size() % 2 != 0,
                msg);
    }

    public static void checkIfDistinct(Collection<?> collection) {
        checkIfDistinct(collection, collection + " must have distinct elements");
    }

    public static void checkIfDistinct(Collection<?> collection, String msg) {
        Set<?> set = Sets.newHashSet(collection);
        Preconditions.checkArgument(set.size() == collection.size(),
                msg);
    }
}
