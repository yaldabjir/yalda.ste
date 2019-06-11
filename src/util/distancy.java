package util;
/**
 *
 * @author mnm
 */
public class distancy {

        public Double[] v, p;
        public Double t, vr, pavg;
        public String[] n;
        public int c;

        public void setData(Double t, Double[] v, Double[] p, String[] n) {
                this.t = t;
                this.v = v;
                this.p = p;
                this.n = n;
                CompNumber lastv = new CompNumber(0, 0);

                for (int i = 0; i < v.length; i++) {

                        Double fazrad = Math.toRadians(p[i]);
                        Double real = v[i] * Math.cos(fazrad);
                        Double image = v[i] * Math.sin(fazrad);
                        CompNumber volte = new CompNumber(real, image);

                        lastv = lastv.plus(volte);

                        if (i + 1 == v.length) {
                                CompNumber temp = new CompNumber(v.length, 0);
                                lastv = lastv.divides(temp);
                        }
                }

                this.vr = Math.sqrt((lastv.real * lastv.real) + (lastv.imagin * lastv.imagin));
                lastv = new CompNumber(0, 0);

                for (int i = 0; i < v.length; i++) {

                        Double radPhase = Math.toRadians(p[i]);
                        Double realVolt = v[i] * Math.cos(radPhase);
                        Double imagVolt = v[i] * Math.sin(radPhase);
                        CompNumber voltBus = new CompNumber(realVolt, imagVolt);

                        lastv = lastv.plus(voltBus);

                        if (i + 1 == v.length) {
                                CompNumber buses = new CompNumber(v.length, 0);
                                lastv = lastv.divides(buses);
                        }
                }

                this.pavg = Math.toDegrees(Math.atan(lastv.imagin / lastv.real));

        }

        class CompNumber {
                public double real;
                public double imagin;
                public CompNumber(double r, double i) {
                        real = r;
                        imagin = i;
                }
                public CompNumber plus(CompNumber b) {
                        CompNumber a = this;
                        double r = a.real + b.real;
                        double i = a.imagin + b.imagin;
                        return new CompNumber(r, i);
                }
                public CompNumber times(CompNumber b) {
                        CompNumber a = this;
                        double r = a.real * b.real - a.imagin * b.imagin;
                        double i = a.real * b.imagin + a.imagin * b.real;
                        return new CompNumber(r, i);
                }
                public CompNumber reciprocal() {
                        double scale = real * real + imagin * imagin;
                        return new CompNumber(real / scale, -imagin / scale);
                }
                public CompNumber divides(CompNumber b) {
                        CompNumber a = this;
                        return a.times(b.reciprocal());
                }
                public CompNumber sin() {
                        return new CompNumber(Math.sin(real) * Math.cosh(imagin), Math.cos(real) * Math.sinh(imagin));
                }
                public CompNumber cos() {
                        return new CompNumber(Math.cos(real) * Math.cosh(imagin), -Math.sin(real) * Math.sinh(imagin));
                }
        }
}
