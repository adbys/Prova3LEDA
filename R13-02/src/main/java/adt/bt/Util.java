package adt.bt;

import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {

		BTNode<T> parent = node.getParent();
		BSTNode<T> x = node;
		BSTNode<T> y = (BSTNode<T>) node.getRight();
		BSTNode<T> z = (BSTNode<T>) node.getRight().getRight();

		y.setParent(x.getParent());
		x.setRight(y.getLeft());
		y.getLeft().setParent(x);
		y.setLeft(x);
		x.setParent(y);

		if (!parent.isEmpty()) {
			if (parent.getLeft().equals(x))
				parent.setLeft(x.getParent());
			else if (parent.getRight().equals(x))
				parent.setRight(x.getParent());
		}

		return y;

	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {

		BTNode<T> parent = node.getParent();
		BSTNode<T> x = node;
		BSTNode<T> y = (BSTNode<T>) node.getLeft();
		BSTNode<T> z = (BSTNode<T>) node.getLeft().getLeft();

		y.setParent(x.getParent());
		x.setLeft(y.getRight());
		y.getRight().setParent(x);
		y.setRight(x);
		x.setParent(y);
		
		if (!parent.isEmpty()) {
			if (parent.getLeft().equals(x))
				parent.setLeft(x.getParent());
			else if (parent.getRight().equals(x))
				parent.setRight(x.getParent());
		}

		return y;

	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
