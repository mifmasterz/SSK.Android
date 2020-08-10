package org.apache.poi.util;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class BinaryTree extends AbstractMap {
    private static int _INDEX_COUNT = 2;
    private static int _INDEX_SUM = (_KEY + _VALUE);
    static int _KEY = 0;
    private static int _MINIMUM_INDEX = 0;
    static int _VALUE = 1;
    private static String[] _data_name = {"key", Param.VALUE};
    private final Set[] _entry_set;
    private final Set[] _key_set;
    int _modifications;
    final Node[] _root;
    int _size;
    private final Collection[] _value_collection;

    private abstract class BinaryTreeIterator implements Iterator {
        private int _expected_modifications;
        protected Node _last_returned_node = null;
        private Node _next_node;
        private int _type;

        /* access modifiers changed from: protected */
        public abstract Object doGetNext();

        BinaryTreeIterator(int type) {
            this._type = type;
            this._expected_modifications = BinaryTree.this._modifications;
            this._next_node = BinaryTree.leastNode(BinaryTree.this._root[this._type], this._type);
        }

        public boolean hasNext() {
            return this._next_node != null;
        }

        public Object next() throws NoSuchElementException, ConcurrentModificationException {
            if (this._next_node == null) {
                throw new NoSuchElementException();
            } else if (BinaryTree.this._modifications != this._expected_modifications) {
                throw new ConcurrentModificationException();
            } else {
                this._last_returned_node = this._next_node;
                this._next_node = BinaryTree.nextGreater(this._next_node, this._type);
                return doGetNext();
            }
        }

        public void remove() throws IllegalStateException, ConcurrentModificationException {
            if (this._last_returned_node == null) {
                throw new IllegalStateException();
            } else if (BinaryTree.this._modifications != this._expected_modifications) {
                throw new ConcurrentModificationException();
            } else {
                BinaryTree.this.doRedBlackDelete(this._last_returned_node);
                this._expected_modifications++;
                this._last_returned_node = null;
            }
        }
    }

    private static final class Node implements Entry {
        private boolean[] _black = {true, true};
        private boolean _calculated_hashcode = false;
        private Comparable[] _data;
        private int _hashcode;
        private Node[] _left = {null, null};
        private Node[] _parent = {null, null};
        private Node[] _right = {null, null};

        Node(Comparable key, Comparable value) {
            this._data = new Comparable[]{key, value};
        }

        public Comparable getData(int index) {
            return this._data[index];
        }

        public void setLeft(Node node, int index) {
            this._left[index] = node;
        }

        public Node getLeft(int index) {
            return this._left[index];
        }

        public void setRight(Node node, int index) {
            this._right[index] = node;
        }

        public Node getRight(int index) {
            return this._right[index];
        }

        public void setParent(Node node, int index) {
            this._parent[index] = node;
        }

        public Node getParent(int index) {
            return this._parent[index];
        }

        public void swapColors(Node node, int index) {
            boolean[] zArr = this._black;
            zArr[index] = zArr[index] ^ node._black[index];
            boolean[] zArr2 = node._black;
            zArr2[index] = zArr2[index] ^ this._black[index];
            boolean[] zArr3 = this._black;
            zArr3[index] = zArr3[index] ^ node._black[index];
        }

        public boolean isBlack(int index) {
            return this._black[index];
        }

        public boolean isRed(int index) {
            return !this._black[index];
        }

        public void setBlack(int index) {
            this._black[index] = true;
        }

        public void setRed(int index) {
            this._black[index] = false;
        }

        public void copyColor(Node node, int index) {
            this._black[index] = node._black[index];
        }

        public Object getKey() {
            return this._data[BinaryTree._KEY];
        }

        public Object getValue() {
            return this._data[BinaryTree._VALUE];
        }

        public Object setValue(Object ignored) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Map.Entry.setValue is not supported");
        }

        public boolean equals(Object o) {
            boolean z = true;
            if (this == o) {
                return true;
            }
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry e = (Entry) o;
            if (!this._data[BinaryTree._KEY].equals(e.getKey()) || !this._data[BinaryTree._VALUE].equals(e.getValue())) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            if (!this._calculated_hashcode) {
                this._hashcode = this._data[BinaryTree._KEY].hashCode() ^ this._data[BinaryTree._VALUE].hashCode();
                this._calculated_hashcode = true;
            }
            return this._hashcode;
        }
    }

    public BinaryTree() {
        this._size = 0;
        this._modifications = 0;
        this._key_set = new Set[]{null, null};
        this._entry_set = new Set[]{null, null};
        this._value_collection = new Collection[]{null, null};
        this._root = new Node[]{null, null};
    }

    public BinaryTree(Map map) throws ClassCastException, NullPointerException, IllegalArgumentException {
        this();
        putAll(map);
    }

    public Object getKeyForValue(Object value) throws ClassCastException, NullPointerException {
        return doGet((Comparable) value, _VALUE);
    }

    public Object removeValue(Object value) {
        return doRemove((Comparable) value, _VALUE);
    }

    public Set entrySetByValue() {
        if (this._entry_set[_VALUE] == null) {
            this._entry_set[_VALUE] = new AbstractSet() {
                public Iterator iterator() {
                    return new BinaryTreeIterator(BinaryTree._VALUE) {
                        /* access modifiers changed from: protected */
                        public Object doGetNext() {
                            return this._last_returned_node;
                        }
                    };
                }

                public boolean contains(Object o) {
                    boolean z = false;
                    if (!(o instanceof Entry)) {
                        return false;
                    }
                    Entry entry = (Entry) o;
                    Object key = entry.getKey();
                    Node node = BinaryTree.this.lookup((Comparable) entry.getValue(), BinaryTree._VALUE);
                    if (node != null && node.getData(BinaryTree._KEY).equals(key)) {
                        z = true;
                    }
                    return z;
                }

                public boolean remove(Object o) {
                    if (!(o instanceof Entry)) {
                        return false;
                    }
                    Entry entry = (Entry) o;
                    Object key = entry.getKey();
                    Node node = BinaryTree.this.lookup((Comparable) entry.getValue(), BinaryTree._VALUE);
                    if (node == null || !node.getData(BinaryTree._KEY).equals(key)) {
                        return false;
                    }
                    BinaryTree.this.doRedBlackDelete(node);
                    return true;
                }

                public int size() {
                    return BinaryTree.this.size();
                }

                public void clear() {
                    BinaryTree.this.clear();
                }
            };
        }
        return this._entry_set[_VALUE];
    }

    public Set keySetByValue() {
        if (this._key_set[_VALUE] == null) {
            this._key_set[_VALUE] = new AbstractSet() {
                public Iterator iterator() {
                    return new BinaryTreeIterator(BinaryTree._VALUE) {
                        /* access modifiers changed from: protected */
                        public Object doGetNext() {
                            return this._last_returned_node.getData(BinaryTree._KEY);
                        }
                    };
                }

                public int size() {
                    return BinaryTree.this.size();
                }

                public boolean contains(Object o) {
                    return BinaryTree.this.containsKey(o);
                }

                public boolean remove(Object o) {
                    int old_size = BinaryTree.this._size;
                    BinaryTree.this.remove(o);
                    return BinaryTree.this._size != old_size;
                }

                public void clear() {
                    BinaryTree.this.clear();
                }
            };
        }
        return this._key_set[_VALUE];
    }

    public Collection valuesByValue() {
        if (this._value_collection[_VALUE] == null) {
            this._value_collection[_VALUE] = new AbstractCollection() {
                public Iterator iterator() {
                    return new BinaryTreeIterator(BinaryTree._VALUE) {
                        /* access modifiers changed from: protected */
                        public Object doGetNext() {
                            return this._last_returned_node.getData(BinaryTree._VALUE);
                        }
                    };
                }

                public int size() {
                    return BinaryTree.this.size();
                }

                public boolean contains(Object o) {
                    return BinaryTree.this.containsValue(o);
                }

                public boolean remove(Object o) {
                    int old_size = BinaryTree.this._size;
                    BinaryTree.this.removeValue(o);
                    return BinaryTree.this._size != old_size;
                }

                /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<java.lang.Object>, for r5v0, types: [java.util.Collection<java.lang.Object>, java.util.Collection] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public boolean removeAll(java.util.Collection<java.lang.Object> r5) {
                    /*
                        r4 = this;
                        r0 = 0
                        java.util.Iterator r1 = r5.iterator()
                    L_0x0005:
                        boolean r2 = r1.hasNext()
                        if (r2 == 0) goto L_0x0019
                        org.apache.poi.util.BinaryTree r2 = org.apache.poi.util.BinaryTree.this
                        java.lang.Object r3 = r1.next()
                        java.lang.Object r2 = r2.removeValue(r3)
                        if (r2 == 0) goto L_0x0005
                        r0 = 1
                        goto L_0x0005
                    L_0x0019:
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.util.BinaryTree.C14163.removeAll(java.util.Collection):boolean");
                }

                public void clear() {
                    BinaryTree.this.clear();
                }
            };
        }
        return this._value_collection[_VALUE];
    }

    private Object doRemove(Comparable o, int index) {
        Node node = lookup(o, index);
        if (node == null) {
            return null;
        }
        Object rval = node.getData(oppositeIndex(index));
        doRedBlackDelete(node);
        return rval;
    }

    private Object doGet(Comparable o, int index) {
        checkNonNullComparable(o, index);
        Node node = lookup(o, index);
        if (node == null) {
            return null;
        }
        return node.getData(oppositeIndex(index));
    }

    private int oppositeIndex(int index) {
        return _INDEX_SUM - index;
    }

    public Node lookup(Comparable data, int index) {
        Node node = this._root[index];
        while (node != null) {
            int cmp = compare(data, node.getData(index));
            if (cmp == 0) {
                return node;
            }
            node = cmp < 0 ? node.getLeft(index) : node.getRight(index);
        }
        return null;
    }

    private static int compare(Comparable o1, Comparable o2) {
        return o1.compareTo(o2);
    }

    static Node leastNode(Node node, int index) {
        Node rval = node;
        if (rval != null) {
            while (rval.getLeft(index) != null) {
                rval = rval.getLeft(index);
            }
        }
        return rval;
    }

    static Node nextGreater(Node node, int index) {
        if (node == null) {
            return null;
        }
        if (node.getRight(index) != null) {
            return leastNode(node.getRight(index), index);
        }
        Node parent = node.getParent(index);
        Node child = node;
        while (parent != null && child == parent.getRight(index)) {
            child = parent;
            parent = parent.getParent(index);
        }
        return parent;
    }

    private static void copyColor(Node from, Node to, int index) {
        if (to == null) {
            return;
        }
        if (from == null) {
            to.setBlack(index);
        } else {
            to.copyColor(from, index);
        }
    }

    private static boolean isRed(Node node, int index) {
        if (node == null) {
            return false;
        }
        return node.isRed(index);
    }

    private static boolean isBlack(Node node, int index) {
        if (node == null) {
            return true;
        }
        return node.isBlack(index);
    }

    private static void makeRed(Node node, int index) {
        if (node != null) {
            node.setRed(index);
        }
    }

    private static void makeBlack(Node node, int index) {
        if (node != null) {
            node.setBlack(index);
        }
    }

    private static Node getGrandParent(Node node, int index) {
        return getParent(getParent(node, index), index);
    }

    private static Node getParent(Node node, int index) {
        if (node == null) {
            return null;
        }
        return node.getParent(index);
    }

    private static Node getRightChild(Node node, int index) {
        if (node == null) {
            return null;
        }
        return node.getRight(index);
    }

    private static Node getLeftChild(Node node, int index) {
        if (node == null) {
            return null;
        }
        return node.getLeft(index);
    }

    private static boolean isLeftChild(Node node, int index) {
        boolean z = true;
        if (node == null) {
            return true;
        }
        if (node.getParent(index) == null) {
            return false;
        }
        if (node != node.getParent(index).getLeft(index)) {
            z = false;
        }
        return z;
    }

    private static boolean isRightChild(Node node, int index) {
        boolean z = true;
        if (node == null) {
            return true;
        }
        if (node.getParent(index) == null) {
            return false;
        }
        if (node != node.getParent(index).getRight(index)) {
            z = false;
        }
        return z;
    }

    private void rotateLeft(Node node, int index) {
        Node right_child = node.getRight(index);
        node.setRight(right_child.getLeft(index), index);
        if (right_child.getLeft(index) != null) {
            right_child.getLeft(index).setParent(node, index);
        }
        right_child.setParent(node.getParent(index), index);
        if (node.getParent(index) == null) {
            this._root[index] = right_child;
        } else if (node.getParent(index).getLeft(index) == node) {
            node.getParent(index).setLeft(right_child, index);
        } else {
            node.getParent(index).setRight(right_child, index);
        }
        right_child.setLeft(node, index);
        node.setParent(right_child, index);
    }

    private void rotateRight(Node node, int index) {
        Node left_child = node.getLeft(index);
        node.setLeft(left_child.getRight(index), index);
        if (left_child.getRight(index) != null) {
            left_child.getRight(index).setParent(node, index);
        }
        left_child.setParent(node.getParent(index), index);
        if (node.getParent(index) == null) {
            this._root[index] = left_child;
        } else if (node.getParent(index).getRight(index) == node) {
            node.getParent(index).setRight(left_child, index);
        } else {
            node.getParent(index).setLeft(left_child, index);
        }
        left_child.setRight(node, index);
        node.setParent(left_child, index);
    }

    private void doRedBlackInsert(Node inserted_node, int index) {
        Node current_node = inserted_node;
        makeRed(current_node, index);
        while (current_node != null && current_node != this._root[index] && isRed(current_node.getParent(index), index)) {
            if (isLeftChild(getParent(current_node, index), index)) {
                Node y = getRightChild(getGrandParent(current_node, index), index);
                if (isRed(y, index)) {
                    makeBlack(getParent(current_node, index), index);
                    makeBlack(y, index);
                    makeRed(getGrandParent(current_node, index), index);
                    current_node = getGrandParent(current_node, index);
                } else {
                    if (isRightChild(current_node, index)) {
                        current_node = getParent(current_node, index);
                        rotateLeft(current_node, index);
                    }
                    makeBlack(getParent(current_node, index), index);
                    makeRed(getGrandParent(current_node, index), index);
                    if (getGrandParent(current_node, index) != null) {
                        rotateRight(getGrandParent(current_node, index), index);
                    }
                }
            } else {
                Node y2 = getLeftChild(getGrandParent(current_node, index), index);
                if (isRed(y2, index)) {
                    makeBlack(getParent(current_node, index), index);
                    makeBlack(y2, index);
                    makeRed(getGrandParent(current_node, index), index);
                    current_node = getGrandParent(current_node, index);
                } else {
                    if (isLeftChild(current_node, index)) {
                        current_node = getParent(current_node, index);
                        rotateRight(current_node, index);
                    }
                    makeBlack(getParent(current_node, index), index);
                    makeRed(getGrandParent(current_node, index), index);
                    if (getGrandParent(current_node, index) != null) {
                        rotateLeft(getGrandParent(current_node, index), index);
                    }
                }
            }
        }
        makeBlack(this._root[index], index);
    }

    /* access modifiers changed from: 0000 */
    public void doRedBlackDelete(Node deleted_node) {
        int index = _MINIMUM_INDEX;
        while (index < _INDEX_COUNT) {
            if (!(deleted_node.getLeft(index) == null || deleted_node.getRight(index) == null)) {
                swapPosition(nextGreater(deleted_node, index), deleted_node, index);
            }
            Node replacement = deleted_node.getLeft(index) != null ? deleted_node.getLeft(index) : deleted_node.getRight(index);
            if (replacement != null) {
                replacement.setParent(deleted_node.getParent(index), index);
                if (deleted_node.getParent(index) == null) {
                    this._root[index] = replacement;
                } else if (deleted_node == deleted_node.getParent(index).getLeft(index)) {
                    deleted_node.getParent(index).setLeft(replacement, index);
                } else {
                    deleted_node.getParent(index).setRight(replacement, index);
                }
                deleted_node.setLeft(null, index);
                deleted_node.setRight(null, index);
                deleted_node.setParent(null, index);
                if (isBlack(deleted_node, index)) {
                    doRedBlackDeleteFixup(replacement, index);
                }
            } else if (deleted_node.getParent(index) == null) {
                this._root[index] = null;
            } else {
                if (isBlack(deleted_node, index)) {
                    doRedBlackDeleteFixup(deleted_node, index);
                }
                if (deleted_node.getParent(index) != null) {
                    if (deleted_node == deleted_node.getParent(index).getLeft(index)) {
                        deleted_node.getParent(index).setLeft(null, index);
                    } else {
                        deleted_node.getParent(index).setRight(null, index);
                    }
                    deleted_node.setParent(null, index);
                }
            }
            index++;
        }
        shrink();
    }

    private void doRedBlackDeleteFixup(Node replacement_node, int index) {
        Node current_node = replacement_node;
        while (current_node != this._root[index] && isBlack(current_node, index)) {
            if (isLeftChild(current_node, index)) {
                Node sibling_node = getRightChild(getParent(current_node, index), index);
                if (isRed(sibling_node, index)) {
                    makeBlack(sibling_node, index);
                    makeRed(getParent(current_node, index), index);
                    rotateLeft(getParent(current_node, index), index);
                    sibling_node = getRightChild(getParent(current_node, index), index);
                }
                if (!isBlack(getLeftChild(sibling_node, index), index) || !isBlack(getRightChild(sibling_node, index), index)) {
                    if (isBlack(getRightChild(sibling_node, index), index)) {
                        makeBlack(getLeftChild(sibling_node, index), index);
                        makeRed(sibling_node, index);
                        rotateRight(sibling_node, index);
                        sibling_node = getRightChild(getParent(current_node, index), index);
                    }
                    copyColor(getParent(current_node, index), sibling_node, index);
                    makeBlack(getParent(current_node, index), index);
                    makeBlack(getRightChild(sibling_node, index), index);
                    rotateLeft(getParent(current_node, index), index);
                    current_node = this._root[index];
                } else {
                    makeRed(sibling_node, index);
                    current_node = getParent(current_node, index);
                }
            } else {
                Node sibling_node2 = getLeftChild(getParent(current_node, index), index);
                if (isRed(sibling_node2, index)) {
                    makeBlack(sibling_node2, index);
                    makeRed(getParent(current_node, index), index);
                    rotateRight(getParent(current_node, index), index);
                    sibling_node2 = getLeftChild(getParent(current_node, index), index);
                }
                if (!isBlack(getRightChild(sibling_node2, index), index) || !isBlack(getLeftChild(sibling_node2, index), index)) {
                    if (isBlack(getLeftChild(sibling_node2, index), index)) {
                        makeBlack(getRightChild(sibling_node2, index), index);
                        makeRed(sibling_node2, index);
                        rotateLeft(sibling_node2, index);
                        sibling_node2 = getLeftChild(getParent(current_node, index), index);
                    }
                    copyColor(getParent(current_node, index), sibling_node2, index);
                    makeBlack(getParent(current_node, index), index);
                    makeBlack(getLeftChild(sibling_node2, index), index);
                    rotateRight(getParent(current_node, index), index);
                    current_node = this._root[index];
                } else {
                    makeRed(sibling_node2, index);
                    current_node = getParent(current_node, index);
                }
            }
        }
        makeBlack(current_node, index);
    }

    private void swapPosition(Node x, Node y, int index) {
        Node x_old_parent = x.getParent(index);
        Node x_old_left_child = x.getLeft(index);
        Node x_old_right_child = x.getRight(index);
        Node y_old_parent = y.getParent(index);
        Node y_old_left_child = y.getLeft(index);
        Node y_old_right_child = y.getRight(index);
        boolean y_was_left_child = false;
        boolean x_was_left_child = x.getParent(index) != null && x == x.getParent(index).getLeft(index);
        if (y.getParent(index) != null && y == y.getParent(index).getLeft(index)) {
            y_was_left_child = true;
        }
        if (x == y_old_parent) {
            x.setParent(y, index);
            if (y_was_left_child) {
                y.setLeft(x, index);
                y.setRight(x_old_right_child, index);
            } else {
                y.setRight(x, index);
                y.setLeft(x_old_left_child, index);
            }
        } else {
            x.setParent(y_old_parent, index);
            if (y_old_parent != null) {
                if (y_was_left_child) {
                    y_old_parent.setLeft(x, index);
                } else {
                    y_old_parent.setRight(x, index);
                }
            }
            y.setLeft(x_old_left_child, index);
            y.setRight(x_old_right_child, index);
        }
        if (y == x_old_parent) {
            y.setParent(x, index);
            if (x_was_left_child) {
                x.setLeft(y, index);
                x.setRight(y_old_right_child, index);
            } else {
                x.setRight(y, index);
                x.setLeft(y_old_left_child, index);
            }
        } else {
            y.setParent(x_old_parent, index);
            if (x_old_parent != null) {
                if (x_was_left_child) {
                    x_old_parent.setLeft(y, index);
                } else {
                    x_old_parent.setRight(y, index);
                }
            }
            x.setLeft(y_old_left_child, index);
            x.setRight(y_old_right_child, index);
        }
        if (x.getLeft(index) != null) {
            x.getLeft(index).setParent(x, index);
        }
        if (x.getRight(index) != null) {
            x.getRight(index).setParent(x, index);
        }
        if (y.getLeft(index) != null) {
            y.getLeft(index).setParent(y, index);
        }
        if (y.getRight(index) != null) {
            y.getRight(index).setParent(y, index);
        }
        x.swapColors(y, index);
        if (this._root[index] == x) {
            this._root[index] = y;
        } else if (this._root[index] == y) {
            this._root[index] = x;
        }
    }

    private static void checkNonNullComparable(Object o, int index) {
        if (o == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(_data_name[index]);
            sb.append(" cannot be null");
            throw new NullPointerException(sb.toString());
        } else if (!(o instanceof Comparable)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(_data_name[index]);
            sb2.append(" must be Comparable");
            throw new ClassCastException(sb2.toString());
        }
    }

    private static void checkKey(Object key) {
        checkNonNullComparable(key, _KEY);
    }

    private static void checkValue(Object value) {
        checkNonNullComparable(value, _VALUE);
    }

    private static void checkKeyAndValue(Object key, Object value) {
        checkKey(key);
        checkValue(value);
    }

    private void modify() {
        this._modifications++;
    }

    private void grow() {
        modify();
        this._size++;
    }

    private void shrink() {
        modify();
        this._size--;
    }

    private void insertValue(Node newNode) throws IllegalArgumentException {
        Node node = this._root[_VALUE];
        while (true) {
            int cmp = compare(newNode.getData(_VALUE), node.getData(_VALUE));
            if (cmp == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot store a duplicate value (\"");
                sb.append(newNode.getData(_VALUE));
                sb.append("\") in this Map");
                throw new IllegalArgumentException(sb.toString());
            } else if (cmp < 0) {
                if (node.getLeft(_VALUE) != null) {
                    node = node.getLeft(_VALUE);
                } else {
                    node.setLeft(newNode, _VALUE);
                    newNode.setParent(node, _VALUE);
                    doRedBlackInsert(newNode, _VALUE);
                    return;
                }
            } else if (node.getRight(_VALUE) != null) {
                node = node.getRight(_VALUE);
            } else {
                node.setRight(newNode, _VALUE);
                newNode.setParent(node, _VALUE);
                doRedBlackInsert(newNode, _VALUE);
                return;
            }
        }
    }

    public int size() {
        return this._size;
    }

    public boolean containsKey(Object key) throws ClassCastException, NullPointerException {
        checkKey(key);
        return lookup((Comparable) key, _KEY) != null;
    }

    public boolean containsValue(Object value) {
        checkValue(value);
        return lookup((Comparable) value, _VALUE) != null;
    }

    public Object get(Object key) throws ClassCastException, NullPointerException {
        return doGet((Comparable) key, _KEY);
    }

    public Object put(Object key, Object value) throws ClassCastException, NullPointerException, IllegalArgumentException {
        checkKeyAndValue(key, value);
        Node node = this._root[_KEY];
        if (node != null) {
            while (true) {
                int cmp = compare((Comparable) key, node.getData(_KEY));
                if (cmp != 0) {
                    if (cmp >= 0) {
                        if (node.getRight(_KEY) == null) {
                            Node newNode = new Node((Comparable) key, (Comparable) value);
                            insertValue(newNode);
                            node.setRight(newNode, _KEY);
                            newNode.setParent(node, _KEY);
                            doRedBlackInsert(newNode, _KEY);
                            grow();
                            break;
                        }
                        node = node.getRight(_KEY);
                    } else if (node.getLeft(_KEY) == null) {
                        Node newNode2 = new Node((Comparable) key, (Comparable) value);
                        insertValue(newNode2);
                        node.setLeft(newNode2, _KEY);
                        newNode2.setParent(node, _KEY);
                        doRedBlackInsert(newNode2, _KEY);
                        grow();
                        break;
                    } else {
                        node = node.getLeft(_KEY);
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Cannot store a duplicate key (\"");
                    sb.append(key);
                    sb.append("\") in this Map");
                    throw new IllegalArgumentException(sb.toString());
                }
            }
        } else {
            Node root = new Node((Comparable) key, (Comparable) value);
            this._root[_KEY] = root;
            this._root[_VALUE] = root;
            grow();
        }
        return null;
    }

    public Object remove(Object key) {
        return doRemove((Comparable) key, _KEY);
    }

    public void clear() {
        modify();
        this._size = 0;
        this._root[_KEY] = null;
        this._root[_VALUE] = null;
    }

    public Set keySet() {
        if (this._key_set[_KEY] == null) {
            this._key_set[_KEY] = new AbstractSet() {
                public Iterator iterator() {
                    return new BinaryTreeIterator(BinaryTree._KEY) {
                        /* access modifiers changed from: protected */
                        public Object doGetNext() {
                            return this._last_returned_node.getData(BinaryTree._KEY);
                        }
                    };
                }

                public int size() {
                    return BinaryTree.this.size();
                }

                public boolean contains(Object o) {
                    return BinaryTree.this.containsKey(o);
                }

                public boolean remove(Object o) {
                    int old_size = BinaryTree.this._size;
                    BinaryTree.this.remove(o);
                    return BinaryTree.this._size != old_size;
                }

                public void clear() {
                    BinaryTree.this.clear();
                }
            };
        }
        return this._key_set[_KEY];
    }

    public Collection values() {
        if (this._value_collection[_KEY] == null) {
            this._value_collection[_KEY] = new AbstractCollection() {
                public Iterator iterator() {
                    return new BinaryTreeIterator(BinaryTree._KEY) {
                        /* access modifiers changed from: protected */
                        public Object doGetNext() {
                            return this._last_returned_node.getData(BinaryTree._VALUE);
                        }
                    };
                }

                public int size() {
                    return BinaryTree.this.size();
                }

                public boolean contains(Object o) {
                    return BinaryTree.this.containsValue(o);
                }

                public boolean remove(Object o) {
                    int old_size = BinaryTree.this._size;
                    BinaryTree.this.removeValue(o);
                    return BinaryTree.this._size != old_size;
                }

                /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<java.lang.Object>, for r5v0, types: [java.util.Collection<java.lang.Object>, java.util.Collection] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public boolean removeAll(java.util.Collection<java.lang.Object> r5) {
                    /*
                        r4 = this;
                        r0 = 0
                        java.util.Iterator r1 = r5.iterator()
                    L_0x0005:
                        boolean r2 = r1.hasNext()
                        if (r2 == 0) goto L_0x0019
                        org.apache.poi.util.BinaryTree r2 = org.apache.poi.util.BinaryTree.this
                        java.lang.Object r3 = r1.next()
                        java.lang.Object r2 = r2.removeValue(r3)
                        if (r2 == 0) goto L_0x0005
                        r0 = 1
                        goto L_0x0005
                    L_0x0019:
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.util.BinaryTree.C14205.removeAll(java.util.Collection):boolean");
                }

                public void clear() {
                    BinaryTree.this.clear();
                }
            };
        }
        return this._value_collection[_KEY];
    }

    public Set entrySet() {
        if (this._entry_set[_KEY] == null) {
            this._entry_set[_KEY] = new AbstractSet() {
                public Iterator iterator() {
                    return new BinaryTreeIterator(BinaryTree._KEY) {
                        /* access modifiers changed from: protected */
                        public Object doGetNext() {
                            return this._last_returned_node;
                        }
                    };
                }

                public boolean contains(Object o) {
                    boolean z = false;
                    if (!(o instanceof Entry)) {
                        return false;
                    }
                    Entry entry = (Entry) o;
                    Object value = entry.getValue();
                    Node node = BinaryTree.this.lookup((Comparable) entry.getKey(), BinaryTree._KEY);
                    if (node != null && node.getData(BinaryTree._VALUE).equals(value)) {
                        z = true;
                    }
                    return z;
                }

                public boolean remove(Object o) {
                    if (!(o instanceof Entry)) {
                        return false;
                    }
                    Entry entry = (Entry) o;
                    Object value = entry.getValue();
                    Node node = BinaryTree.this.lookup((Comparable) entry.getKey(), BinaryTree._KEY);
                    if (node == null || !node.getData(BinaryTree._VALUE).equals(value)) {
                        return false;
                    }
                    BinaryTree.this.doRedBlackDelete(node);
                    return true;
                }

                public int size() {
                    return BinaryTree.this.size();
                }

                public void clear() {
                    BinaryTree.this.clear();
                }
            };
        }
        return this._entry_set[_KEY];
    }
}
