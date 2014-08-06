/**
 * @package com.doteplay.editor.ui.component
 * @file ComponentGroup.java
 */
package com.doteplay.editor.data.ui.component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.doteyplay.game.editor.UIType;
import com.doteplay.editor.common.ISavebleData;

/**
 */
public class ComponentTree implements ISavebleData, TreeModel {

	private ComponentNode root;

	protected EventListenerList listenerList = new EventListenerList();

	protected boolean asksAllowsChildren;

	public ComponentTree() {
		root = newNode(null, UIType.Panel, 0, "根面板");
	}

	/**
	 * 添加节点
	 * 
	 * @param parent
	 * @param type
	 * @param id
	 * @param name
	 * @return
	 */
	public ComponentNode newNode(ComponentNode parent, UIType type, int id, String name) {

		GComponent component = ComponentFactory.create(type);
		component.setId(id);
		component.setName(name);

		ComponentNode node = new ComponentNode(component, this);
		addNode(node, parent);
		return node;
	}

	public void addNode(ComponentNode node, ComponentNode parent) {
		node.setParent(parent);
		if (parent != null) {
			parent.add(node);
		}
	}

	/**
	 * 删除节点
	 * 
	 * @param resourceType
	 * @return
	 */
	public boolean deleteNode(ComponentNode node) {
		node.removeFromParent();
		return true;
	}

	// public TreePath getPathToRoot(ComponentNode node) {
	// TreePath path;
	// ComponentNode temp = node;
	// while (temp.getParent() != null) {
	// TreePath parentPath = new TreePath(temp.getParent());
	// path = new TreePath(temp);
	// temp = (ComponentNode)temp.getParent();
	// }
	// return path;
	// }

	public ComponentNode[] getPath(ComponentNode node) {
		List<ComponentNode> nodeList = new ArrayList<ComponentNode>();
		ComponentNode temp = node;
		nodeList.add(temp);
		while (temp.getParent() != null) {
			temp = (ComponentNode) temp.getParent();
			nodeList.add(temp);
		}

		int size = nodeList.size();
		ComponentNode[] nodes = new ComponentNode[size];
		for (int i = 0; i < size; i++) {
			ComponentNode n = nodeList.get(i);
			nodes[size - i - 1] = n;
		}
		return nodes;
	}

	/**
	 * 返回节点列表
	 * 
	 * @return
	 */
	public synchronized List<ComponentNode> getAllNodeList() {

		List<ComponentNode> nodeList = new ArrayList<ComponentNode>();

		Stack<ComponentNode> todo = new Stack<ComponentNode>();
		todo.push(root);
		while (!todo.isEmpty()) {
			ComponentNode old = todo.pop();
			nodeList.add(old);
			for (Enumeration<MutableTreeNode> e = old.children(); e.hasMoreElements();) {
				ComponentNode n = (ComponentNode) e.nextElement();
				if (!n.isLeaf()) {
					todo.push(n);
				}
				else {
					nodeList.add(n);
				}
			}
		}
		return nodeList;
	}

	@Override
	public void init(DataInputStream in) throws Exception {
		List<ComponentNode> nodeList = new ArrayList<ComponentNode>();
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			UIType componentType = UIType.valueOf(in.readInt());
			GComponent component = ComponentFactory.create(componentType);
			component.init(in);
			ComponentNode node = new ComponentNode(component, this);
			nodeList.add(node);
		}

		for (int i = 0; i < size; i++) {
			int parentNodeIndex = in.readInt();
			if (parentNodeIndex != -1) {
				ComponentNode parent = nodeList.get(parentNodeIndex);
				addNode(nodeList.get(i), parent);
			}
			else {
				addNode(nodeList.get(i), null);
			}

		}

		root = nodeList.get(0);

	}

	@Override
	public void save(DataOutputStream out) throws Exception {
		List<ComponentNode> nodeList = getAllNodeList();
		int size = nodeList.size();
		out.writeInt(size);
		for (int i = 0; i < size; i++) {
			ComponentNode node = nodeList.get(i);
			GComponent component = node.getComponent();
			out.writeInt(component.getType().ordinal());
			component.save(out);
		}

		for (int i = 0; i < size; i++) {
			ComponentNode node = nodeList.get(i);
			ComponentNode parentNode = (ComponentNode) node.getParent();
			if (parentNode == null) {
				out.writeInt(-1);
			}
			else {
				out.writeInt(nodeList.indexOf(parentNode));
			}
		}
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception {

	}

	@Override
	public void saveServer(DataOutputStream out) throws Exception {

	}

	public ComponentNode getRoot() {
		return this.root;
	}

	public void setRoot(ComponentNode root) {
		this.root = root;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(TreeModelListener.class, l);
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((TreeNode) parent).getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((TreeNode) parent).getChildCount();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == null || child == null)
			return -1;
		return ((TreeNode) parent).getIndex((TreeNode) child);
	}

	@Override
	public boolean isLeaf(Object node) {
		if (asksAllowsChildren)
			return !((TreeNode) node).getAllowsChildren();
		return ((TreeNode) node).isLeaf();
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(TreeModelListener.class, l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		MutableTreeNode aNode = (MutableTreeNode) path.getLastPathComponent();
		aNode.setUserObject(newValue);
		nodeChanged(aNode);
	}

	public void nodeChanged(TreeNode node) {
		if (listenerList != null && node != null) {
			TreeNode parent = node.getParent();

			if (parent != null) {
				int anIndex = parent.getIndex(node);
				if (anIndex != -1) {
					int[] cIndexs = new int[1];

					cIndexs[0] = anIndex;
					nodesChanged(parent, cIndexs);
				}
			}
			else if (node == getRoot()) {
				nodesChanged(node, null);
			}
		}
	}

	public void nodesChanged(TreeNode node, int[] childIndices) {
		if (node != null) {
			if (childIndices != null) {
				int cCount = childIndices.length;

				if (cCount > 0) {
					Object[] cChildren = new Object[cCount];

					for (int counter = 0; counter < cCount; counter++)
						cChildren[counter] = node.getChildAt(childIndices[counter]);
					fireTreeNodesChanged(this, getPathToRoot(node), childIndices, cChildren);
				}
			}
			else if (node == getRoot()) {
				fireTreeNodesChanged(this, getPathToRoot(node), null, null);
			}
		}
	}

	public TreeNode[] getPathToRoot(TreeNode aNode) {
		return getPathToRoot(aNode, 0);
	}

	protected TreeNode[] getPathToRoot(TreeNode aNode, int depth) {
		TreeNode[] retNodes;
		if (aNode == null) {
			if (depth == 0)
				return null;
			else
				retNodes = new TreeNode[depth];
		}
		else {
			depth++;
			if (aNode == root)
				retNodes = new TreeNode[depth];
			else
				retNodes = getPathToRoot(aNode.getParent(), depth);
			retNodes[retNodes.length - depth] = aNode;
		}
		return retNodes;
	}

	protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
	}
}
