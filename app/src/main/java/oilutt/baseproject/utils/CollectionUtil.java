package oilutt.baseproject.utils;

import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Unchecked")
public final class CollectionUtil {
    private CollectionUtil() {
    }

    /**
     * Creates an empty {@link Collection} if the given is null.
     * Use on foreach and for statements.
     */
    public static <T> Collection<T> fix(Collection<T> collection) {
        return collection == null ? Collections.emptyList() : collection;
    }

    /**
     * Creates an empty {@link List} if the given is null.
     * Use on foreach and for statements.
     */
    public static <T> List<T> fix(List<T> collection) {
        return collection == null ? Collections.emptyList() : collection;
    }

    /**
     * Checks if the {@link Collection} passed as parameter is empty. It considers empty null collections.
     *
     * @param collection The {@link Collection} that will be checked
     * @return {@code true} if the collection is empty or {@code null}
     */
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks if the {@link Map} passed as parameter is empty. It considers empty null collections.
     *
     * @param map The {@link Map} that will be checked
     * @return {@code true} if the collection is empty or {@code null}
     */
    public static boolean isEmpty(@Nullable Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Return array of T with all items included in list and items passed by parameter
     * @param list
     * @param items
     * @param <T>
     * @return
     */
    public static <T> T[] concatList(T[] list, T... items) {
        ArrayList<T> itemList = new ArrayList<>();
        Collections.addAll(itemList, list);
        Collections.addAll(itemList, items);
        if (itemList.isEmpty()) {
            return null;
        }
        Class clazz = itemList.get(0).getClass();
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(clazz, itemList.size());
        result = itemList.toArray(result);
        return result;
    }

    /**
     * Checks if both {@link List} passed as parameter have the same elements
     *
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> boolean equals(List<T> list1, List<T> list2) {

        if (list1 == null && list2 != null) {
            return false;
        }
        if (list1 != null && list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            T item = list1.get(i);
            T itemToCompare = list2.get(i);
            if (!item.equals(itemToCompare)) {
                return false;
            }
        }
        return true;
    }
}
