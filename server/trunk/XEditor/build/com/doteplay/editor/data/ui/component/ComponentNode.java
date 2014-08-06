/**
 * @package com.doteplay.editor.ui.component
 * @file ComponentNode.java
 */
package com.doteplay.editor.data.ui.component;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 */
public class ComponentNode implements MutableTreeNode {

	/**
	 * 父节点
	 */
	private transient MutableTreeNode parent = null;

	/**
	 * 子节点
	 */
	private transient Vector<MutableTreeNode> childrens = new Vector<MutableTreeNode>();

	/**
	 * 组件
	 */
	private GComponent component;

	private ComponentTree tree;

	public ComponentNode(GComponent component, ComponentTree tree) {
		this.setUserObject(component);
		this.tree = tree;
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
		this.childrens.add(index, child);
	}

	@Override
	public void remove(int index) {
		this.childrens.remove(index);
	}

	@Override
	public void remove(MutableTreeNode node) {
		this.childrens.remove(node);
	}

	@Override
	public void removeFromParent() {
		this.parent.remove(this);
	}

	@Override
	public void setParent(MutableTreeNode newParent) {
		this.parent = newParent;
	}

	@Override
	public void setUserObject(Object object) {
		component = (GComponent) object;
	}

	@Override
	public Enumeration<MutableTreeNode> children() {
		return childrens.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return childrens.get(childIndex);
	}

	@Override
	public int getChildCount() {
		if (childrens == null) {
			return 0;
		}
		return childrens.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return childrens.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	public ComponentNode getParentNode() {
		return (ComponentNode) parent;
	}

	public GComponent getParentGComponent() {
		ComponentNode c = (ComponentNode) parent;
		if (c == null) {
			return null;
		}
		return c.getComponent();
	}

	@Override
	public boolean isLeaf() {
		return (getChildCount() == 0);
	}

	public GComponent getComponent() {
		return this.component;
	}

	public void setComponentPos(Point p) {
		component.setPos(p);
	}
	
	public void setComponentPos(int sc_x, int sc_y) {
		component.setPos(sc_x, sc_y);
	}
	
	public Point getComponentPos() {
		return component.getPos();
	}

	public void setComponentSize(int w, int h) {
		component.setSize(w, h);
	}

	public void setComponentBounds(int sc_x, int sc_y) {
		component.setPos(sc_x, sc_y);
	}

	public Rectangle getComponentBounds() {
		return component.getBounds();
	}

	/**
	 * 是否属于该节点
	 * 
	 * @param node
	 * @return
	 */
	public boolean isBelongTo(TreeNode node) {
		TreeNode temp_node = this;
		while (temp_node != null) {
			if (temp_node.getParent() == node) {
				return true;
			}
			temp_node = temp_node.getParent();
		}
		return false;
	}

	public void add(MutableTreeNode child) {
		this.childrens.add(child);
	}

	public Point getAbsPos() {
		ComponentNode temp_node = this;
		Point p = this.getComponent().getPos();

		while (temp_node != null) {
			ComponentNode parent = (ComponentNode) temp_node.getParent();
			if (parent == null) {
				break;
			}
			Point parent_p = parent.getComponent().getPos();
			p.x += parent_p.x;
			p.y += parent_p.y;
			temp_node = (ComponentNode) temp_node.getParent();
		}
		return p;
	}
	
	public Rectangle getAbsBounds() {
		Point p = this.getAbsPos();
		Rectangle rect = getComponentBounds();
		return new Rectangle(p.x, p.y, rect.width, rect.height);
	}

	public void setBounds(Rectangle r) {
		Rectangle rect = this.getComponent().getBounds();
		for (int i = 0; i < childrens.size(); i++) {
			ComponentNode child = (ComponentNode) childrens.get(i);

		}
	}

	public void draw(Graphics2D g) {
		Rectangle rect = getAbsBounds();
		component.draw(g, rect.x, rect.y);
		for (int i = 0; i < childrens.size(); i++) {
			ComponentNode child = (ComponentNode) childrens.get(i);
			child.draw(g);
		}
	}

	@Override
	public String toString() {
		String s = "" + this.getComponent();
		return s;
	}

	@Override
	public ComponentNode clone() {
		GComponent c = this.getComponent().clone();
		ComponentNode n = new ComponentNode(c, this.tree);
		n.parent = this.parent;
		n.childrens = this.childrens;
		return n;
	}
}
