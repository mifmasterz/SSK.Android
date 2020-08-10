package org.apache.commons.math3.geometry.partitioning.utilities;

import java.lang.Comparable;

@Deprecated
public class AVLTree<T extends Comparable<T>> {
    /* access modifiers changed from: private */
    public Node top = null;

    public class Node {
        /* access modifiers changed from: private */
        public T element;
        /* access modifiers changed from: private */
        public Node left = null;
        private Node parent;
        /* access modifiers changed from: private */
        public Node right = null;
        private Skew skew;

        Node(T element2, Node parent2) {
            this.element = element2;
            this.parent = parent2;
            this.skew = Skew.BALANCED;
        }

        public T getElement() {
            return this.element;
        }

        /* access modifiers changed from: 0000 */
        public int size() {
            int i = 0;
            int size = (this.left == null ? 0 : this.left.size()) + 1;
            if (this.right != null) {
                i = this.right.size();
            }
            return size + i;
        }

        /* access modifiers changed from: 0000 */
        public Node getSmallest() {
            Node node = this;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        /* access modifiers changed from: 0000 */
        public Node getLargest() {
            Node node = this;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        public Node getPrevious() {
            if (this.left != null) {
                Node node = this.left.getLargest();
                if (node != null) {
                    return node;
                }
            }
            for (Node node2 = this; node2.parent != null; node2 = node2.parent) {
                if (node2 != node2.parent.left) {
                    return node2.parent;
                }
            }
            return null;
        }

        public Node getNext() {
            if (this.right != null) {
                Node node = this.right.getSmallest();
                if (node != null) {
                    return node;
                }
            }
            for (Node node2 = this; node2.parent != null; node2 = node2.parent) {
                if (node2 != node2.parent.right) {
                    return node2.parent;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public boolean insert(T newElement) {
            boolean z = false;
            if (newElement.compareTo(this.element) < 0) {
                if (this.left == null) {
                    this.left = new Node<>(newElement, this);
                    return rebalanceLeftGrown();
                }
                if (this.left.insert(newElement)) {
                    z = rebalanceLeftGrown();
                }
                return z;
            } else if (this.right == null) {
                this.right = new Node<>(newElement, this);
                return rebalanceRightGrown();
            } else {
                if (this.right.insert(newElement)) {
                    z = rebalanceRightGrown();
                }
                return z;
            }
        }

        public void delete() {
            Node child;
            boolean leftShrunk;
            Node node;
            if (this.parent == null && this.left == null && this.right == null) {
                this.element = null;
                AVLTree.this.top = null;
            } else {
                if (this.left == null && this.right == null) {
                    node = this;
                    this.element = null;
                    leftShrunk = node == node.parent.left;
                    child = null;
                } else {
                    node = this.left != null ? this.left.getLargest() : this.right.getSmallest();
                    this.element = node.element;
                    leftShrunk = node == node.parent.left;
                    child = node.left != null ? node.left : node.right;
                }
                Node node2 = node.parent;
                if (leftShrunk) {
                    node2.left = child;
                } else {
                    node2.right = child;
                }
                if (child != null) {
                    child.parent = node2;
                }
                while (true) {
                    if (!leftShrunk) {
                        if (!node2.rebalanceRightShrunk()) {
                            break;
                        }
                    } else if (!node2.rebalanceLeftShrunk()) {
                        break;
                    }
                    if (node2.parent != null) {
                        leftShrunk = node2 == node2.parent.left;
                        node2 = node2.parent;
                    } else {
                        return;
                    }
                }
            }
        }

        private boolean rebalanceLeftGrown() {
            switch (this.skew) {
                case LEFT_HIGH:
                    if (this.left.skew == Skew.LEFT_HIGH) {
                        rotateCW();
                        this.skew = Skew.BALANCED;
                        this.right.skew = Skew.BALANCED;
                    } else {
                        Skew s = this.left.right.skew;
                        this.left.rotateCCW();
                        rotateCW();
                        switch (s) {
                            case LEFT_HIGH:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.RIGHT_HIGH;
                                break;
                            case RIGHT_HIGH:
                                this.left.skew = Skew.LEFT_HIGH;
                                this.right.skew = Skew.BALANCED;
                                break;
                            default:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.BALANCED;
                                break;
                        }
                        this.skew = Skew.BALANCED;
                    }
                    return false;
                case RIGHT_HIGH:
                    this.skew = Skew.BALANCED;
                    return false;
                default:
                    this.skew = Skew.LEFT_HIGH;
                    return true;
            }
        }

        private boolean rebalanceRightGrown() {
            switch (this.skew) {
                case LEFT_HIGH:
                    this.skew = Skew.BALANCED;
                    return false;
                case RIGHT_HIGH:
                    if (this.right.skew == Skew.RIGHT_HIGH) {
                        rotateCCW();
                        this.skew = Skew.BALANCED;
                        this.left.skew = Skew.BALANCED;
                    } else {
                        Skew s = this.right.left.skew;
                        this.right.rotateCW();
                        rotateCCW();
                        switch (s) {
                            case LEFT_HIGH:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.RIGHT_HIGH;
                                break;
                            case RIGHT_HIGH:
                                this.left.skew = Skew.LEFT_HIGH;
                                this.right.skew = Skew.BALANCED;
                                break;
                            default:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.BALANCED;
                                break;
                        }
                        this.skew = Skew.BALANCED;
                    }
                    return false;
                default:
                    this.skew = Skew.RIGHT_HIGH;
                    return true;
            }
        }

        private boolean rebalanceLeftShrunk() {
            switch (this.skew) {
                case LEFT_HIGH:
                    this.skew = Skew.BALANCED;
                    return true;
                case RIGHT_HIGH:
                    if (this.right.skew == Skew.RIGHT_HIGH) {
                        rotateCCW();
                        this.skew = Skew.BALANCED;
                        this.left.skew = Skew.BALANCED;
                        return true;
                    } else if (this.right.skew == Skew.BALANCED) {
                        rotateCCW();
                        this.skew = Skew.LEFT_HIGH;
                        this.left.skew = Skew.RIGHT_HIGH;
                        return false;
                    } else {
                        Skew s = this.right.left.skew;
                        this.right.rotateCW();
                        rotateCCW();
                        switch (s) {
                            case LEFT_HIGH:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.RIGHT_HIGH;
                                break;
                            case RIGHT_HIGH:
                                this.left.skew = Skew.LEFT_HIGH;
                                this.right.skew = Skew.BALANCED;
                                break;
                            default:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.BALANCED;
                                break;
                        }
                        this.skew = Skew.BALANCED;
                        return true;
                    }
                default:
                    this.skew = Skew.RIGHT_HIGH;
                    return false;
            }
        }

        private boolean rebalanceRightShrunk() {
            switch (this.skew) {
                case LEFT_HIGH:
                    if (this.left.skew == Skew.LEFT_HIGH) {
                        rotateCW();
                        this.skew = Skew.BALANCED;
                        this.right.skew = Skew.BALANCED;
                        return true;
                    } else if (this.left.skew == Skew.BALANCED) {
                        rotateCW();
                        this.skew = Skew.RIGHT_HIGH;
                        this.right.skew = Skew.LEFT_HIGH;
                        return false;
                    } else {
                        Skew s = this.left.right.skew;
                        this.left.rotateCCW();
                        rotateCW();
                        switch (s) {
                            case LEFT_HIGH:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.RIGHT_HIGH;
                                break;
                            case RIGHT_HIGH:
                                this.left.skew = Skew.LEFT_HIGH;
                                this.right.skew = Skew.BALANCED;
                                break;
                            default:
                                this.left.skew = Skew.BALANCED;
                                this.right.skew = Skew.BALANCED;
                                break;
                        }
                        this.skew = Skew.BALANCED;
                        return true;
                    }
                case RIGHT_HIGH:
                    this.skew = Skew.BALANCED;
                    return true;
                default:
                    this.skew = Skew.LEFT_HIGH;
                    return false;
            }
        }

        private void rotateCW() {
            T tmpElt = this.element;
            this.element = this.left.element;
            this.left.element = tmpElt;
            Node tmpNode = this.left;
            this.left = tmpNode.left;
            tmpNode.left = tmpNode.right;
            tmpNode.right = this.right;
            this.right = tmpNode;
            if (this.left != null) {
                this.left.parent = this;
            }
            if (this.right.right != null) {
                this.right.right.parent = this.right;
            }
        }

        private void rotateCCW() {
            T tmpElt = this.element;
            this.element = this.right.element;
            this.right.element = tmpElt;
            Node tmpNode = this.right;
            this.right = tmpNode.right;
            tmpNode.right = tmpNode.left;
            tmpNode.left = this.left;
            this.left = tmpNode;
            if (this.right != null) {
                this.right.parent = this;
            }
            if (this.left.left != null) {
                this.left.left.parent = this.left;
            }
        }
    }

    private enum Skew {
        LEFT_HIGH,
        RIGHT_HIGH,
        BALANCED
    }

    public void insert(T element) {
        if (element == null) {
            return;
        }
        if (this.top == null) {
            this.top = new Node<>(element, null);
        } else {
            this.top.insert(element);
        }
    }

    public boolean delete(T element) {
        if (element != null) {
            Node node = getNotSmaller(element);
            while (node != null) {
                if (node.element == element) {
                    node.delete();
                    return true;
                } else if (node.element.compareTo(element) > 0) {
                    return false;
                } else {
                    node = node.getNext();
                }
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return this.top == null;
    }

    public int size() {
        if (this.top == null) {
            return 0;
        }
        return this.top.size();
    }

    public Node getSmallest() {
        if (this.top == null) {
            return null;
        }
        return this.top.getSmallest();
    }

    public Node getLargest() {
        if (this.top == null) {
            return null;
        }
        return this.top.getLargest();
    }

    public Node getNotSmaller(T reference) {
        Node candidate = null;
        Node node = this.top;
        while (node != null) {
            if (node.element.compareTo(reference) >= 0) {
                candidate = node;
                if (node.left == null) {
                    return candidate;
                }
                node = node.left;
            } else if (node.right == null) {
                return candidate;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    public Node getNotLarger(T reference) {
        Node candidate = null;
        Node node = this.top;
        while (node != null) {
            if (node.element.compareTo(reference) <= 0) {
                candidate = node;
                if (node.right == null) {
                    return candidate;
                }
                node = node.right;
            } else if (node.left == null) {
                return candidate;
            } else {
                node = node.left;
            }
        }
        return null;
    }
}
