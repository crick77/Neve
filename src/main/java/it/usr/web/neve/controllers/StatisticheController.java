/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controllers;

import it.usr.web.neve.model.Stat;
import it.usr.web.neve.services.StatisticheService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartOptions;
import org.primefaces.model.charts.polar.PolarAreaChartDataSet;
import org.primefaces.model.charts.polar.PolarAreaChartModel;
import org.primefaces.model.charts.polar.PolarAreaChartOptions;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class StatisticheController extends BaseController {
    @Inject
    StatisticheService ss;
    private PolarAreaChartModel comunePraticheChart;
    private HorizontalBarChartModel comuneTotaleChart;
    private PieChartModel esitoChart;
    private BarChartModel statoChart;
    final private Random random = new Random();
    private List<Number> valuesPratiche;
    private List<Number> valuesTotale;
    private List<Number> valuesEsito;
    private List<Number> valuesStato;
    private List<String> bgColors;
    private List<String> bgColorsAlfa;
    private List<String> labels;
    private BigDecimal totaleRichiesta;
    private int totalePratiche;
    
    public void initialize() { 
        totalePratiche = 0;
        totaleRichiesta = new BigDecimal(0);
        List<Stat> lStatCom = ss.getStatistichePerComune();
        List<Stat> lStatEsito = ss.getStatistichePerEsito();
        List<Stat> lStatStato = ss.getStatistichePerStato();
                                      
        valuesPratiche = new ArrayList<>();
        valuesTotale = new ArrayList<>();
        valuesEsito = new ArrayList<>();
        valuesStato = new ArrayList<>();
        bgColors = new ArrayList<>();
        bgColorsAlfa = new ArrayList<>();
        labels = new ArrayList<>();        
        
        lStatCom.forEach(com -> {
            valuesPratiche.add(com.getNumeroPratiche());
            totalePratiche+=com.getNumeroPratiche();
            valuesTotale.add(com.getTotale());
            totaleRichiesta = totaleRichiesta.add(com.getTotale());
            
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            bgColors.add("rgb("+r+", "+g+", "+b+")");
            bgColorsAlfa.add("rgb("+r+", "+g+", "+b+", 0.2)");
            
            labels.add(com.getVoce());
        });        
            
        creaNumeroPraticheComuneChart();
        creaTotaleComuneChart();                                                                      
        
        bgColors = new ArrayList<>();
        bgColorsAlfa = new ArrayList<>();
        labels = new ArrayList<>();
        lStatEsito.forEach(com -> {
            valuesEsito.add(com.getNumeroPratiche()); 
            
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            bgColors.add("rgb("+r+", "+g+", "+b+")");
            bgColorsAlfa.add("rgb("+r+", "+g+", "+b+", 0.2)");
            
            labels.add(com.getVoce());
        });
        
        creaEsitoChart();
        
        bgColors = new ArrayList<>();
        bgColorsAlfa = new ArrayList<>();
        labels = new ArrayList<>();
        lStatStato.forEach(com -> {
            valuesStato.add(com.getNumeroPratiche()); 
            
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            bgColors.add("rgb("+r+", "+g+", "+b+")");
            bgColorsAlfa.add("rgb("+r+", "+g+", "+b+", 0.2)");
            
            labels.add(com.getVoce());
        });
        
        creaStatoChart();
    }
    
    public PolarAreaChartModel getComunePraticheChart() {
        return comunePraticheChart;
    }        

    public HorizontalBarChartModel getComuneTotaleChart() {
        return comuneTotaleChart;
    }        

    public PieChartModel getEsitoChart() {
        return esitoChart;
    }

    public BarChartModel getStatoChart() {
        return statoChart;
    }
                
    private void creaNumeroPraticheComuneChart() {
        comunePraticheChart = new PolarAreaChartModel();
        ChartData data = new ChartData();
        PolarAreaChartDataSet dataSet = new PolarAreaChartDataSet();
        
        dataSet.setBackgroundColor(bgColors);
        dataSet.setData(valuesPratiche);        
        data.addChartDataSet(dataSet);  
        data.setLabels(labels);
        
        PolarAreaChartOptions options = new PolarAreaChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Pratiche per Comune ("+totalePratiche+")");
        title.setFontSize(16);
        options.setTitle(title);  
        
        comunePraticheChart.setData(data);                        
        comunePraticheChart.setOptions(options);        
    }

    private void creaTotaleComuneChart() {
        comuneTotaleChart = new HorizontalBarChartModel();
        ChartData data = new ChartData();
        HorizontalBarChartDataSet dataSet = new HorizontalBarChartDataSet();
        dataSet.setLabel("Totale richiesto: "+decimalFormat(totaleRichiesta, "#,##0.00 €"));        
        
        dataSet.setBackgroundColor(bgColorsAlfa);
        dataSet.setBorderColor(bgColors);
        dataSet.setBorderWidth(1);
        dataSet.setData(valuesTotale);        
        data.addChartDataSet(dataSet);  
        data.setLabels(labels);
              
        BarChartOptions options = new BarChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Totale per Comune");
        title.setFontSize(16);
        options.setTitle(title);        
        
        comuneTotaleChart.setData(data);        
        comuneTotaleChart.setOptions(options);    
        comuneTotaleChart.setExtender("comuneTotaleExtender");
    }

    private void creaEsitoChart() {
        esitoChart = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();        
        dataSet.setData(valuesEsito);        
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);        
        data.setLabels(labels);

        PieChartOptions options = new PieChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Pratiche per Esito ("+totalePratiche+")");
        title.setFontSize(16);
        options.setTitle(title);
        
        esitoChart.setData(data);
        esitoChart.setOptions(options);
    }

    private void creaStatoChart() {
        statoChart = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet dataSet = new BarChartDataSet();        
        dataSet.setData(valuesStato);        
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);        
        data.setLabels(labels);
        dataSet.setLabel("Totale pratiche "+totalePratiche);

        BarChartOptions options = new BarChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Pratiche per Stato ("+totalePratiche+")");
        title.setFontSize(16);
        options.setTitle(title);
        
        statoChart.setData(data);
        statoChart.setOptions(options);
    }
}
