package core.path;

import java.util.ArrayList;

public class PathFind {
    // GamePanel gp;
    // Node[][] node;
    // ArrayList<Node> openList = new ArrayList<Node>();
    // public ArrayList<Node> PathList = new ArrayList<Node>();
    // Node startNode, goalNode, currentNode;
    // boolean goalreached = false;
    // int step = 0;

    // public PathFinding(GamePanel gp) {
    //     this.gp = gp;
    //     intializeNode();
    // }

    // public void intializeNode() {
    //     node = new Node[gp.maxWorldCol][gp.maxWorldRow];

    //     int col = 0;
    //     int row = 0;

    //     while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
    //         node[col][row] = new Node(col, row);

    //         col++;
    //         if (col == gp.maxWorldCol) {
    //             col = 0;
    //             row++;
    //         }

    //     }
    // }

    // public void reset() {
    //     int col = 0;
    //     int row = 0;

    //     while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

    //         node[col][row].open = false;
    //         node[col][row].checked = false;
    //         node[col][row].solid = false;

    //         col++;

    //         if (col == gp.maxWorldCol) {
    //             col = 0;
    //             row++;
    //         }

    //     }

    //     openList.clear();
    //     PathList.clear();
    //     goalreached = false;
    //     step = 0;
    // }

    // public void setNode(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
    //     reset();

    //     startNode = node[startCol][startRow];
    //     currentNode = startNode;
    //     goalNode = node[goalCol][goalRow];
    //     openList.add(startNode);

    //     int col = 0;
    //     int row = 0;
    //     while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
    //         int tileNum = gp.tm.mapTileNum[col][row];
    //         if (gp.tm.tile[tileNum].collision == true) {
    //             node[col][row].solid = true;
    //         }

    //         getCost(node[col][row]);
    //         col++;
    //         if (col == gp.maxWorldCol) {
    //             col = 0;
    //             row++;
    //         }

    //     }
    // }

    // public void getCost(Node node) {

    //     // G cosy
    //     int xDistance = Math.abs(node.col - goalNode.col);
    //     int yDistance = Math.abs(node.row - goalNode.row);
    //     node.gCost = xDistance + yDistance;

    //     // H cost
    //     xDistance = Math.abs(node.col - startNode.col);
    //     yDistance = Math.abs(node.row - startNode.row);
    //     node.hCost = xDistance + yDistance;

    //     // F cost
    //     node.fCost = node.gCost + node.hCost;

    // }

    // public boolean search() {
    //     while (goalreached == false && step < 500) {
    //         int col = currentNode.col;
    //         int row = currentNode.row;

    //         // check current node
    //         currentNode.checked = true;
    //         openList.remove(currentNode);

    //         // check top node
    //         if (row - 1 >= 0) {
    //             openNode(node[col][row - 1]);
    //         }

    //         // check left node
    //         if (col - 1 >= 0) {
    //             openNode(node[col - 1][row]);
    //         }

    //         // check bottom node
    //         if (row + 1 < gp.maxWorldRow) {
    //             openNode(node[col][row + 1]);
    //         }

    //         // check right node
    //         if (col + 1 < gp.maxWorldCol) {
    //             openNode(node[col + 1][row]);
    //         }

    //         // Find BEst Node
    //         int bestNodeIndex = 0;
    //         int bestNodeCost = 9999;
    //         for (int i = 0; i < openList.size(); i++) {
    //             if (openList.get(i).fCost < bestNodeCost) {
    //                 bestNodeIndex = i;
    //                 bestNodeCost = openList.get(i).fCost;

    //             } else if (openList.get(i).fCost == bestNodeCost) {
    //                 if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
    //                     bestNodeIndex = i;

    //                 }
    //             }
    //         }

    //         if (openList.size() == 0) {
    //             break;
    //         }

    //         currentNode = openList.get(bestNodeIndex);

    //         if (currentNode == goalNode) {
    //             goalreached = true;
    //             TrackthePath();
    //         }
    //         step++;

    //     }
    //     return goalreached;
    // }

    // public void openNode(Node node) {
    //     if (node.solid == false && node.checked == false && node.open == false) {
    //         node.open = true;
    //         node.parent = currentNode;
    //         openList.add(node);
    //     }
    // }

    // public void TrackthePath() {
    //     Node node = goalNode;
    //     while (node != startNode) {
    //         PathList.add(0, node);
    //         node = node.parent;
    //     }
    // }
}
