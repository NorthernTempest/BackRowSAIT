package service;

import java.util.ArrayList;
import java.util.Comparator;

public final class SortService {

	// uses quick sort
	public static <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> array) {
		return quicksort(array, 0, array.size() - 1);
	}

	private static <T extends Comparable<T>> ArrayList<T> quicksort(ArrayList<T> array, int low, int high) {
		if (low < high) {
			int i = low;

			for (int j = low; j < high; j++) {
				if (array.get(j).compareTo(array.get(high)) < 0) {
					T temp = array.get(i);
					array.set(i, array.get(j));
					array.set(j, temp);
					i++;
				}
			}

			T temp = array.get(i);
			array.set(i, array.get(high));
			array.set(high, temp);

			array = quicksort(array, low, i - 1);
			array = quicksort(array, i + 1, high);
		}

		return array;
	}

	public static <E, T extends Comparator<E>> ArrayList<E> sort(ArrayList<E> array, T comparator) {
		return quicksort(array, 0, array.size() - 1, comparator);
	}

	private static <E, T extends Comparator<E>> ArrayList<E> quicksort(ArrayList<E> array, int low, int high,
			T comparator) {
		if (low < high) {
			int i = low;

			for (int j = low; j < high; j++) {
				if (comparator.compare(array.get(j), array.get(high)) < 0) {
					E temp = array.get(i);
					array.set(i, array.get(j));
					array.set(j, temp);
					i++;
				}
			}

			E temp = array.get(i);
			array.set(i, array.get(high));
			array.set(high, temp);

			array = quicksort(array, low, i - 1, comparator);
			array = quicksort(array, i + 1, high, comparator);
		}

		return array;
	}

	public static <E> ArrayList<E> reverse(ArrayList<E> array) {
		for( int i = 0; i < array.size() / 2; i++ )
		{
			array.set(i, array.get( array.size() - 1 - i ) );
		}
		
		return array;
	}
}
