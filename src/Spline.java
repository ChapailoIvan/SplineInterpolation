public class Spline {
    static double L = -2.0,
                  R = 2.0;
    static int N = 10;
    static double H;
    static double error = 0.0;

    static double[] NODES = new double[N + 1];
    static double[] VALUES = new double[N + 1];
    static double[] MOMENTS = new double[N + 1];

    static double[] a = new double[N + 1];
    static double[] b = new double[N + 1];
    static double[] c = new double[N + 1];
    static double[] f = new double[N + 1];

    // additional terms
    static double dFUNCL = 2 * L * Math.sin(2 * L) + 2 * Math.pow(L, 2) * Math.cos(2 * L);
    static double dFUNCR = 2 * R * Math.sin(2 * R) + 2 * Math.pow(R, 2) * Math.cos(2 * R);

    public static double getFuncValue(double x) {
        return Math.pow(x, 2) * Math.sin(2 * x);
    }

    public static void calculateValues() {
        H = (R - L) / N;
        for (int i = 0; i <= N; ++i) {
            NODES[i] = L + i * H;
            VALUES[i] = getFuncValue(NODES[i]);
        }
    }

    public static void calculateSplineCoeff() {
        c[0] = (NODES[1] - NODES[0]) / 3;
        b[0] = (NODES[1] - NODES[0]) / 6;
        f[0] = (VALUES[1] - VALUES[0]) / (NODES[1] - NODES[0]) - dFUNCL;

        for (int n = 1; n <= N - 1; ++n) {
            a[n] = (NODES[n] - NODES[n - 1]) / 6;
            c[n] = (NODES[n + 1] - NODES[n - 1]) / 3;
            b[n] = (NODES[n + 1] - NODES[n]) / 6;
            f[n] = (VALUES[n + 1] - VALUES[n]) / (NODES[n + 1] - NODES[n]) - (VALUES[n] - VALUES[n - 1]) / (NODES[n] - NODES[n - 1]);
        }

        a[N] = (NODES[N] - NODES[N - 1]) / 6;
        c[N] = (NODES[N] - NODES[N - 1]) / 3;
        f[N] = dFUNCR - (VALUES[N] - VALUES[N - 1]) / (NODES[N] - NODES[N - 1]);
    }

    public static double getSplineValue(double p) {
        int l = (int) Math.floor((p - L) / H) == N ? N - 1 : (int) Math.floor((p - L) / H);
        int r = l + 1;

        return MOMENTS[l] * Math.pow(NODES[r] - p, 3) / (6 * (NODES[r] - NODES[l])) + MOMENTS[r] * Math.pow(p - NODES[l], 3) / (6 * (NODES[r] - NODES[l])) +
                (VALUES[l] - Math.pow(NODES[r] - NODES[l], 2) * MOMENTS[l] / 6) * (NODES[r] - p) / (NODES[r] - NODES[l]) +
                (VALUES[r] - Math.pow(NODES[r] - NODES[l], 2) * MOMENTS[r] / 6 ) * (p - NODES[l]) / (NODES[r] - NODES[l]);
    }

    public static void buildSpline() {
        TridiagonalMatrixAlgorithm.solve(a, b, c, MOMENTS, f);
    }

    public static void getError() {
        double h = (Spline.R - Spline.L) / 100;
        for (int i = 0; i <= 100; ++i) {
             error = Math.max(error, Math.abs(getSplineValue(Spline.L + i * h) - getFuncValue(Spline.L + i * h)));
        }
    }
}

