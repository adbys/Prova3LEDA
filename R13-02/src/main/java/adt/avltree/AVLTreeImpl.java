package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	@Override
	public void insert(T element) {
		super.insert(element);
		BSTNode<T> node = search(element);
		rebalanceUp(node);
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (node.isEmpty()) {
			return;
		}
		super.remove(element);
		rebalanceUp((BSTNode<T>) node.getParent());
	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isLeaf())
			return this.findHeight((BSTNode<T>) node.getLeft()) - this.findHeight((BSTNode<T>) node.getRight());
		return 0;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {

		int nodeBalance = this.calculateBalance(node);
		int childrenBalance;
		BSTNode<T> nodeUp;

		if (nodeBalance < -1) {

			childrenBalance = this.calculateBalance((BSTNode<T>) node.getRight());

			if (childrenBalance < 0) {

				nodeUp = Util.leftRotation(node);

			} else {

				Util.rightRotation((BSTNode<T>) node.getRight());
				nodeUp = Util.leftRotation(node);

			}

			if (node.equals(this.getRoot())) {
				this.root = nodeUp;
			}

		} else if (nodeBalance > 1) {

			childrenBalance = this.calculateBalance((BSTNode<T>) node.getLeft());

			if (childrenBalance > 0) {

				nodeUp = Util.rightRotation(node);

			} else {

				Util.leftRotation((BSTNode<T>) node.getLeft());
				nodeUp = Util.rightRotation(node);

			}

			if (node.equals(this.getRoot())) {
				this.root = nodeUp;
			}

		}

	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> aux = node;

		while (!aux.isEmpty()) {
			if (Math.abs(this.calculateBalance((BSTNode<T>) aux)) > 1) {
				this.rebalance((BSTNode<T>) aux);
			}

			aux = (BSTNode<T>) aux.getParent();

		}
	}

}
