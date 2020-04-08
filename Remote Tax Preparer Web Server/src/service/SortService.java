package service;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A set of methods to sort and organize the order of objects in an array list.
 * 
 * @author Jesse Goerzen
 */
public final class SortService {

	/**
	 * Sorts the given list of objects using quicksort.
	 * 
	 * @param <T> The type of object being sorted.
	 * @param array The list of objects to be sorted.
	 * @return The sorted list of objects.
	 */
	public static <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> array) {
		return quicksort(array, 0, array.size() - 1);
	}
	
	/**
	 * Sorts part of the given list of objects using quicksort.
	 * 
	 * @param <T> The type of object being sorted.
	 * @param array The list of objects to be sorted.
	 * @param low The position of the first item in the list to be sorted.
	 * @param high The position of the last item in the list to be sorted.
	 * @return The list of objects, part of which is sorted.
	 */
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
	
	/**
	 * Sorts the given list of objects using quicksort.
	 * 
	 * @param <E> The type of object being sorted.
	 * @param <T> The type of comparator for the type of object being sorted.
	 * @param array The list of objects to be sorted.
	 * @param comparator The Comparator object that checks which of the two objects is greater.
	 * @return The sorted list of objects.
	 */
	public static <E, T extends Comparator<E>> ArrayList<E> sort(ArrayList<E> array, T comparator) {
		return quicksort(array, 0, array.size() - 1, comparator);
	}
	
	/**
	 * Sorts the given list of objects using quicksort.
	 * 
	 * @param <E> The type of object being sorted.
	 * @param <T> The type of comparator for the type of object being sorted.
	 * @param array The list of objects to be sorted.
	 * @param low The position of the first item in the list to be sorted.
	 * @param high The position of the last item in the list to be sorted.
	 * @param comparator The Comparator object that checks which of the two objects is greater.
	 * @return The list of objects, part of which is sorted.
	 */
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
	
	/**
	 * Reverses the order of the items in the list of objects.
	 * 
	 * @param <E> The type of object in the list.
	 * @param array ArrayList The list of objects.
	 * @return ArrayList The reversed list of objects.
	 */
	public static <E> ArrayList<E> reverse(ArrayList<E> array) {
		for( int i = 0; i < array.size() / 2; i++ )
		{
			E item = array.get(i);
			array.set(i, array.get( array.size() - 1 - i ) );
			array.set(array.size() - 1 - i, item);
		}
		
		return array;
	}
}
