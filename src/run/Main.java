/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import clustering.kmeans;
import clustering.knn;
import com.github.plot.Plot;
import java.awt.Color;
import util.distancy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.io;
/**
 *
 * @author mnm
 */
public class Main {

        io i = new io();

        public void run() {
                List<List<String>> res1 = i.readfromfile(new File("measurements.csv"));
                List<List<String>> res2 = i.readfromfile(new File("analog_values.csv"));

                kmeans km = new kmeans();
                knn kn = new knn();

                Double center[][] = km.run(res1);
                List<distancy> test = kn.run(res1, res2);

                List<List<Double>> xarr = new ArrayList<>();
                List<List<Double>> yarr = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                        List<Double> t = new ArrayList<>();
                        List<Double> t1 = new ArrayList<>();
                        xarr.add(t);
                        yarr.add(t1);
                }
                for (distancy measurement : km.list) {
                        switch (measurement.c) {
                                case 0:
                                        xarr.get(0).add(measurement.pavg);
                                        yarr.get(0).add(measurement.vr);
                                        break;
                                case 1:
                                        xarr.get(1).add(measurement.pavg);
                                        yarr.get(1).add(measurement.vr);
                                        break;
                                case 2:
                                        xarr.get(2).add(measurement.pavg);
                                        yarr.get(2).add(measurement.vr);
                                        break;
                                case 3:
                                        xarr.get(3).add(measurement.pavg);
                                        yarr.get(3).add(measurement.vr);
                                        break;
                        }
                }

                for (distancy testValue : test) {
                        switch (testValue.c) {
                                case 1:
                                        xarr.get(4).add(testValue.pavg);
                                        yarr.get(4).add(testValue.vr);
                                        break;
                                case 2:
                                        xarr.get(5).add(testValue.pavg);
                                        yarr.get(5).add(testValue.vr);
                                        break;
                                case 3:
                                        xarr.get(6).add(testValue.pavg);
                                        yarr.get(6).add(testValue.vr);
                                        break;
                                case 4:
                                        xarr.get(7).add(testValue.pavg);
                                        yarr.get(7).add(testValue.vr);
                                        break;
                        }
                }

                xarr.get(8).add(center[0][1]);
                yarr.get(8).add(center[0][0]);
                xarr.get(9).add(center[1][1]);
                yarr.get(9).add(center[1][0]);
                xarr.get(10).add(center[2][1]);
                yarr.get(10).add(center[2][0]);
                xarr.get(11).add(center[3][1]);
                yarr.get(11).add(center[3][0]);
                Plot plot = Plot.plot(Plot.plotOpts().
                        title("clusters").
                        legend(Plot.LegendFormat.BOTTOM)).
                        xAxis("x", Plot.axisOpts().
                                range(-28, 26)).
                        yAxis("y", Plot.axisOpts().
                                range(.9, 1)).
                        series("Data1", Plot.data().
                                xy(xarr.get(0), yarr.get(0)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.GREEN).
                                        color(Color.BLACK)).
                        series("Data2", Plot.data().
                                xy(xarr.get(1), yarr.get(1)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.BLUE).
                                        color(Color.BLACK)).
                        series("Data3", Plot.data().
                                xy(xarr.get(2), yarr.get(2)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.RED).
                                        color(Color.BLACK)).
                        series("Data4", Plot.data().
                                xy(xarr.get(3), yarr.get(3)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.YELLOW).
                                        color(Color.BLACK)).
                        series("Data5", Plot.data().
                                xy(xarr.get(4), yarr.get(4)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.GRAY).
                                        color(Color.BLACK)).
                        series("Data6", Plot.data().
                                xy(xarr.get(5), yarr.get(5)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.MAGENTA).
                                        color(Color.BLACK)).
                        series("Data7", Plot.data().
                                xy(xarr.get(6), yarr.get(6)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.PINK).
                                        color(Color.BLACK)).
                        series("Data8", Plot.data().
                                xy(xarr.get(7), yarr.get(7)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.ORANGE).
                                        color(Color.BLACK)).
                        series("Data9", Plot.data().
                                xy(xarr.get(8), yarr.get(8)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.CYAN).
                                        color(Color.BLACK)).
                        series("Data10", Plot.data().
                                xy(xarr.get(9), yarr.get(9)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.LIGHT_GRAY).
                                        color(Color.BLACK)).
                        series("Data11", Plot.data().
                                xy(xarr.get(10), yarr.get(10)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.BLACK).
                                        color(Color.BLACK)).
                        series("Data12", Plot.data().
                                xy(xarr.get(11), yarr.get(11)),
                                Plot.seriesOpts().
                                        line(Plot.Line.NONE).
                                        marker(Plot.Marker.DIAMOND).
                                        markerColor(Color.DARK_GRAY).
                                        color(Color.BLACK));
                int c = 0;
//                System.out.println("asdasdad");
//                for (Double x : xarr.get(1)) {
//                        System.out.println(x + "\t" + yarr.get(1).get(c++));
//                }
                try {
                        plot.save("clusters", "png");
                } catch (Exception s) {
                }

        }

        public static void main(String[] args) {
                Main m = new Main();
                m.run();

        }

}
