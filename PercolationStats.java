public class PercolationStats
{
    private int N;
    private int T;
    private double[] thresholds;
    
    public PercolationStats(int n, int t)
    {
        if (!(n > 0) ||  !(t > 0))
            throw new IllegalArgumentException("N and T must be positive");
        N = n;
        T = t;
        thresholds = new double[T];
        Percolation[] per = new Percolation[T];
        
        int i;
        int j;
        
        for (int exp = 0; exp < T; exp++)
        {
           per[exp] = new Percolation(N);
           
           int opensite = 0;
           while (!per[exp].percolates())
           {
               i = StdRandom.uniform(1, N + 1);
               j = StdRandom.uniform(1, N + 1);
              
               if (!per[exp].isOpen(i, j))
              {
               per[exp].open(i, j);
               opensite++;
//             StdOut.println("Opening (" + i + ", " + j + ")" );
               thresholds[exp] = 1.0 * opensite / (N * N);
//             StdOut.println("Opensties# " + opensite + " and " + thresholds[exp]);
               }
           }
        }
    }
    
    public double mean()
    {
        return StdStats.mean(thresholds);
    }
    
    public double stddev()
    {
        return StdStats.stddev(thresholds);
    }
    
    public double confidenceLo()
    {
        return (mean() - 1.96 * stddev() / Math.sqrt(T));
    }
    
    public double confidenceHi()
    {
        return (mean() + 1.96 * stddev() / Math.sqrt(T));
    }
    
    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        double time = 0;
        Stopwatch timer = new Stopwatch();
        PercolationStats pers = new PercolationStats(N, T);
        time = timer.elapsedTime();
        StdOut.println("Time consumed: " + time);
        StdOut.println("mean = " + pers.mean());
        StdOut.println("stddev = " + pers.stddev());
        StdOut.println("95% confidence interval = " 
                           + pers.confidenceLo() + ", " + pers.confidenceHi());
    }
}   