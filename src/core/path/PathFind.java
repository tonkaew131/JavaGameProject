package core.path;

import java.util.ArrayList;

import core.Map;
import core.Texture;

public class PathFind {
    private Map map;
    private Node[][] node;

    private int mapCol;
    private int mapRow;

    private ArrayList<Node> openList = new ArrayList<Node>();
    private ArrayList<Node> pathList = new ArrayList<Node>();

    private Node startNode, goalNode, currentNode;

    boolean goalReached = false;
    int step = 0;

    public PathFind(Map map) {
        this.map = map;
        intializeNode();
    }

    public void intializeNode() {
        this.mapCol = map.getMapWidth();
        this.mapRow = map.getMapHeight();

        node = new Node[mapRow][mapCol];

        int col = 0;
        int row = 0;

        while (col < mapCol && row < mapRow) {
            node[row][col] = new Node(col, row);

            col++;
            if (col == mapCol) {
                col = 0;
                row++;
            }
        }
    }

    public void reset() {
        int col = 0;
        int row = 0;

        while (col < mapCol && row < mapRow) {
            node[row][col].open = false;
            node[row][col].checked = false;
            node[row][col].solid = false;

            col++;

            if (col == mapCol) {
                col = 0;
                row++;
            }

        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow) {
        reset();

        startNode = node[startRow][startCol];
        currentNode = startNode;
        goalNode = node[goalRow][goalCol];
        openList.add(startNode);

        int col = 0;
        int row = 0;
        while (col < mapCol && row < mapRow) {
            if (map.getTexture(col, row) != Texture.EMPTY) {
                node[row][col].solid = true;
            }

            getCost(node[row][col]);
            col++;
            if (col == mapCol) {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node) {
        // G cosy
        int xDistance = Math.abs(node.col - goalNode.col);
        int yDistance = Math.abs(node.row - goalNode.row);
        node.gCost = xDistance + yDistance;

        // H cost
        xDistance = Math.abs(node.col - startNode.col);
        yDistance = Math.abs(node.row - startNode.row);
        node.hCost = xDistance + yDistance;

        // F cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (goalReached == false && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // check current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // check top node
            if (row - 1 >= 0) {
                openNode(node[row - 1][col]);
            }

            // check left node
            if (col - 1 >= 0) {
                openNode(node[row][col - 1]);
            }

            // check bottom node
            if (row + 1 < mapRow) {
                openNode(node[row + 1][col]);
            }

            // check right node
            if (col + 1 < mapCol) {
                openNode(node[row][col + 1]);
            }

            // Find BEst Node
            int bestNodeIndex = 0;
            int bestNodeCost = 9999;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodeCost) {
                    bestNodeIndex = i;
                    bestNodeCost = openList.get(i).fCost;

                } else if (openList.get(i).fCost == bestNodeCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.size() == 0) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackthePath();
            }
            step++;

        }
        return goalReached;
    }

    public void openNode(Node node) {
        if (node.solid == false && node.checked == false && node.open == false) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackthePath() {
        Node node = goalNode;
        while (node != startNode) {
            pathList.add(0, node);
            node = node.parent;
        }
    }

    public ArrayList<Node> getPathList() {
        return pathList;
    }
}
