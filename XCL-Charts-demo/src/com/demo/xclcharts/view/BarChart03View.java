/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */
package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.XEnum.LabelAlign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

/**
 * @ClassName BarChart03View
 * @Description 用于展示定制线与明细刻度线
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * MODIFIED    YYYY-MM-DD   REASON
 */
public class BarChart03View extends TouchView implements Runnable{
	
	private String TAG = "BarChart03View";
	private BarChart chart = new BarChart();
	//轴数据源
	private List<String> chartLabels = new LinkedList<String>();
	private List<BarData> chartData = new LinkedList<BarData>();
	private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
	
	public BarChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		chartLabels();
		chartDataSet();
		chartCustomLines();
		chartRender();
		
		new Thread(this).start();
		
	}
	
	private void chartRender()
	{
		try {
			
			//图所占范围大小
			chart.setChartRange(0.0f, 0.0f, getScreenWidth(),getScreenHeight());		
			if(chart.isVerticalScreen())
			{
				chart.setPadding(15, 20, 8, 10);
			}else{
				chart.setPadding(20, 20, 10, 8);
			}
					
			//标题
			chart.setTitle("小小熊 - 期末考试成绩");
			chart.addSubtitle("(XCL-Charts Demo)");	
			//数据源
			//chart.setDataSource(chartData);
			chart.setCategories(chartLabels);	
			chart.setCustomLines(mCustomLineDataset);
			
			//图例
			chart.getLegend().setLeftLegend("分数");
			chart.getLegend().setLowerLegend("科目");
			
			//数据轴
			chart.getDataAxis().setAxisMax(100);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(5);		
			//指隔多少个轴刻度(即细刻度)后为主刻度
			chart.getDataAxis().setDetailModeSteps(5);
			
			//背景网格
			chart.getPlotGrid().showHorizontalLines();
									
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub		
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(tmp).toString();				
					return (label);
				}
				
			});
			
			
			//在柱形顶部显示值
			chart.getBar().setItemLabelVisible(true);
			//设定格式
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0");					 
					String label = df.format(value).toString();
					return label;
				}});
			
			//隐藏Key
			chart.getPlotKey().hideKeyLabels();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void chartDataSet()
	{
		//标签对应的柱形数据集
		List<Double> dataSeriesA= new LinkedList<Double>();	
		dataSeriesA.add(98d); 
		dataSeriesA.add(100d); 
		dataSeriesA.add(95d); 
		dataSeriesA.add(100d); 
		BarData BarDataA = new BarData("",dataSeriesA,(int)Color.rgb(53, 169, 239));
		
		
		chartData.add(BarDataA);
	}
	
	private void chartLabels()
	{
		chartLabels.add("语文"); 
		chartLabels.add("数学"); 
		chartLabels.add("英语"); 
		chartLabels.add("计算机"); 
	}	
	
	/**
	 * 定制线/分界线
	 */
	private void chartCustomLines()
	{				
		CustomLineData line1 = new CustomLineData("及格线",60d,(int)Color.RED,7);
		line1.setCustomLineCap(XEnum.DotStyle.PRISMATIC);		
		line1.setLabelHorizontalPostion(XEnum.LabelAlign.LEFT);
		line1.setLabelOffset(15);	
		line1.getLineLabelPaint().setColor(Color.RED);
		mCustomLineDataset.add(line1);
		
		CustomLineData line2 = new CustomLineData("没过打屁股",60d,(int)Color.RED,7);
		line2.setLabelHorizontalPostion(XEnum.LabelAlign.CENTER);
		line2.hideLine();
		mCustomLineDataset.add(line2);
		
		CustomLineData line3 = new CustomLineData("良好",80d,(int)Color.rgb(35, 172, 57),5);	
		line3.setCustomLineCap(XEnum.DotStyle.RECT);		
		line3.setLabelHorizontalPostion(XEnum.LabelAlign.LEFT);	
		line3.setLineStyle(XEnum.LineStyle.DOT);
		mCustomLineDataset.add(line3);
		
		CustomLineData line4 = new CustomLineData("优秀",90d,(int)Color.rgb(53, 169, 239),5);	
		line4.setCustomLineCap(XEnum.DotStyle.TRIANGLE);
		line4.setLabelOffset(15);
		line4.getLineLabelPaint().setColor((int)Color.rgb(216, 44, 41));		
		line4.setLineStyle(XEnum.LineStyle.DASH);
		mCustomLineDataset.add(line4);
		
		int average =  calcAvg() ;
		CustomLineData line6 = new CustomLineData("本次考试平均得分:"+Integer.toString(average),
														(double)average,(int)Color.BLUE,5);
		line6.setLabelHorizontalPostion(XEnum.LabelAlign.CENTER);
		line6.setLineStyle(XEnum.LineStyle.DASH);
		line6.getLineLabelPaint().setColor(Color.RED);
		mCustomLineDataset.add(line6);							
	}
	
	private int calcAvg()
	{
		return (98+100+95+100)/4;
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub		
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);		
		return lst;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {          
         	chartAnimation();         	
         }
         catch(Exception e) {
             Thread.currentThread().interrupt();
         } 
	}
	
	private void chartAnimation()
	{
		  try {                            	            	 
          	          	
          	for(int i=0;i< chartData.size() ;i++)
          	{           		       		          		          		          	
          		BarData barData = chartData.get(i); 
          		for(int j=0;j<barData.getDataSet().size();j++)
                {     
          			Thread.sleep(100);
          			List<BarData> animationData = new LinkedList<BarData>();
          			List<Double> dataSeries= new LinkedList<Double>();	
          			for(int k=0;k<=j;k++)
          			{          				
          				dataSeries.add(barData.getDataSet().get(k));    
          			}
          			
          			BarData animationBarData = new BarData("",dataSeries,(int)Color.rgb(53, 169, 239));
          			animationData.add(animationBarData);
          			chart.setDataSource(animationData);
          			postInvalidate(); 
                }          		          		   
          	}
          	 
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }            
	}
}