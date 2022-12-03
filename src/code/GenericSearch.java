package code;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class GenericSearch {
	static HashSet<String> visitedStates = new HashSet<String>();

	public static String BFS(TreeNode node, boolean visualize) {
		// finalGrid.getAllActions();
		int nodesSearched = 0;
		ArrayList<TreeNode> queue = new ArrayList<>();
		queue.add(node);
		int counter = 0;
		while (!queue.isEmpty()) {
			counter++;
			// System.out.println("Actions in queue: ");
			// for (TreeNode n : queue) {
			// System.out.print(n.actions + ", ");
			// // System.out.println("Grid of queue" + n.gridString);
			// // System.out.println();
			// }
			// System.out.println();
			node = queue.remove(0);
			nodesSearched++;

			// System.out.println("Nodes Searched: " + nodesSearched);
			// if (nodesSearched > 15)
			// return "stop";
			// System.out.println("Node: " + node);
			// System.out.println("MY COORDINATES: " + node.cgX + " " + node.cgY);
			// for (Ship s : node.ships) {
			// s.printShip();
			// }
			// if (node.actions.size() > 0) {
			// System.out.println("Action from parent: " +
			// node.actions.get(node.actions.size() - 1));
			// System.out.println("GridString: " + node.gridString);

			// }
			// System.out.println("Parent: " + node.parent);
			// System.out.println("Nodes Searched" + nodesSearched);

			// node.printGrid();
			// System.out.println(node.gridString);
			// System.out.println();

			if (goalTest(node)) {
				System.out.println(node.actions);

				String res = "";
				for (String action : node.actions)
					res += action + ",";
				res = res.substring(0, res.length() - 1);
				res += ";";
				res += node.deaths + ";";
				res += node.blackBoxRetrived + ";";
				res += nodesSearched + "";

				while (node != null) {
					System.out.println("Node: " + node);
					System.out.println("Parent: " + node.parent);
					if (node.actions.size() > 0)
						System.out.println("ACtions: " + node.actions.get(node.actions.size() - 1));
					if (visualize)
						node.printGrid();
					node = node.parent;
					System.err.println();
				}
				return res;
			}

			queue.addAll(generateStates(node));

		}
		return "No Solution found";

	}

	public static String DFS(TreeNode node, boolean visualize) {
		// finalGrid.getAllActions();
		int nodesSearched = 0;
		ArrayList<TreeNode> queue = new ArrayList<>();
		queue.add(node);
		while (!queue.isEmpty()) {
			// System.out.println("Actions in queue: ");
			// for (TreeNode n : queue) {
			// System.out.print(n.actions + ", ");
			// // System.out.println("Grid of queue" + n.gridString);
			// // System.out.println();
			// }
			// System.out.println();
			node = queue.remove(0);
			nodesSearched++;

			// System.out.println("Nodes Searched: " + nodesSearched);
			// if (nodesSearched > 15)
			// return "stop";
			// System.out.println("Node: " + node);
			// System.out.println("MY COORDINATES: " + node.cgX + " " + node.cgY);
			// for (Ship s : node.ships) {
			// s.printShip();
			// }
			// if (node.actions.size() > 0) {
			// System.out.println("Action from parent: " +
			// node.actions.get(node.actions.size() - 1));
			// System.out.println("GridString: " + node.gridString);

			// }
			// System.out.println("Parent: " + node.parent);
			// System.out.println("Nodes Searched" + nodesSearched);

			// node.printGrid();
			// System.out.println(node.gridString);
			// System.out.println();

			if (goalTest(node)) {
				System.out.println(node.actions);

				String res = "";
				for (String action : node.actions)
					res += action + ",";
				res = res.substring(0, res.length() - 1);
				res += ";";
				res += node.deaths + ";";
				res += node.blackBoxRetrived + ";";
				res += nodesSearched + "";

				while (node != null) {
					System.out.println("Node: " + node);
					System.out.println("Parent: " + node.parent);
					if (node.actions.size() > 0)
						System.out.println("ACtions: " + node.actions.get(node.actions.size() - 1));
					if (visualize)
						node.printGrid();
					node = node.parent;
					System.err.println();
				}
				return res;
			}

			queue.addAll(0, generateStates(node));

		}
		return "No Solution found";
	}

	private static ArrayList<TreeNode> generateStates(TreeNode node) {
		ArrayList<String> possibleActions = node.getAllPossibleActions();
		// System.out.println(possibleActions);
		ArrayList<TreeNode> children = new ArrayList<>();
		for (String action : possibleActions) {
			// Pickup
			// if (action != "up" && action != "retrieve")
			// continue;
			TreeNode child = node.makeChild();
			if (action == "pickup") {
				// Do something
				child.pickUp();
				child.actions.add("pickup");
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// drop
			if (action == "drop") {
				child.drop();
				child.actions.add("drop");
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// retrieve
			if (action == "retrieve") {
				child.retrieve();
				child.actions.add("retrieve");
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
				// System.out.println("Goal:" + goalTest(child));
				// child.printGrid();
				// for (Ship s : child.ships) {
				// System.err.println("update " + s.sunk + " " + s.wreck);
				// }
				// System.err.println("Child: " + child);
			}

			// Move Left
			if (action == "left") {
				child.moveLeft();
				child.actions.add("left");
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// Move Right
			if (action == "right") {
				child.moveRight();
				child.actions.add("right");
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// Move Up
			if (action == "up") {
				child.moveUp();
				child.actions.add("up");
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// Move Down
			if (action == "down") {
				child.moveDown();
				child.actions.add("down");
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}
			if (!visitedStates.contains(child.gridString)) {
				children.add(child);
				visitedStates.add(child.gridString);
			}
		}
		return children;
	}

	private static boolean goalTest(TreeNode node) {
		// No living passengers who are not rescued
		for (Ship s : node.ships) {
			if (s.passengers > 0)
				return false;
		}

		// There are no undamaged boxes which have not been retrieved
		for (Ship s : node.ships) {
			if (s.passengers < 0 && s.passengers > -20)
				return false;

		}

		// the rescue boat is not carrying any passengers
		if (node.passengersCarried > 0)
			return false;
		return true;
	}
}
