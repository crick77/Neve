/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.model.Stat;
import it.usr.web.neve.service.StatisticheService;
import java.awt.Color;
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
    private PieChartModel tipologiaChart;
    private BarChartModel statoChart;
    final private Random random = new Random();
    private List<Number> valuesPratiche;
    private List<Number> valuesTotale;
    private List<Number> valuesEsito;
    private List<Number> valuesStato;
    private List<Number> valuesTipologia;
    private List<String> bgColors;
    private List<String> bgColorsAlfa;
    private List<String> labels;
    private BigDecimal totaleRichiesta;
    private int totalePratiche;
    public final static float ALPHA = 0.2f;
    
    public void initialize() { 
        totalePratiche = 0;
        totaleRichiesta = new BigDecimal(0);
        List<Stat> lStatCom = ss.getStatistichePerComune();
        List<Stat> lStatEsito = ss.getStatistichePerEsito();
        List<Stat> lStatStato = ss.getStatistichePerStato();
        List<Stat> lStatTipologia = ss.getStatistichePerTipologia();
                                      
        valuesPratiche = new ArrayList<>();
        valuesTotale = new ArrayList<>();
        valuesEsito = new ArrayList<>();
        valuesStato = new ArrayList<>();
        valuesTipologia = new ArrayList<>();
        bgColors = new ArrayList<>();
        bgColorsAlfa = new ArrayList<>();
        labels = new ArrayList<>();        
        
        lStatCom.forEach(com -> {
            valuesPratiche.add(com.getNumeroPratiche());
            totalePratiche+=com.getNumeroPratiche();
            valuesTotale.add(com.getTotale());
            totaleRichiesta = totaleRichiesta.add(com.getTotale());
            
            Color c = colorGen(255, 255, 255);
            bgColors.add(colorToString(c));
            bgColorsAlfa.add(colorToString(c, ALPHA));
            
            labels.add(com.getVoce()+" per "+decimalFormat(com.getTotale(), CURRENCY_PATTERN));
        });        
             
        creaNumeroPraticheComuneChart();
        creaTotaleComuneChart();                                                                      
        
        bgColors = new ArrayList<>();
        bgColorsAlfa = new ArrayList<>();
        labels = new ArrayList<>();
        lStatEsito.forEach(com -> {
            valuesEsito.add(com.getNumeroPratiche()); 
            
            Color c = colorGen(255, 255, 128);
            bgColors.add(colorToString(c));
            bgColorsAlfa.add(colorToString(c, ALPHA));
            
            labels.add(com.getVoce()+" per "+decimalFormat(com.getTotale(), CURRENCY_PATTERN));
        });
                
        creaEsitoChart();
        
        bgColors = new ArrayList<>();
        bgColorsAlfa = new ArrayList<>();
        labels = new ArrayList<>();
        lStatStato.forEach(com -> {
            valuesStato.add(com.getNumeroPratiche()); 
            
            Color c = colorGen(255, 128, 255);
            bgColors.add(colorToString(c));
            bgColorsAlfa.add(colorToString(c, ALPHA));
            
            labels.add(com.getVoce()+" per "+decimalFormat(com.getTotale(), CURRENCY_PATTERN));
        });
        
        creaStatoChart();
        
        bgColors = new ArrayList<>();
        bgColorsAlfa = new ArrayList<>();
        labels = new ArrayList<>();
        lStatTipologia.forEach(tip -> {
            valuesTipologia.add(tip.getNumeroPratiche()); 
            
            Color c = colorGen(128, 255, 128);
            bgColors.add(colorToString(c));
            bgColorsAlfa.add(colorToString(c, ALPHA));
            
            labels.add(tip.getVoce()+" per "+decimalFormat(tip.getTotale(), CURRENCY_PATTERN));
        });
        
        createTipologiaChart();
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

    public PieChartModel getTipologiaChart() {
        return tipologiaChart;
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
        title.setText("Pratiche per Comune (# pratiche: "+totalePratiche+")");
        title.setFontSize(16);
        options.setTitle(title);  
        
        comunePraticheChart.setData(data);                        
        comunePraticheChart.setOptions(options);        
    }

    private void creaTotaleComuneChart() {
        comuneTotaleChart = new HorizontalBarChartModel();
        ChartData data = new ChartData();
        HorizontalBarChartDataSet dataSet = new HorizontalBarChartDataSet();
        dataSet.setLabel("Totale richiesto: "+decimalFormat(totaleRichiesta, CURRENCY_PATTERN));        
        
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
        title.setText("Pratiche per Esito (# pratiche: "+totalePratiche+")");
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
        title.setText("Pratiche per Stato (# pratiche: "+totalePratiche+")");
        title.setFontSize(16);
        options.setTitle(title);
        
        statoChart.setData(data);
        statoChart.setOptions(options);
    }

    private void createTipologiaChart() {
        tipologiaChart = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();        
        dataSet.setData(valuesTipologia);        
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);        
        data.setLabels(labels);

        PieChartOptions options = new PieChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Pratiche per Tipologia (# pratiche: "+totalePratiche+")");
        title.setFontSize(16);
        options.setTitle(title);
        
        tipologiaChart.setData(data);
        tipologiaChart.setOptions(options);
    }
    
    private Color colorGen(int r, int g, int b) {       
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color        
        red = (red + r) / 2;
        green = (green + g) / 2;
        blue = (blue + b) / 2;        
        
        return new Color(red, green, blue);
    }
    
    private String colorToString(Color color, Float... alpha) {
        if(alpha.length > 0) {
            return "rgb("+color.getRed()+", "+color.getGreen()+", "+color.getBlue()+", "+alpha[0]+")";
        }
        else {
            return "rgb("+color.getRed()+", "+color.getGreen()+", "+color.getBlue()+")";
        }
    }
}
