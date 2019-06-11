/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import util.distancy;
import util.knnNode;

/**
 *
 * @author mnm
 */
public class knn {

        public List<distancy> run(List<List<String>> res1, List<List<String>> res2) {
                List<distancy> list = init(res1);
                List<distancy> test = init(res2);
                classification(list, test);
                return test;
        }

        public void classification(List<distancy> training, List<distancy> test) {
                int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
                for (distancy tVal : test) {
                        List<knnNode> res = new ArrayList<knnNode>();

                        for (distancy d : training) {
                                double temp = 0.0;
                                temp = Math.pow((d.vr - tVal.vr), 2) + Math.pow((d.vr - tVal.vr), 2);
                                knnNode kn = new knnNode();
                                kn.value = Math.sqrt(temp);
                                kn.c = d.c;
                                res.add(kn);
                        }

                        Collections.sort(res, new Comparator<knnNode>() {
                                @Override
                                public int compare(knnNode a, knnNode b) {
                                        return a.value < b.value ? -1 : a.value == b.value ? 0 : 1;
                                }
                        });

                        for (int i = 0; i < 5; i++) {
                                if (res.get(i).c == 0) {
                                        c1++;
                                }
                                if (res.get(i).c == 1) {
                                        c2++;
                                }
                                if (res.get(i).c == 2) {
                                        c3++;
                                }
                                if (res.get(i).c == 3) {
                                        c4++;
                                }
                        }
                        if (c1 > c2) {if (c1 > c3) {if (c1 > c4) {
                                                tVal.c = 1;
                                        } else {
                                                tVal.c = 4;
                                        }} else if (c3 > c4) {
                                        tVal.c = 3;
                                } else {
                                        tVal.c = 4;
                                }} else {
                                if (c2 > c3) {if (c2 > c4) {
                                                tVal.c = 2;
                                        } else {
                                                tVal.c = 4;
                                        }} else {
                                        if (c3 > c4) {
                                                tVal.c = 3;
                                        } else {
                                                tVal.c = 4;
                                        }
                                }
                        }

                }

                for (int x = 0; x < test.size(); x++) {
                        System.out.println(test.get(x).vr + " " + test.get(x).pavg + " " + test.get(x).c);
                }
        }

        public List<distancy> init(List<List<String>> data) {
                int nov = 0;
                int nom = 0;
                List<distancy> res = new ArrayList<distancy>();
                for (int i = 0; i < data.size(); i++) {
                        if (Double.parseDouble(data.get(i).get(2))
                                != Double.parseDouble(data.get(i + 1).get(2))) {
                                nov = i + 1;
                                break;
                        }
                }

                for (int i = 0; i < data.size(); i++) {
                        if (Double.parseDouble(data.get(i).get(2)) > nom) {
                                Double t = Double.parseDouble(data.get(i).get(2));
                                nom = t.intValue();
                        }
                }
                int mizan_temo = 0;
                for (int i = 0; i <= nom - 1; i++) {

                        Double parameters[] = new Double[nov];
                        String title[] = new String[nov];
                        String subject[] = new String[nov];
                        Double mizan[] = new Double[(nov + 1) / 2];
                        Double faz[] = new Double[(nov + 1) / 2];
                        int bus = 0;

                        Double time = Double.parseDouble(data.get(mizan_temo).get(2));
                        for (int j = 0; j < nov; j++) {
                                parameters[j] = Double.parseDouble(data.get(mizan_temo).get(3));
                                title[j] = (data.get(mizan_temo).get(1));
                                subject[j] = data.get(mizan_temo).get(4);
                                mizan_temo++;
                                if (j > 0 && subject[j].equals(subject[j - 1])) {
                                        mizan[bus] = parameters[j - 1];
                                        faz[bus] = parameters[j];
                                        bus++;
                                }
                                if (j + 1 == nov) {
                                        distancy mes = new distancy();
                                        mes.setData(time, mizan, faz, title);
                                        res.add(mes);
                                }
                        }
                }

                return res;
        }
}
