package adt.bst;

import java.util.ArrayList;
import java.util.Arrays;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {

		return this.height((BSTNode) this.getRoot());

	}

	private int height(BSTNode node) {
		int result = -1;

		if (!node.isEmpty()) {

			int leftHeight = this.height((BSTNode) node.getLeft());
			int rightHeight = this.height((BSTNode) node.getRight());

			if (leftHeight > rightHeight) {
				return 1 + leftHeight;
			} else {
				return 1 + rightHeight;
			}
		}

		return result;
	}

	@Override
	public BSTNode<T> search(T element) {
		if (this.getRoot().equals(element)) {
			return this.getRoot();
		} else {
			return this.search(element, this.getRoot());
		}

	}

	private BSTNode<T> search(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			return node;
		}

		if (element.equals(node.getData())) {

			return node;

		} else {
			if (element.compareTo(node.getData()) > 0) {
				return this.search(element, (BSTNode<T>) node.getRight());
			} else {
				return this.search(element, (BSTNode<T>) node.getLeft());
			}
		}
	}

	@Override
	public void insert(T element) {
		if (this.isEmpty())
			this.root = new BSTNode.Builder().data(element).left(new BSTNode<T>()).right(new BSTNode<T>())
					.parent(new BSTNode<T>()).build();
		else
			this.insert(element, this.getRoot());

	}

	private void insert(T element, BSTNode<T> node) {
		if (node.getData().compareTo(element) < 0) {
			if (node.getRight().isEmpty()) {
				BSTNode newNode = new BSTNode.Builder().data(element).left(new BSTNode<T>()).right(new BSTNode<T>())
						.parent(node).build();
				node.setRight(newNode);
			} else {
				this.insert(element, (BSTNode<T>) node.getRight());
			}
		} else {
			if (node.getLeft().isEmpty()) {
				BSTNode newNode = new BSTNode.Builder().data(element).left(new BSTNode<T>()).right(new BSTNode<T>())
						.parent(node).build();
				node.setLeft(newNode);
			} else {
				this.insert(element, (BSTNode<T>) node.getLeft());
			}

		}

	}

	@Override
	public BSTNode<T> maximum() {
		if (this.isEmpty())
			return null;

		return this.maximum(this.getRoot());

	}

	private BSTNode<T> maximum(BSTNode<T> node) {

		if (node.getRight().isEmpty()) {
			return node;
		} else {
			return this.maximum((BSTNode<T>) node.getRight());
		}

	}

	@Override
	public BSTNode<T> minimum() {

		if (this.isEmpty())
			return null;

		return this.minimum(this.getRoot());

	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.getLeft().isEmpty())
			return node;
		else
			return this.minimum((BSTNode<T>) node.getLeft());
	}

	@Override
	public BSTNode<T> sucessor(T element) {

		BSTNode node = this.search(element);

		if (node.isEmpty()) {
			return null;
		}

		if (!node.getRight().isEmpty()) {
			return this.minimum((BSTNode<T>) node.getRight());
		} else {
			BSTNode aux = node;
			while (aux.equals(aux.getParent().getRight())) {
				aux = (BSTNode) aux.getParent();
			}
			return (BSTNode<T>) aux.getParent();
		}

	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode node = this.search(element);

		if (node.isEmpty()) {
			return null;
		}

		if (!node.getLeft().isEmpty()) {
			return this.maximum((BSTNode<T>) node.getLeft());
		} else {
			BSTNode aux = node;
			while (aux.equals(aux.getParent().getLeft())) {
				aux = (BSTNode) aux.getParent();
			}
			if (aux.getParent().isEmpty()) {
				return null;
			}
			return (BSTNode<T>) aux.getParent();
		}

	}

	@Override
	public void remove(T element) {
		BSTNode node = this.search(element);

		if (node.isLeaf()) {
			this.removeLeaf(node);
		} else if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			this.removeTwoDegree(node);
		} else {
			this.removeOneDegree(node);
		}

	}

	private void removeLeaf(BSTNode node) {
		node.setData(null);
	}

	private void removeOneDegree(BSTNode node) {

		if (this.getRoot().equals(node)) {
			if (!node.getLeft().isEmpty()) {
				this.root = (BSTNode<T>) node.getLeft();
				this.root.setParent(node.getParent());
			} else {
				this.root = (BSTNode<T>) node.getRight();
				this.root.setParent(node.getParent());
			}

			return;
		}

		if (!node.getRight().isEmpty()) {

			node.getRight().setParent(node.getParent());

			if (node.getParent().getRight().equals(node)) {
				node.getParent().setRight(node.getRight());
			} else if (node.getParent().getLeft().equals(node)) {
				node.getParent().setLeft(node.getRight());
			}

		} else {

			node.getLeft().setParent(node.getParent());

			if (node.getParent().getRight().equals(node)) {
				node.getParent().setRight(node.getRight());
			} else if (node.getParent().getLeft().equals(node)) {
				node.getParent().setLeft(node.getLeft());
			}

		}

	}

	private void removeTwoDegree(BSTNode node) {
		BSTNode sucessor = this.sucessor((T) node.getData());
		BSTNode predecessor = this.predecessor((T) node.getData());

		if (!sucessor.isEmpty()) {
			T sucessorValue = (T) sucessor.getData();
			this.remove(sucessorValue);
			node.setData(sucessorValue);
		} else {
			T predecessorValue = (T) predecessor.getData();
			this.remove(predecessorValue);
			node.setData(predecessorValue);

		}

	}

	@Override
	public T[] preOrder() {
		if (isEmpty())
			return (T[]) new Comparable[0];

		T[] array = (T[]) new Comparable[this.size()];

		preOrder(this.root, array, 0);

		return array;
	}

	private int preOrder(BSTNode<T> node, T[] array, int index) {
		array[index++] = node.getData();

		if (!node.getLeft().isEmpty())
			index = preOrder((BSTNode<T>) node.getLeft(), array, index);

		if (!node.getRight().isEmpty())
			index = preOrder((BSTNode<T>) node.getRight(), array, index);

		return index;
	}

	@Override
	public T[] order() {
		if (isEmpty())
			return (T[]) new Comparable[0];

		T[] array = (T[]) new Comparable[this.size()];

		order(this.root, array, 0);

		return array;
	}

	private int order(BSTNode<T> node, T[] array, int index) {
		if (!node.getLeft().isEmpty())
			index = order((BSTNode<T>) node.getLeft(), array, index);

		array[index++] = node.getData();

		if (!node.getRight().isEmpty())
			index = order((BSTNode<T>) node.getRight(), array, index);

		return index;
	}

	@Override
	public T[] postOrder() {
		if (isEmpty())
			return (T[]) new Comparable[0];

		T[] array = (T[]) new Comparable[this.size()];

		postOrder(this.root, array, 0);

		return array;
	}

	private int postOrder(BSTNode<T> node, T[] array, int index) {
		if (!node.getLeft().isEmpty())
			index = postOrder((BSTNode<T>) node.getLeft(), array, index);

		if (!node.getRight().isEmpty())
			index = postOrder((BSTNode<T>) node.getRight(), array, index);

		array[index++] = node.getData();

		return index;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}
	
	public T estatisticaDeOrdem(int e){
		return estatisticaDeOrdem(1, e);
		
	}

	private T estatisticaDeOrdem(int atual, int procurado) {
		T minimo = this.minimum().getData();
		this.remove(minimo);

		if (atual == procurado) {
			this.insert(minimo);
			return minimo;
		}
		
		T minimodavez = this.estatisticaDeOrdem(++atual, procurado);
		
		this.insert(minimo);
		
		return minimodavez;
		
	}

	public int findHeight(BSTNode<T> node) {

      		if (node.isEmpty()) {
         		return -1;
      		}

      		int heightLeft = this.findHeight((BSTNode<T>) node.getLeft());
      		int heightRight = this.findHeight((BSTNode<T>) node.getRight());

      		if (heightLeft > heightRight) {

   		      return heightLeft + 1;

   		   } else {

   		      return heightRight + 1;
    		  }
   }


}
