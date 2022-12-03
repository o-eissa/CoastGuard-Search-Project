package code;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class GenericSearch {
	public static HashSet<String> visitedStates = new HashSet<String>();

	public static String BFS(TreeNode node, boolean visualize) {
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

	public static String IDS(TreeNode node, boolean visualize) {

		int level = 0;
		ArrayList<TreeNode> q;

		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		int nodesSearched = 0;

		String solution = "No solution";

		while (solution.equals("No solution")) {

			// System.out.println("level: " + level);

			q = new ArrayList<TreeNode>();
			visitedStates = new HashSet<String>();
			q.add(node);

			while (!q.isEmpty()) {

				node = q.remove(0);
				nodesSearched++;

				if (node.actions.size() < level) {
					children = generateStates(node);
					// System.out.println("Actions Size: " + node.actions + " level: " +
					// level);
				} else {
					children.clear();
				}

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
					solution = res;
					return res;
				}

				q.addAll(children);

			}

			level++;
			solution = "No solution";

		}
		System.out.println("No solution");
		// return failure
		return "No Solution";

	}

	public static ArrayList<TreeNode> calcHeuristic1OnQueue(ArrayList<TreeNode> queue, boolean uniformCost) {
		for (TreeNode node : queue) {
			int alive = 0;
			int pickups = 0;
			int retrieves = 0;
			for (Ship ship : node.ships) {
				// Calculate Retrieves Neeeded
				if (ship.passengers != -20)
					retrieves++;
				if (ship.passengers > 0) {
					alive += ship.passengers;
					// Calculate Pickups Needed
					pickups++;
				}
			}
			// Calculate Drops Needed
			int drops = alive / (node.cgMaxCapacity + node.passengersCarried);
			node.h = pickups + retrieves + drops;

			if (uniformCost) {
				// Our cost is number of people dying due to this action
				// for (String action : node.actions) {
				// switch (action) {
				// case "pickup":
				// uc += (node.ships.size() - node.passengersCarried);
				// break;
				// case "retrieve":
				// uc += node.ships.size();
				// break;
				// case "drop":
				// uc += node.ships.size();
				// break;
				// default:
				// uc += node.ships.size();

				// }
				// }
				node.h += node.pathCost;
			}

		}
		return queue;
	}

	public static String GR1(TreeNode node, boolean visualize) {
		ArrayList<TreeNode> q = new ArrayList<TreeNode>();
		q.add(node);
		q = calcHeuristic1OnQueue(q, false);
		int nodesSearched = 0;
		while (!q.isEmpty()) {
			int minSoFar = 2147483647;
			int minIndex = 0;

			for (int i = 0; i < q.size(); i++) {
				node = q.get(i);
				if (node.h < minSoFar) {
					minSoFar = node.h;
					minIndex = i;
				}
			}

			node = q.remove(minIndex);
			// System.out.println(node.depth);

			// if (!q.isEmpty())
			// if (node.depth < q.get(0).depth)
			// System.out.println(q.get(0).depth);

			// System.out.println("dequeue " + node.operator);
			// System.out.println(visualizeGrid(state));

			nodesSearched++;

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

			ArrayList<TreeNode> children = generateStates(node);
			children = calcHeuristic1OnQueue(children, false);

			// System.out.println(children.size());
			q.addAll(children);
		}
		// return failure
		return "No Solution";

	}

	public static ArrayList<TreeNode> calcHeuristic2OnQueue(ArrayList<TreeNode> queue, boolean uniformCost) {
		// Same as First heuristic but giving weights to actions

		// Another Heurstics is getting calculating the minimum distance to ship+this
		// ship to nearest station (To be implemented)
		for (TreeNode node : queue) {
			int alive = 0;
			int pickups = 0;
			int retrieves = 0;
			for (Ship ship : node.ships) {
				// Calculate Retrieves Neeeded
				if (ship.passengers != -20)
					retrieves++;
				if (ship.passengers > 0) {
					alive += ship.passengers;
					// Calculate Pickups Needed
					pickups++;
				}
			}
			// Calculate Drops Needed
			int drops = alive / (node.cgMaxCapacity + node.passengersCarried);

			// Weighing every action as states with less pickups is closer to goal
			pickups *= 4;
			retrieves *= 1;
			drops *= 2;

			node.h = pickups + retrieves + drops;
			if (uniformCost) {
				// Our cost is number of people dying due to this action
				// int uc = 0;
				// for (String action : node.actions) {
				// switch (action) {
				// case "pickup":
				// uc += (node.ships.size() - node.passengersCarried);
				// break;
				// case "retrieve":
				// uc += node.ships.size();
				// break;
				// case "drop":
				// uc += node.ships.size();
				// break;
				// default:
				// uc += node.ships.size();

				// }
				// }
				node.h += node.pathCost;
			}
		}
		return queue;
	}

	public static String GR2(TreeNode node, boolean visualize) {
		ArrayList<TreeNode> q = new ArrayList<TreeNode>();
		q.add(node);
		q = calcHeuristic2OnQueue(q, false);
		int nodesSearched = 0;
		while (!q.isEmpty()) {
			int minSoFar = 2147483647;
			int minIndex = 0;

			for (int i = 0; i < q.size(); i++) {
				node = q.get(i);
				if (node.h < minSoFar) {
					minSoFar = node.h;
					minIndex = i;
				}
			}

			node = q.remove(minIndex);
			// System.out.println(node.depth);

			// if (!q.isEmpty())
			// if (node.depth < q.get(0).depth)
			// System.out.println(q.get(0).depth);

			// System.out.println("dequeue " + node.operator);
			// System.out.println(visualizeGrid(state));

			nodesSearched++;

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

			ArrayList<TreeNode> children = generateStates(node);
			children = calcHeuristic2OnQueue(children, false);

			// System.out.println(children.size());
			q.addAll(children);
		}
		// return failure
		return "No Solution";

	}

	public static String AS1(TreeNode node, boolean visualize) {
		ArrayList<TreeNode> q = new ArrayList<TreeNode>();
		q.add(node);
		q = calcHeuristic1OnQueue(q, true);
		int nodesSearched = 0;
		while (!q.isEmpty()) {
			int minSoFar = 2147483647;
			int minIndex = 0;

			for (int i = 0; i < q.size(); i++) {
				node = q.get(i);
				if (node.h < minSoFar) {
					minSoFar = node.h;
					minIndex = i;
				}
			}

			node = q.remove(minIndex);
			// System.out.println(node.depth);

			// if (!q.isEmpty())
			// if (node.depth < q.get(0).depth)
			// System.out.println(q.get(0).depth);

			// System.out.println("dequeue " + node.operator);
			// System.out.println(visualizeGrid(state));

			nodesSearched++;

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

			ArrayList<TreeNode> children = generateStates(node);
			children = calcHeuristic1OnQueue(children, true);

			// System.out.println(children.size());
			q.addAll(children);
		}
		// return failure
		return "No Solution";

	}

	public static String AS2(TreeNode node, boolean visualize) {
		ArrayList<TreeNode> q = new ArrayList<TreeNode>();
		q.add(node);
		q = calcHeuristic2OnQueue(q, true);
		int nodesSearched = 0;
		while (!q.isEmpty()) {
			int minSoFar = 2147483647;
			int minIndex = 0;

			for (int i = 0; i < q.size(); i++) {
				node = q.get(i);
				if (node.h < minSoFar) {
					minSoFar = node.h;
					minIndex = i;
				}
			}

			node = q.remove(minIndex);
			// System.out.println(node.depth);

			// if (!q.isEmpty())
			// if (node.depth < q.get(0).depth)
			// System.out.println(q.get(0).depth);

			// System.out.println("dequeue " + node.operator);
			// System.out.println(visualizeGrid(state));

			nodesSearched++;

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

			ArrayList<TreeNode> children = generateStates(node);
			children = calcHeuristic2OnQueue(children, true);

			// System.out.println(children.size());
			q.addAll(children);
		}
		// return failure
		return "No Solution";

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
				child.pathCost += 1;
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// drop
			if (action == "drop") {
				child.drop();
				child.actions.add("drop");
				child.pathCost += 1;
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// retrieve
			if (action == "retrieve") {
				child.retrieve();
				child.actions.add("retrieve");
				child.pathCost += 1;
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
				child.pathCost += 5;
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// Move Right
			if (action == "right") {
				child.moveRight();
				child.actions.add("right");
				child.pathCost += 5;
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// Move Up
			if (action == "up") {
				child.moveUp();
				child.actions.add("up");
				child.pathCost += 5;
				child.damage();
				child.gridString = CoastGuard.encodeGrid(child);
				child.updateGrid();
			}

			// Move Down
			if (action == "down") {
				child.moveDown();
				child.actions.add("down");
				child.pathCost += 5;
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
