package dke.extension.data.dimension;

import java.util.LinkedList;


public class DimensionTree<T> {
  private DimensionNode<T> root;

    public DimensionTree(T name) {
        setRoot(new DimensionNode<T>());
        getRoot().setName(name);
        getRoot().setChildren(new LinkedList<DimensionNode<T>>());
      }

    public DimensionNode<T> getRoot() {
        return root;
    }

    public void setRoot(DimensionNode<T> root) {
        this.root = root;
    }
}
