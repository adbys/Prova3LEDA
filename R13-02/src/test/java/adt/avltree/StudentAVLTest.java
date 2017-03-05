package adt.avltree;
 
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
 
import adt.bst.BSTNode;
 
public class StudentAVLTest {
 
    private AVLTree<Integer> avl;
    private BSTNode<Integer> NIL = new BSTNode<Integer>();
 
    @Before
    public void setUp() {
        avl = new AVLTreeImpl<>();
    }
 
    @Test
    public void testInit() {
        assertTrue(avl.isEmpty());
        assertEquals(0, avl.size());
        assertEquals(-1, avl.height());
        assertEquals(NIL, avl.getRoot());
    }
 
    @Test
    public void testInsert() {
        avl.insert(-10);
        assertEquals(1, avl.size());
        assertEquals(0, avl.height());
        assertArrayEquals(new Integer[] { -10 }, avl.preOrder());
 
        assertFalse(avl.isEmpty());
        assertEquals(new Integer(-10), avl.getRoot().getData());
 
        avl.insert(-15);
        assertEquals(2, avl.size());
        assertEquals(1, avl.height());
        assertArrayEquals(new Integer[] { -10, -15 }, avl.preOrder());
 
        avl.insert(20);
        assertEquals(3, avl.size());
        assertEquals(1, avl.height());
        assertArrayEquals(new Integer[] { -10, -15, 20 }, avl.preOrder());
    }
 
    @Test
    public void testRemove() {
        avl.insert(55);
        avl.insert(9);
        avl.insert(91);
        avl.insert(12);
 
        avl.remove(-1);
        assertEquals(4, avl.size());
 
        avl.remove(91);
        assertEquals(3, avl.size());
        assertArrayEquals(new Integer[] { 12, 9, 55 }, avl.preOrder());
 
        avl.remove(12);
        assertEquals(2, avl.size());
        assertArrayEquals(new Integer[] { 55, 9 }, avl.preOrder());
 
        avl.remove(9);
        avl.remove(55);
        assertEquals(NIL, avl.getRoot());
        assertTrue(avl.isEmpty());
    }
     
    @Test
    public void testRemoveAleatorio() {
        for(int i = 1; i < 32; i++){
            avl.insert(i);
        }
        System.out.println(((AVLTreeImpl<Integer>) avl).calculateBalance((BSTNode<Integer>)avl.getRoot()));
//        ((BSTNode<?>) avl.getRoot()).printTree();
        assertEquals(Math.floor(Math.log(avl.size()) / Math.log(2)), avl.height(), 0.1);
        assertTrue(avl.getRoot().getData().equals(16));
             
 
        avl.remove(8);
        avl.remove(13);
        avl.remove(15);
        assertEquals(Math.floor(Math.log(avl.size()) / Math.log(2)), avl.height(), 0.1);
        assertTrue(avl.getRoot().getData().equals(16));
             
        avl.remove(14);
        assertEquals(Math.floor(Math.log(avl.size()) / Math.log(2)), avl.height(), 0.1);
        assertTrue(avl.getRoot().getData().equals(16));
         
        avl.remove(24);
        avl.insert(24);
        avl.remove(25);
        avl.remove(27);
         
        avl.remove(31);
        assertEquals(Math.floor(Math.log(avl.size()) / Math.log(2)), avl.height(), 1.1);
        assertTrue(avl.getRoot().getData().equals(16));
    }
     
    @Test
    public void testSPLab() {
        assertEquals(0, avl.size());
        assertEquals(-1, avl.height());
         
        avl.insert(55);
        avl.insert(9);
        avl.insert(91);
        avl.insert(12);
         
         
 
        assertEquals(4, avl.size());
        assertEquals(2, avl.height());
         
        avl.insert(8);
        avl.insert(90);
 
        assertEquals(6, avl.size());
         
        assertEquals(2, avl.height());
         
        avl.insert(15);
         
        assertEquals(3, avl.height());
         
        avl.insert(17);
         
        assertEquals(3, avl.height());
         
        assertArrayEquals(new Integer[] { 55, 9, 8, 15, 12, 17, 91, 90}, avl.preOrder());
 
        avl.insert(7);
        avl.insert(6);
         
        assertEquals(3, avl.height());
        assertEquals(10, avl.size());
         
        avl.insert(87);
        avl.insert(61);
        avl.insert(59);
        avl.insert(3);
        avl.insert(40);
         
        assertEquals(4, avl.height());
        assertEquals(15, avl.size());   
         
        assertArrayEquals(new Integer[] { 55, 9, 7, 6, 3, 8, 15, 12, 17, 40, 90, 61, 59, 87, 91}, avl.preOrder());
         
        avl.remove(12);
        avl.remove(8);
         
        assertEquals(3, avl.height());
         
        avl.remove(61);
         
        assertEquals(3, avl.height());
         
        assertArrayEquals(new Integer[] { 55, 9, 6, 3, 7, 17, 15, 40, 90, 87, 59, 91}, avl.preOrder());
         
         
    }
}