package code;

import java.util.ArrayList;
import java.util.List;

public class DeprecatedTreeNode {
	DeprecatedTreeNode parent;
	TreeNode grid;
	List<String> plan = new ArrayList<String>();
	int pathCost;
	int deaths;
	int blackBoxRetrived;
	int passengersCG;
	int cgX;
	int cgY;
	int nodesExpanded;

	public DeprecatedTreeNode(DeprecatedTreeNode parent, TreeNode grid, ArrayList<String> plan, int pathCost,
			int deaths, int blackBoxRetrived,
			int passengersCG, int cgX, int cgY, int nodesExpanded) {
		super();
		this.parent = parent;
		this.grid = grid;
		this.plan = plan;
		this.pathCost = pathCost;
		this.deaths = deaths;
		this.blackBoxRetrived = blackBoxRetrived;
		this.passengersCG = passengersCG;
		this.nodesExpanded = nodesExpanded;
	}

}
