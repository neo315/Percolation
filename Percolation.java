public class Percolation
{   
    private WeightedQuickUnionUF uf;
    private boolean[] op;
    private int column;  // Total column number
    private int row;  //Total row number
    
    public Percolation(int M, int N)
    {
        if (!(M > 0) ||  !(N > 0))
            throw new IllegalArgumentException("n must be positive");
        column = N;
        row = M;
        
        uf = new WeightedQuickUnionUF(column * row + 2);
        op = new boolean[column * row + 2];
       
        for (int i = 0; i < column * row + 2; i++)
            op[i] = false;
            
        for (int i = 0; i < column; i++)
        {
            uf.union(0, i + 1);
            uf.union(column * row  + 1, column * row - i);
        }
        
    }
    
    public Percolation(int N)
    {
        this(N, N);
    }
    
    public void open(int i, int j)
    {
        if (!(i > 0) || !(j > 0) || (i > row) || (j > column)) 
            throw new IndexOutOfBoundsException("The range show be inside " + row + " x " + column);
        
        if (!isOpen(i, j))
        {
            op[(i - 1) * column + j] = true;
        if ((((i - 1) * column + j) % column != 1) && isOpen(i, j - 1)) 
            uf.union((i - 1) * column + j - 1, (i - 1) * column + j);
        if ((((i - 1) * column + j) % column != 0) && isOpen(i, j + 1)) 
            uf.union((i - 1) * column + j, (i - 1) * column + j + 1);
        if ((i != 1) && isOpen(i - 1, j)) 
            uf.union((i - 1) * column + j, (i - 1) * column + j - column);
        if ((i != row) && isOpen(i + 1, j)) 
            uf.union((i - 1) * column + j, (i - 1) * column + j + column);
        }
    }
    
    public boolean isOpen(int i, int j)
    {
        if (!(i > 0) || !(j > 0) || (i > row) || (j > column)) 
            throw new IndexOutOfBoundsException("The range show be inside " + row + " x " + column);
        
        return op[(i - 1) * column + j];
    }
    
    public boolean isFull(int i, int j)
    {
        if (!(i > 0) || !(j > 0) || (i > row) || (j > column))
            throw new IndexOutOfBoundsException("The range show be inside " + row + " x " + column);
        return (uf.connected((i - 1) * column + j, 0) 
                    && isOpen(i, j));
    }
    
    public boolean percolates()
    {
        if (N == 1) return isOpen(1, 1);
        else return uf.connected(0, N * N + 1);
    }
    
    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        Percolation per = new Percolation(N);
        StdOut.println("Using a " + N +" by " + N + " grids.");
        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            per.open(p, q);
            if (per.percolates()) StdOut.println("The system percolates!");
        else StdOut.println("The system doesn't percolates");
        }
    }
}