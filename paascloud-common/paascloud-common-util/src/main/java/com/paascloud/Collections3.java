package com.paascloud;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The class Collections 3.
 *
 * @author paascloud.net@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Collections3 {

	/**
	 * 返回a+b的新List.
	 *
	 * @param <T> the type parameter
	 * @param a   the a
	 * @param b   the b
	 *
	 * @return the list
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 *
	 * @param <T> the type parameter
	 * @param a   the a
	 * @param b   the b
	 *
	 * @return the list
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 *
	 * @param <T> the type parameter
	 * @param a   the a
	 * @param b   the b
	 *
	 * @return the list
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
}
