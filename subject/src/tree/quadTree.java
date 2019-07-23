package tree;

// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    public Node() {
    }

    public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
        val = _val;
        isLeaf = _isLeaf;
        topLeft = _topLeft;
        topRight = _topRight;
        bottomLeft = _bottomLeft;
        bottomRight = _bottomRight;
    }
};

class QuadTree {
    public Node construct(int[][] grid) {
        int n = grid.length;
        return quadTreeBulid(grid, 0, n - 1, 0, n - 1);
    }

    public Node quadTreeBulid(int[][] grid, int col_s, int col_e, int row_s, int row_e) {
        // int n = grid.length;

        boolean topLeft = true;
        boolean tl_val = true;
        boolean topRight = true;
        boolean tr_val = true;
        boolean bottomLeft = true;
        boolean bl_val = true;
        boolean bottomRight = true;
        boolean br_val = true;

        boolean isLeaf = true;

        int tl = grid[row_s][col_s];
        int tr = grid[row_s][col_e];
        int bl = grid[row_e][col_s];
        int br = grid[row_e][col_e];

        //当只有一个格子时，直接返回
        if (col_e == col_s) {
            if (tl == 0) {
                tl_val = false;
            }
            return new Node(tl_val, true, null, null, null, null);
        }

        int tmp = tl + tr + bl + br;
        //全为0或1才会满足是叶子结点
        if (tmp != 0 && tmp != 4) {
            isLeaf = false;
        }
        int mid_row = (row_s + row_e) / 2;
        int mid_col = (col_e + col_s) / 2;

        //topLeft叶子节点判断
        for (int i = row_s; i <= mid_row; i++) {
            for (int j = col_s; j <= mid_col; j++) {
                if (grid[i][j] != tl) {
                    topLeft = false;
                }
            }
        }
        if (topLeft == true) {
            if (tl == 0) {
                tl_val = false;
            }
        } else {
            isLeaf = false;
        }

        //topRight叶子节点判断
        for (int i = row_s; i <= mid_row; i++) {
            for (int j = mid_col + 1; j <= col_e; j++) {
                if (grid[i][j] != tr) {
                    topRight = false;
                }
            }
        }
        if (topRight == true) {
            if (tr == 0) {
                tr_val = false;
            }
        } else {
            isLeaf = false;
        }

        //bottomLeft叶子节点判断
        for (int i = mid_row + 1; i <= row_e; i++) {
            for (int j = col_s; j <= mid_col; j++) {
                if (grid[i][j] != bl) {
                    bottomLeft = false;
                }
            }
        }
        if (bottomLeft == true) {
            if (bl == 0) {
                bl_val = false;
            }
        } else {
            isLeaf = false;
        }
        //bottomRight叶子节点判断
        for (int i = mid_row + 1; i <= row_e; i++) {
            for (int j = mid_col + 1; j <= col_e; j++) {
                if (grid[i][j] != br) {
                    bottomRight = false;
                }
            }
        }
        if (bottomRight == true) {
            if (br == 0) {
                br_val = false;
            }
        } else {
            isLeaf = false;
        }
        //判断整个是否为叶子节点
        if (isLeaf) {
            Node root;
            if (tmp == 0) {
                root = new Node(false, true, null, null, null, null);
            } else {
                root = new Node(true, true, null, null, null, null);
            }
            return root;
        } else {
            Node root = new Node(true, false, null, null, null, null);
            root.topLeft = quadTreeBulid(grid, col_s, mid_col, row_s, mid_row);
            root.topRight = quadTreeBulid(grid, mid_col + 1, col_e, row_s, mid_row);
            root.bottomLeft = quadTreeBulid(grid, col_s, mid_col, mid_row + 1, row_e);
            root.bottomRight = quadTreeBulid(grid, mid_col + 1, col_e, mid_row + 1, row_e);
            //  if(topLeft){
            //     Node topL = new Node(tl_val,true,null,null,null,null);
            //     root.topLeft = topL;
            //  }else{
            //     root.topLeft = quadTreeBulid(grid,col_s,mid_col,row_s,mid_row);
            //  }
            //  if(topRight){
            //     Node topR = new Node(tr_val,true,null,null,null,null);
            //     root.topRight = topR;
            //  }else{
            //     root.topRight = quadTreeBulid(grid,mid_col+1,col_e,row_s,mid_row);
            //  }
            //  if(bottomLeft){
            //     Node bottomL = new Node(bl_val,true,null,null,null,null);
            //     root.bottomLeft = bottomL;
            //  }else{
            //     root.bottomLeft = quadTreeBulid(grid,col_s,mid_col,mid_row+1,row_e);
            //  }
            //  if(bottomRight){
            //      Node bottomR = new Node(br_val,true,null,null,null,null);
            //      root.bottomRight = bottomR;
            //  }else{
            //     root.bottomRight = quadTreeBulid(grid,mid_col+1,col_e,mid_row+1,row_e);
            //  }
            return root;
        }
    }

}

//better solution
class Solution {
    public Node construct(int[][] grid) {
        return four(grid, 0, 0, grid.length);
    }

    public Node four(int[][] grid, int x, int y, int length) {
        Node node = new Node();
        int target = grid[x][y];
        for (int i = x; i < x + length; i++) {
            for (int j = y; j < y + length; j++) {
                if (grid[i][j] != target) {
                    node.isLeaf = false;
                    node.val = false;
                    node.topLeft = four(grid, x, y, length / 2);
                    node.topRight = four(grid, x, y + length / 2, length / 2);
                    node.bottomLeft = four(grid, x + length / 2, y, length / 2);
                    node.bottomRight = four(grid, x + length / 2, y + length / 2, length / 2);
                    return node;
                }
            }
        }
        node.isLeaf = true;
        node.val = target == 1 ? true : false;
        return node;
    }
}

class test {
    public static void main(String[] args) {
        QuadTree a = new QuadTree();
        int[][] matrix = {{1, 1}, {1, 1}};
        System.out.println(a.construct(matrix));
    }
}

