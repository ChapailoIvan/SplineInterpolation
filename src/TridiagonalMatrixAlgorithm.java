public class TridiagonalMatrixAlgorithm {
    public static void solve(double[] a, double[] b, double[] c, double[] ay, double[] f) {
        int N = f.length - 1;
        double[] q = new double[N + 1];
        double[] d = new double[N + 2];

        q[1] = - b[0] / c[0]; d[1] = f[0] / c[0];

        for (int i = 1; i <= N - 1; i++) {
            double k = (c[i] + q[i] * a[i]);
            if (k != 0) {
                q[i + 1] = - b[i] / k;
                d[i + 1] = (f[i] - a[i] * d[i]) / k;
            }
        }
        d[N + 1] = (f[N] - a[N] * d[N]) / (c[N] + q[N] * a[N]);

        ay[N] = d[N + 1];
        for (int i = N - 1; i >= 0; i--) {
            ay[i] = q[i + 1] * ay[i + 1] + d[i + 1];
        }
    }
}
