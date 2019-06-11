/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import util.kmeanNode;
import java.util.ArrayList;
import java.util.List;
import util.distancy;

/**
 *
 * @author mnm
 */
public class kmeans {

        Double[][] centers_temp = new Double[4][2];
        Double[] tafazol = new Double[4];
        Double[] faseleh = new Double[4];
        public List<distancy> list;
        public List<kmeanNode> nlist;
        public Double[][] centers = new Double[4][2];
        public List<List<kmeanNode>> clusters = new ArrayList<>();
        public Double xv, nv, xp, np, epsilon = 0.00001;
        ;
        Double[] tafazol_temp = new Double[4];
        Double fasele_temp = 0.0;

        public Double[][] run(List<List<String>> res) {
                init(res);
                centers = new Double[][]{{0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}};
                for (int c = 0; c < 4; c++) {
                        for (int i = 0; i < clusters.get(c).size(); i++) {
                                centers[c][0] += clusters.get(c).get(i).v / clusters.get(c).size();
                                centers[c][1] += clusters.get(c).get(i).faz / clusters.get(c).size();
                        }
                }
                start();
                return centers;
        }
        public void init(List<List<String>> data) {
                xv = 0.0;
                nv = 1.0;
                xp = -180.0;
                np = 180.0;
                for (int i = 0; i < 4; i++) {
                        List<kmeanNode> t = new ArrayList<>();
                        clusters.add(t);
                }
                int numberOfValues = 0;
                int numberofMeasurements = 0;
                list = new ArrayList<distancy>();

                for (int i = 0; i < data.size(); i++) {
                        if (Double.parseDouble(data.get(i).get(2))
                                != Double.parseDouble(data.get(i + 1).get(2))) {
                                numberOfValues = i + 1;
                                break;
                        }
                }

                for (int i = 0; i < data.size(); i++) {
                        if (Double.parseDouble(data.get(i).get(2)) > numberofMeasurements) {
                                Double t = Double.parseDouble(data.get(i).get(2));
                                numberofMeasurements = t.intValue();
                        }
                }
                int value = 0;
                for (int i = 0; i <= numberofMeasurements - 1; i++) {
                        Double param[] = new Double[numberOfValues];
                        String names[] = new String[numberOfValues];
                        String sub[] = new String[numberOfValues];
                        Double volt[] = new Double[(numberOfValues + 1) / 2];
                        Double phases[] = new Double[(numberOfValues + 1) / 2];
                        int bus = 0;
                        Double time = Double.parseDouble(data.get(value).get(2));
                        for (int j = 0; j < numberOfValues; j++) {
                                param[j] = Double.parseDouble(data.get(value).get(3));
                                names[j] = (data.get(value).get(1));
                                sub[j] = data.get(value).get(4);
                                value++;
                                if (j > 0 && sub[j].equals(sub[j - 1])) {
                                        volt[bus] = param[j - 1];
                                        phases[bus] = param[j];
                                        bus++;
                                }
                                if (j + 1 == numberOfValues) {
                                        distancy mes = new distancy();
                                        mes.setData(time, volt, phases, names);
                                        list.add(mes);
                                }
                        }
                }

                nlist = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).vr < nv) {
                                nv = list.get(i).vr;
                        }
                        if (list.get(i).vr > xv) {
                                xv = list.get(i).vr;
                        }

                        if (list.get(i).pavg < np) {
                                np = list.get(i).pavg;
                        }

                        if (list.get(i).pavg > xp) {
                                xp = list.get(i).pavg;
                        }
                }
                for (int i = 0; i < list.size(); i++) {
                        Double normPhase = (list.get(i).pavg - np) / (xp - np);
                        Double normVolt = (list.get(i).vr - nv) / (xv - nv);
                        kmeanNode meas = new kmeanNode();
                        meas.faz = normPhase;
                        meas.v = normVolt;
                        meas.zaman = list.get(i).t;
                        nlist.add(meas);
                }
                centers[0][0] = 0.5;
                centers[0][1] = 0.5;
                for (int i = 0; i < nlist.size(); i++) {
                        Double newdistance = Math.sqrt(((centers[0][0] - nlist.get(i).v) * (centers[0][0] - nlist.get(i).v))
                                + ((centers[0][1] - nlist.get(i).faz) * (centers[0][1] - nlist.get(i).faz)));
                        if (newdistance > fasele_temp) {
                                fasele_temp = newdistance;
                                centers[1][0] = nlist.get(i).v;
                                centers[1][1] = nlist.get(i).faz;
                        }
                }
                fasele_temp = 0.0;
                for (int i = 0; i < nlist.size(); i++) {
                        Double newdistance0 = Math.sqrt(((centers[0][0] - nlist.get(i).v) * (centers[0][0] - nlist.get(i).v))
                                + ((centers[0][1] - nlist.get(i).faz) * (centers[0][1] - nlist.get(i).faz)));
                        Double newdistance1 = Math.sqrt(((centers[1][0] - nlist.get(i).v) * (centers[1][0] - nlist.get(i).v))
                                + ((centers[1][1] - nlist.get(i).faz) * (centers[1][1] - nlist.get(i).faz)));
                        Double newdistance = (newdistance0 + newdistance1) / 2;
                        if (newdistance > fasele_temp) {
                                fasele_temp = newdistance;
                                centers[2][0] = nlist.get(i).v;
                                centers[2][1] = nlist.get(i).faz;
                        }
                }
                fasele_temp = 0.0;
                for (int i = 0; i < nlist.size(); i++) {

                        Double temp1 = Math.sqrt(((centers[0][0] - nlist.get(i).v) * (centers[0][0] - nlist.get(i).v))
                                + ((centers[0][1] - nlist.get(i).faz) * (centers[0][1] - nlist.get(i).faz)));
                        Double temp2 = Math.sqrt(((centers[1][0] - nlist.get(i).v) * (centers[1][0] - nlist.get(i).v))
                                + ((centers[1][1] - nlist.get(i).faz) * (centers[1][1] - nlist.get(i).faz)));
                        Double temp3 = Math.sqrt(((centers[2][0] - nlist.get(i).v) * (centers[2][0] - nlist.get(i).v))
                                + ((centers[2][1] - nlist.get(i).faz) * (centers[2][1] - nlist.get(i).faz)));
                        Double temp = (temp1 + temp2 + temp3) / 3;

                        if (temp > fasele_temp) {
                                fasele_temp = temp;
                                centers[3][0] = nlist.get(i).v;
                                centers[3][1] = nlist.get(i).faz;
                        }
                }
                for (int i = 0; i < nlist.size(); i++) {
                        for (int j = 0; j < 4; j++) {
                                tafazol_temp[j] = Math.sqrt(((centers[j][0] - nlist.get(i).v) * (centers[j][0] - nlist.get(i).v))
                                        + ((centers[j][1] - nlist.get(i).faz) * (centers[j][1] - nlist.get(i).faz)));
                        }
                        if (tafazol_temp[0] < tafazol_temp[1]) {
                                if (tafazol_temp[0] < tafazol_temp[2]) {
                                        if (tafazol_temp[0] < tafazol_temp[3]) {
                                                clusters.get(0).add(nlist.get(i));
                                                continue;
                                        } else {
                                                clusters.get(3).add(nlist.get(i));
                                                continue;
                                        }
                                } else if (tafazol_temp[2] < tafazol_temp[3]) {
                                        clusters.get(2).add(nlist.get(i));
                                        continue;
                                } else {
                                        clusters.get(3).add(nlist.get(i));
                                        continue;
                                }
                        } else if (tafazol_temp[1] < tafazol_temp[2]) {
                                if (tafazol_temp[1] < tafazol_temp[3]) {
                                        clusters.get(1).add(nlist.get(i));
                                        continue;
                                } else {
                                        clusters.get(3).add(nlist.get(i));
                                        continue;
                                }
                        } else if (tafazol_temp[2] < tafazol_temp[3]) {
                                clusters.get(2).add(nlist.get(i));
                                continue;
                        } else {
                                clusters.get(3).add(nlist.get(i));
                                continue;
                        }
                }

        }
        public void start() {
                while (true) {
                        for (int i = 0; i < 4; i++) {
                                List<kmeanNode> t = new ArrayList<>();
                                clusters.add(t);
                        }
                        for (int i = 0; i < nlist.size(); i++) {
                                System.out.println(nlist.size());
                                for (int j = 0; j < 4; j++) {
                                        faseleh[j] = Math.sqrt(((centers[j][0] - nlist.get(i).v) * (centers[j][0] - nlist.get(i).v))
                                                + ((centers[j][1] - nlist.get(i).faz) * (centers[j][1] - nlist.get(i).faz)));
                                }
                                if (faseleh[0] < faseleh[1]) {
                                        if (faseleh[0] < faseleh[2]) {
                                                if (faseleh[0] < faseleh[3]) {
                                                        clusters.get(0).add(nlist.get(i));
                                                        continue;
                                                } else {
                                                        clusters.get(3).add(nlist.get(i));
                                                        continue;
                                                }
                                        } else if (faseleh[2] < faseleh[3]) {
                                                clusters.get(2).add(nlist.get(i));
                                                continue;
                                        } else {
                                                clusters.get(3).add(nlist.get(i));
                                                continue;
                                        }
                                } else if (faseleh[1] < faseleh[2]) {
                                        if (faseleh[1] < faseleh[3]) {
                                                System.out.println(clusters.get(1).size());
                                                System.out.println(nlist.size());
                                                clusters.get(1).add(nlist.get(i));
                                                continue;
                                        } else {
                                                clusters.get(3).add(nlist.get(i));
                                                continue;
                                        }
                                } else if (faseleh[2] < faseleh[3]) {
                                        clusters.get(2).add(nlist.get(i));
                                        continue;
                                } else {
                                        clusters.get(3).add(nlist.get(i));
                                        continue;
                                }
                        }
                        centers_temp = centers;
                        centers = new Double[][]{{0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}};
                        for (int c = 0; c < 4; c++) {
                                for (int i = 0; i < clusters.get(c).size(); i++) {
                                        centers[c][0] += clusters.get(c).get(i).v / clusters.get(c).size();
                                        centers[c][1] += clusters.get(c).get(i).faz / clusters.get(c).size();
                                }
                        }
                        for (int j = 0; j < 4; j++) {
                                tafazol[j] = Math.sqrt(((centers[j][0] - centers_temp[j][0]) * (centers[j][0] - centers_temp[j][0]))
                                        + ((centers[j][1] - centers_temp[j][1]) * (centers[j][1] - centers_temp[j][1])));
                        }
                        if (tafazol[0] <= epsilon && tafazol[1] <= epsilon && tafazol[2] <= epsilon && tafazol[3] <= epsilon) {
                                break;
                        }
                }
                System.out.println("Cluster #1  " + clusters.get(0).size());
                System.out.println("Cluster #2  " + clusters.get(1).size());
                System.out.println("Cluster #3  " + clusters.get(2).size());
                System.out.println("Cluster #4  " + clusters.get(3).size());
                centers = new Double[][]{{0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}};
                for (int i = 0; i < 4; i++) {
                        centers[i][0] = centers[i][0] * (xv - nv) + nv;
                        centers[i][1] = centers[i][1] * (xp - np) + np;
                }
                for (int i = 0; i < 4; i++) {
                        System.out.println("center #" + i + " " + centers[i][0] + ", " + centers[i][1]);
                }
                for (int j = 0; j < list.size(); j++) {
                        for (int c = 0; c < 4; c++) {
                                for (int i = 0; i < clusters.get(c).size(); i++) {
                                        if (list.get(j).t == clusters.get(c).get(i).zaman) {
                                                list.get(j).c = c;
                                                break;
                                        }
                                }
                        }

                }

        }

        

}
