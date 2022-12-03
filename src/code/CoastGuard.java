package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CoastGuard extends GenericSearch {

	public static String genGrid() {
		String result = "";
		String ships = "";
		String stations = "";

		// m and n positions for grid
		int mGrid = (int) (Math.random() * 11 + 5);
		int nGrid = (int) (Math.random() * 11 + 5);

		String[][] grid = new String[mGrid][nGrid];
		result += mGrid + "," + nGrid + ";";

		// position of coast guard
		int coastGuardX = (int) (Math.random() * mGrid);
		int coastGuardY = (int) (Math.random() * nGrid);

		// number of passengers on coast guard
		int coastGuardPassengers = (int) (Math.random() * 71 + 30);

		grid[coastGuardX][coastGuardY] = "Coast Guard";
		result += coastGuardX + "," + coastGuardY + ";" + coastGuardPassengers + ";";

		// position of first ship
		int shipX = (int) (Math.random() * mGrid);
		int shipY = (int) (Math.random() * nGrid);
		while (grid[shipX][shipY] != null) {
			shipX = (int) (Math.random() * mGrid);
			shipY = (int) (Math.random() * nGrid);
		}
		int passangers = (int) (Math.random() * 101 + 1);
		grid[shipX][shipY] = "Ship(" + passangers + ")";
		ships += shipX + "," + shipY + "," + passangers;

		// position of first station
		int stationX = (int) (Math.random() * mGrid);
		int stationY = (int) (Math.random() * nGrid);
		while (grid[stationX][stationY] != null) {
			stationX = (int) (Math.random() * mGrid);
			stationY = (int) (Math.random() * nGrid);
		}
		grid[stationX][stationY] = "Station";
		stations += stationX + "," + stationY;

		// generate n number of ships
		int maxPositions = (mGrid * nGrid) - 3;
		int numberOfShips = (int) (Math.random() * maxPositions);
		ships += genShips(grid, mGrid, nGrid, numberOfShips);

		// generate n number of stations
		stations += genStations(grid, mGrid, nGrid, numberOfShips);
		result += stations + ";" + ships + ";";

		return result;
	}

	public static String encodeGrid(TreeNode node) {
		String result = "";
		String ships = "";
		String stations = "";

		// m and n positions for grid

		String[][] grid = new String[node.mGrid][node.nGrid];
		result += node.nGrid + "," + node.mGrid + ";";

		// position of coast guard
		int coastGuardX = node.cgX;
		int coastGuardY = node.cgY;

		// number of passengers on coast guard
		int coastGuardPassengers = node.cgMaxCapacity;

		grid[coastGuardX][coastGuardY] = "Coast Guard";
		result += coastGuardPassengers + ";" + coastGuardX + "," + coastGuardY + ";";

		ships += encodeShips(node, grid);

		// generate n number of stations
		stations += encodeStations(node, grid);
		result += stations + ";" + ships + ";";
		return result;
	}

	public static String encodeShips(TreeNode node, String[][] grid) {
		int shipX;
		int shipY;
		int passengers;
		String ships = "";

		// place n number of ships at random positions
		for (int i = 0; i < node.ships.size(); i++) {
			shipX = node.ships.get(i).shipX;
			shipY = node.ships.get(i).shipY;
			passengers = node.ships.get(i).passengers;
			grid[shipX][shipY] = "Ship(" + passengers + ")";

			// append to string
			ships += shipX + "," + shipY + "," + passengers + ",";
		}
		return ships.substring(0, ships.length() - 1);
	}

	public static String encodeStations(TreeNode node, String[][] grid) {
		String stations = "";
		for (int i = 0; i < node.stations.size(); i++) {
			int stationX = node.stations.get(i).stationX;
			int stationY = node.stations.get(i).stationY;
			grid[stationX][stationY] = "Station";
			// append to string
			stations += stationX + "," + stationY + ",";
		}
		return stations.substring(0, stations.length() - 1);

	}

	public static String genShips(String[][] grid, int mGrid, int nGrid, int numberOfShips) {
		int shipX;
		int shipY;
		int passangers;
		String ships = "";

		// place n number of ships at random positions
		for (int i = 0; i < numberOfShips; i++) {
			shipX = (int) (Math.random() * mGrid);
			shipY = (int) (Math.random() * nGrid);
			while (grid[shipX][shipY] != null) {
				shipX = (int) (Math.random() * mGrid);
				shipY = (int) (Math.random() * nGrid);
			}
			passangers = (int) (Math.random() * 101 + 1);
			grid[shipX][shipY] = "Ship(" + passangers + ")";

			// append to string
			ships += "," + shipX + "," + shipY + "," + passangers;
		}
		return ships;
	}

	public static String genStations(String[][] grid, int mGrid, int nGrid, int numberOfShips) {
		int stationX;
		int stationY;
		String stations = "";
		int maxPositions = (mGrid * nGrid) - 3 - numberOfShips;
		int numberOfStations = (int) (Math.random() * maxPositions);
		// place n number of stations at random positions
		for (int i = 0; i < numberOfStations; i++) {
			stationX = (int) (Math.random() * mGrid);
			stationY = (int) (Math.random() * nGrid);
			while (grid[stationX][stationY] != null) {
				stationX = (int) (Math.random() * mGrid);
				stationY = (int) (Math.random() * nGrid);
			}
			grid[stationX][stationY] = "Station";
			// append to string
			stations += "," + stationX + "," + stationY;
		}
		return stations;

	}

	public static String solve(String grid, String strategy, boolean visualize) {
		TreeNode root = new TreeNode(null, new ArrayList<String>(), 0, 0, 0, 0, grid);
		CoastGuard.visitedStates = new HashSet<>();

		switch (strategy) {
			case "BF":
				return BFS(root, visualize);
			case "DF":
				return DFS(root, visualize);
			case "ID":
				return IDS(root, visualize);
			case "GR1":
				return GR1(root, visualize);
			case "GR2":
				return GR2(root, visualize);
			case "AS1":
				return AS1(root, visualize);
			case "AS2":
				return AS2(root, visualize);
			default:
				return "Strategy Not Found";
		}
	}

	static class Checker {

		byte a;
		byte b;
		HashMap<String, Byte> ss = new HashMap<String, Byte>();
		ArrayList<String> is = new ArrayList<String>();
		byte s;
		int r;
		int d;
		byte x00;
		byte x01;
		byte xc;
		byte cp;

		public Checker(byte m, byte n, byte x, byte x00, byte x01, ArrayList<String> st, HashMap<String, Byte> sh) {
			this.a = m;
			this.b = n;
			this.xc = x;
			this.x00 = x00;
			this.x01 = x01;
			this.is = st;
			this.ss = sh;

		}

		boolean f1(int z, int k) {
			if (!f99(x00 + z, x01 + k)) {
				mn();
				return false;
			}

			this.x00 += z;
			this.x01 += k;
			mn();
			return true;
		}

		boolean f2() {

			if (!ss.containsKey(x00 + "," + x01)) {
				mn();
				return false;
			}
			if (ss.get(x00 + "," + x01) < 0) {
				mn();
				return false;
			}
			byte ts = ss.get(x00 + "," + x01);
			byte cc = (byte) (xc - cp);
			if (cc >= ts) {
				cp += ts;
				ss.replace(x00 + "," + x01, (byte) -20);
			} else {
				cp = xc;
				int n = ts - cc;
				ss.replace(x00 + "," + x01, (byte) n);
			}
			mn();
			return true;
		}

		boolean f3() {

			if (!is.contains(x00 + "," + x01)) {
				mn();
				return false;
			}
			s += cp;
			cp = 0;
			mn();
			return true;
		}

		boolean f4() {

			if (!ss.containsKey(x00 + "," + x01)) {
				mn();
				return false;
			}
			if (ss.get(x00 + "," + x01) < 0 && ss.get(x00 + "," + x01) > -20) {

				r += 1;
				ss.replace(x00 + "," + x01, (byte) 0);
				mn();
				return true;
			}
			return false;

		}

		boolean f99(int i, int j) {

			return i >= this.b || i < 0 || j >= this.a || j < 0 ? false : true;

		}

		void mn() {
			ArrayList<String> toclean = new ArrayList<String>();
			for (String k : ss.keySet()) {
				byte v = ss.get(k);
				if (v <= (byte) -1 && v >= (byte) -20)
					v++;
				else {
					if (v == 1) {
						v = (byte) -19;
						d++;
					} else {
						if (v > (byte) 1) {
							v--;
							d++;
						}
					}
				}

				if (v == 0) {
					toclean.add(k);
				} else {
					ss.replace(k, v);
				}

			}
			for (String c : toclean) {
				ss.remove(c);
			}

		}

		void clean() {
			for (String k : ss.keySet()) {
				if (ss.get(k).equals((byte) 0))
					ss.remove(k);
			}
		}

		public boolean cool() {
			return ss.size() == 0 && cp == 0;
		}

	}

	public static boolean applyPlan(String grid, String solution) {
		boolean linkin = true;
		String[] solutionArray = solution.split(";");
		String plan = solutionArray[0];
		int blue = Integer.parseInt(solutionArray[1]);
		int doors = Integer.parseInt(solutionArray[2]);

		plan.replace(" ", "");
		plan.replace("\n", "");
		plan.replace("\r", "");
		plan.replace("\n\r", "");
		plan.replace("\t", "");

		String[] actions = plan.split(",");

		String[] gridArray = grid.split(";");
		String[] dimensions = gridArray[0].split(",");
		byte m = Byte.parseByte(dimensions[0]);
		byte n = Byte.parseByte(dimensions[1]);

		byte x = Byte.parseByte(gridArray[1]);

		String[] xx = gridArray[2].split(",");
		byte x00 = Byte.parseByte(xx[0]);
		byte x01 = Byte.parseByte(xx[1]);

		String[] st = gridArray[3].split(",");
		ArrayList<String> xyz = new ArrayList<String>();
		for (int i = 0; i < st.length - 1; i += 2) {
			xyz.add(st[i] + "," + st[i + 1]);
		}

		String[] sh = gridArray[4].split(",");
		HashMap<String, Byte> m4 = new HashMap<String, Byte>();
		for (int i = 0; i < sh.length - 1; i += 3) {
			m4.put(sh[i] + "," + sh[i + 1], Byte.parseByte(sh[i + 2]));
		}
		Checker s = new Checker(m, n, x, x00, x01, xyz, m4);
		for (int i = 0; i < actions.length; i++) {

			switch (actions[i]) {
				case "up":
					linkin = s.f1(-1, 0);
					break;
				case "down":
					linkin = s.f1(1, 0);
					break;
				case "right":
					linkin = s.f1(0, 1);
					break;
				case "left":
					linkin = s.f1(0, -1);
					break;
				case "pickup":
					linkin = s.f2();
					break;
				case "drop":
					linkin = s.f3();
					break;
				case "retrieve":
					linkin = s.f4();
					break;
				default:
					linkin = false;
					break;

			}
			if (!linkin) {
				System.out.println("action that failed " + actions[i]);
				return false;
			}
		}

		return s.cool() && s.d == blue && s.r == doors;
	}

	public static void main(String[] args) {
		// String testGrid =
		// "10,6;59;1,7;0,0,2,2,3,0,5,3;1,3,69,3,4,80,4,7,94,4,9,14,5,2,39;";
		// String grid0 = "5,6;50;0,1;0,4,3,3;1,1,90;";
		// String grid1 = "6,6;52;2,0;2,4,4,0,5,4;2,1,19,4,2,6,5,0,8;";
		// String grid2 = "7,5;40;2,3;3,6;1,1,10,4,5,90;";
		// String grid3 = "8,5;60;4,6;2,7;3,4,37,3,5,93,4,0,40;";
		// String grid4 = "5,7;63;4,2;6,2,6,3;0,0,17,0,2,73,3,0,30;";
		// String grid5 = "5,5;69;3,3;0,0,0,1,1,0;0,3,78,1,2,2,1,3,14,4,4,9;";
		// String grid6 = "7,5;86;0,0;1,3,1,5,4,2;1,1,42,2,5,99,3,5,89;";
		// String grid7 = "6,7;82;1,4;2,3;1,1,58,3,0,58,4,2,72;";
		// String grid8 = "6,6;74;1,1;0,3,1,0,2,0,2,4,4,0,4,2,5,0;0,0,78,3,3,5,4,3,40;";
		// String grid9 = "7,5;100;3,4;2,6,3,5;0,0,4,0,1,8,1,4,77,1,5,1,3,2,94,4,3,46;";
		// String grid10 =
		// "10,6;59;1,7;0,0,2,2,3,0,5,3;1,3,69,3,4,80,4,7,94,4,9,14,5,2,39;";

		// String solution = solve(grid0, "BF", false);
		// System.out.println(solution);
		// solution = solution.replace(" ", "");
		// System.out.println("Solution1:" + solution);
		// System.out.println(applyPlan(grid0, solution));
		// System.out.println("Solution1:" + solution);

		// System.out.println("\n Now DF on:" + grid0 + "\n");

		// String solution = CoastGuard.solve(grid1, "AS1", false);
		// solution = solution.replace(" ", "");
		// System.out.println(solution);
		// System.out.println(
		// applyPlan(grid1, solution));

		// String solution = solve(grid0, "DF", false);
		// System.out.println(solution);
		// solution2 = solution.replace(" ", "");
		// System.out.println(applyPlan(grid0, solution));

		// Multiple retrieves
		// Uncounted retrieves

	}
}
// [up, up, pickup, retrieve, up, pickup, down, down, down, down, right,
// retrieve, up, up, up, up, left, down, down, down, down, right, up, up, up,
// up, left, retrieve, down, down, down, down, left, left, up, up, up, up, drop]

// [up, up, pickup, retrieve, up, pickup, down, down, down, down, right,
// retrieve, up, up, up, up, left, down, down, down, down, right, up, up, up,
// up, left, retrieve, down, down, down, down, right, up, up, up, up, left]