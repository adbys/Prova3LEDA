package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap Ã© definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Essa comparaÃ§Ã£o nÃ£o Ã© feita diretamente com os elementos armazenados,
 * mas sim usando um comparator. Dessa forma, dependendo do comparator, a heap
 * pode funcionar como uma max-heap ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	* O comparador Ã© utilizado para fazer as comparaÃ§Ãµes da heap. O ideal Ã©
	* mudar apenas o comparator e mandar reordenar a heap usando esse
	* comparator. Assim os metodos da heap nÃ£o precisam saber se vai funcionar
	* como max-heap ou min-heap.
	*/
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	* Construtor da classe. Note que de inicio a heap funciona como uma
	* min-heap.
	*/
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	* Deve retornar o indice que representa o filho a esquerda do elemento
	* indexado pela posicao i no vetor
	*/
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	* Deve retornar o indice que representa o filho a direita do elemento
	* indexado pela posicao i no vetor
	*/
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = Util.makeArray(index + 1);
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	* Valida o invariante de uma heap a partir de determinada posicao, que pode
	* ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	* (comparados usando o comparator) elementos na parte de cima da heap.
	*/
	private void heapify(int position) {

		int largest = position;
		
		if (this.left(position) <= this.index && this.comparator.compare(this.heap[this.left(position)], this.heap[largest]) > 0) {
			largest = this.left(position);
		}
		
		if (this.right(position) <= this.index && this.comparator.compare(this.heap[this.right(position)], this.heap[largest]) > 0) {
			largest = this.right(position);
		}
		
		if (largest != position) {
			Util.swap(this.getHeap(), position, largest);
			this.heapify(largest);

		}
		
		
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////
		if (element == null)
			return;
		
		this.heap[++this.index] = element;
		
		int aux = this.index;
		
		while(aux > 0 && this.comparator.compare(this.heap[aux], this.heap[this.parent(aux)]) > 0) {
			Util.swap(this.getHeap(), aux, this.parent(aux));
			aux = this.parent(aux);
			
		}
		
		
	}

	@Override
	public void buildHeap(T[] array) {
		if(array == null)
			return;
		
		for (int i = (array.length/2); i >= 0; i--) {
			this.heapify(array, i);
		}
		
		System.out.println();
		
	}

	private void heapify(T[] array, int i) {
		
		int largest = i;
		
		if (this.left(i) < array.length && array[this.left(i)].compareTo(array[i]) > 0 ) {
			largest = this.left(i);
		}
		
		if (this.right(i) < array.length && array[this.right(i)].compareTo(array[largest]) > 0) {
			largest = this.right(i);
		}
		
		
		if (i != largest) {
			Util.swap(array, i, largest);
			this.heapify(array, largest);
		}
		
	}

	@Override
	public T extractRootElement() {
		if (this.isEmpty())
			return null;
		
		T retorno = this.heap[0];
		
		Util.swap(this.getHeap(), 0, this.index);
		
		this.heap[this.index--] = null;
		
		this.heapify(0);	
		

		return retorno;
	}

	@Override
	public T rootElement() {
		if (this.isEmpty())
			return null;
		
		return this.heap[0];
	}

	@Override
	public T[] heapsort(T[] array) {
		T[] retorno = (T[]) new Comparable[array.length];
		
		if (array.length == 1)
			return array;
		
		for (T element : array) {
			this.insert(element);
		}
		
		Comparator<T> comparator = (o1, o2) -> o1.compareTo(o2);
		
		if (comparator.compare(this.heap[0], this.heap[1]) > 0) {
			for (int i = array.length - 1; i >= 0; i--) {
				retorno[i] = this.extractRootElement();
			}
			
		} else {
			for (int i = 0; i < array.length; i++) {
				retorno[i] = this.extractRootElement();
			}
		}
		
		
		
		return retorno;		
		
	}

	@Override
	public int size() {
		return this.index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}