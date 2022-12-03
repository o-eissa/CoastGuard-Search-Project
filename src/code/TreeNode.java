package code;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	TreeNode parent;
	int pathCost;
	int deaths;
	int blackBoxRetrived;
	int passengersCarried;

	List<Station> stations = new ArrayList<Station>();
	List<Ship> ships = new ArrayList<Ship>();
	int mGrid = 0;
	int nGrid = 0;
	int cgX = 0;
	int cgY = 0;
	int cgMaxCapacity = 0;
	ArrayList<String> actions = new ArrayList<>();
	String[][] grid;
	String gridString;

	public TreeNode(TreeNode parent, ArrayList<String> actions, int pathCost, int deaths, int blackBoxRetrived,
			int passengersCarried, String gridString) {
		this.parent = parent;
		this.actions = actions;
		this.pathCost = pathCost;
		this.deaths = deaths;
		this.blackBoxRetrived = blackBoxRetrived;
		this.passengersCarried = passengersCarried;
		this.gridString = gridString;

		this.grid = parseGridString(gridString);
	}

	public String[][] parseGridString(String gridString) {
		String[] splitList = gridString.split(";");
		String[] result;

		// Split string
		// "3,4;32;0,1;0,1;3,2,65;"
		for (int i = 0; i < splitList.length; i++) {
			result = splitList[i].split(",");
			switch (i) {
				case 0:
					this.nGrid = Integer.parseInt(result[0]);
					this.mGrid = Integer.parseInt(result[1]);
					break;
				case 1:
					this.cgMaxCapacity = Integer.parseInt(result[0]);
					break;
				case 2:
					this.cgX = Integer.parseInt(result[0]);
					this.cgY = Integer.parseInt(result[1]);
					break;
				case 3:
					for (int j = 0; j < result.length - 1; j += 2) {
						Station tempStation = new Station(Integer.parseInt(result[j]), Integer.parseInt(result[j + 1]));
						this.stations.add(tempStation);
					}
					break;
				case 4:
					for (int j = 0; j < result.length - 2; j += 3) {
						int passengers = Integer.parseInt(result[j + 2]);

						Ship tempShip = new Ship(Integer.parseInt(result[j]), Integer.parseInt(result[j + 1]),
								passengers);
						this.ships.add(tempShip);
					}
					break;
			}
		}

		// create Grid
		String[][] grid = new String[mGrid][nGrid];
		grid[this.cgX][this.cgY] = "CG";

		for (int i = 0; i < stations.size(); i++) {
			int stationX = stations.get(i).stationX;
			int stationY = stations.get(i).stationY;
			if (grid[stationX][stationY] == null)
				grid[stationX][stationY] = "Station";
			else
				grid[stationX][stationY] = grid[stationX][stationY] + ",Station";
		}

		for (int i = 0; i < ships.size(); i++) {
			int shipX = ships.get(i).shipX;
			int shipY = ships.get(i).shipY;
			int passangers = ships.get(i).passengers;
			if (grid[shipX][shipY] == null) {
				if (passangers <= 0) {
					if (ships.get(i).passengers <= -20)
						grid[shipX][shipY] = "Damaged";
					else
						grid[shipX][shipY] = "BlackBox(" + passangers + ")";
				} else {
					grid[shipX][shipY] = "Ship(" + passangers + ")";
				}

			} else {
				if (passangers <= 0) {
					if (ships.get(i).passengers <= -20)
						grid[shipX][shipY] = grid[shipX][shipY] + ",Damaged";
					else {
						grid[shipX][shipY] = grid[shipX][shipY] + ",BlackBox(" + passangers + ")";

					}
				} else
					grid[shipX][shipY] = grid[shipX][shipY] + ",Ship(" + passangers + ")";
			}
		}
		return grid;
	}

	public void updateGrid() {
		// create Grid
		String[][] grid = new String[mGrid][nGrid];
		grid[this.cgX][this.cgY] = "CG";

		for (int i = 0; i < stations.size(); i++) {
			int stationX = stations.get(i).stationX;
			int stationY = stations.get(i).stationY;
			if (grid[stationX][stationY] == null)
				grid[stationX][stationY] = "Station";
			else
				grid[stationX][stationY] = grid[stationX][stationY] + ",Station";
		}

		for (int i = 0; i < ships.size(); i++) {
			int shipX = ships.get(i).shipX;
			int shipY = ships.get(i).shipY;
			int passangers = ships.get(i).passengers;
			if (grid[shipX][shipY] == null) {
				if (passangers <= 0) {
					if (ships.get(i).passengers <= -20)
						grid[shipX][shipY] = "Damaged";
					else
						grid[shipX][shipY] = "BlackBox(" + passangers + ")";
				} else {
					grid[shipX][shipY] = "Ship(" + passangers + ")";
				}

			} else {
				if (passangers <= 0) {
					if (ships.get(i).passengers <= -20)
						grid[shipX][shipY] = grid[shipX][shipY] + ",Damaged";
					else
						grid[shipX][shipY] = grid[shipX][shipY] + ",BlackBox(" + passangers + ")";
				} else
					grid[shipX][shipY] = grid[shipX][shipY] + ",Ship(" + passangers + ")";
			}
		}
		this.grid = grid;
	}

	public ArrayList<String> getAllPossibleActions() {
		ArrayList<String> res = new ArrayList<>();
		// retrieve
		for (Ship ship : this.ships) {
			if (ship.passengers <= 0 && ship.passengers > -20 && ship.shipX == this.cgX && ship.shipY == this.cgY) {
				res.add("retrieve");
			}
		}
		// pickup
		for (Ship ship : this.ships) {

			if (ship.shipX == this.cgX && ship.shipY == this.cgY && this.cgMaxCapacity > 0 && ship.passengers > 0) {
				res.add("pickup");
			}
		}

		// drop
		if (this.grid[this.cgX][this.cgY].contains("Station") && this.passengersCarried > 0) {
			res.add("drop");
		}
		if (this.cgX == 0 && this.cgY == 0) {
			res.add("down");
			res.add("right");
		} else if (this.cgX == mGrid - 1 && this.cgY == nGrid - 1) {
			res.add("up");
			res.add("left");
		} else if (this.cgX == mGrid - 1 && this.cgY == 0) {
			res.add("up");
			res.add("right");
		} else if (this.cgX == 0 && this.cgY == nGrid - 1) {
			res.add("down");
			res.add("left");
		} else if (this.cgX == 0) {
			res.add("down");
			res.add("right");
			res.add("left");
		} else if (this.cgY == 0) {
			res.add("up");
			res.add("down");
			res.add("right");
		} else if (this.cgX == mGrid - 1) {
			res.add("up");
			res.add("right");
			res.add("left");
		} else if (this.cgY == nGrid - 1) {
			res.add("up");
			res.add("down");
			res.add("left");
		} else {
			res.add("up");
			res.add("down");
			res.add("right");
			res.add("left");
		}

		if (this.actions.size() > 0) {
			if (this.actions.get(this.actions.size() - 1) == "up" && this.actions.contains("down"))
				res.remove("down");
			if (this.actions.get(this.actions.size() - 1) == "down" && this.actions.contains("up"))
				res.remove("up");
			if (this.actions.get(this.actions.size() - 1) == "right" && this.actions.contains("left"))
				res.remove("left");
			if (this.actions.get(this.actions.size() - 1) == "left" && this.actions.contains("right"))
				res.remove("right");
		}
		return res;
	}

	public void pickUp() {
		for (Ship ship : ships) {

			if (ship.shipX == this.cgX && ship.shipY == this.cgY) {
				if (this.cgMaxCapacity > 0 && ship.passengers <= this.cgMaxCapacity) {
					this.cgMaxCapacity -= ship.passengers;
					this.passengersCarried += ship.passengers;
					ship.passengers = 0;

				}

				else if (ship.passengers >= this.cgMaxCapacity) {
					passengersCarried += this.cgMaxCapacity;
					ship.passengers -= this.cgMaxCapacity;
					this.cgMaxCapacity = 0;

				}
			}
		}
	}

	public void drop() {
		if (this.grid[this.cgX][this.cgY].contains("Station")) {
			cgMaxCapacity += passengersCarried;
			this.passengersCarried = 0;
		}
	}

	public void retrieve() {
		for (Ship ship : this.ships) {
			if (ship.passengers > -20 && ship.passengers <= 0 && ship.shipX == this.cgX && ship.shipY == this.cgY) {
				// Add to cg box count

				ship.passengers = -20;
				this.blackBoxRetrived++;
			}
		}
	}

	public void damage() {
		for (Ship ship : this.ships) {
			if (ship.passengers <= 0 && ship.passengers > -20) {
				ship.passengers -= 1;

			} else if (ship.passengers > 0) {
				ship.passengers -= 1;
				this.deaths += 1;
				if (ship.passengers == 0)
					ship.passengers = -1;

			}
		}

	}

	public void moveLeft() {
		this.cgY -= 1;
	}

	public void moveRight() {
		this.cgY += 1;
	}

	public void moveUp() {
		this.cgX -= 1;
	}

	public void moveDown() {
		this.cgX += 1;
	}

	public TreeNode makeChild() {
		TreeNode child = new TreeNode(this, new ArrayList<>(this.actions), this.pathCost, this.deaths,
				this.blackBoxRetrived, this.passengersCarried, this.gridString);
		return child;
	}

	public void printGrid() {
		for (int row = 0; row < this.grid.length; row++)// Cycles through rows
		{
			for (int col = 0; col < this.grid[row].length; col++)// Cycles through columns
			{
				System.out.print(" " + this.grid[row][col] + " ");
			}
			System.out.println(); // Makes a new row
		}
		System.out.println();
	}

	// public static void main(String[] args) {
	// // stations.add(new Station(2, 2));

	// TreeNode g = new TreeNode(null, new ArrayList<>(), 0, 0, 0, 0,
	// "3,4;32;3,2;0,1;3,2,12;");

	// // System.out.println("Ships");
	// // for (Ship s : g.ships) {
	// // System.out.println(s.shipX + " " + s.shipY);
	// // }
	// // System.out.println("Stations");
	// // for (Station s : g.stations) {
	// // System.out.println(s.stationX + " " + s.stationY);
	// // }

	// }
}
