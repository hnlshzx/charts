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
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */
package com.demo.xclcharts.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

/**
 * @ClassName PieChart01View
 * @Description  饼图的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class PieChart01View extends TouchView implements Runnable{
	
	private String TAG = "PieChart01View";
	private PieChart chart = new PieChart();	
	private LinkedList<PieData> chartData = new LinkedList<PieData>();

	public PieChart01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		chartDataSet();	
		chartRender();
		new Thread(this).start();
	}
	
	private void chartRender()
	{
		try {					
			
			//图所占范围大小
			chart.setChartRange(0.0f, 0.0f,getScreenWidth(),getScreenHeight());
			
			//图的内边距
			chart.setPadding(10, 20, 15, 15);
			
			//设定数据源
			//chart.setDataSource(chartData);			
			
			//设置起始偏移角度(即第一个扇区从哪个角度开始绘制)
			chart.setInitialAngle(90);	
			
			//标签显示(隐藏，显示在中间，显示在扇区外面)
			chart.setLabelLocation(XEnum.ArcLabelLocation.OUTSIDE);
			
			//标题
			chart.setTitle("饼图-Pie Chart");
			chart.addSubtitle("(XCL-Charts Demo)");
			chart.getPlotTitle().setTitlePosition(XEnum.Position.LOWER);
			
			//显示key值
			chart.getPlotKey().showKeyLabels();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//设置图表数据源		
		chartData.add(new PieData("HP","20%",20,(int)Color.rgb(155, 187, 90)));
		chartData.add(new PieData("IBM","30%",30,(int)Color.rgb(191, 79, 75),false));
		chartData.add(new PieData("DELL","10%",10,(int)Color.rgb(242, 167, 69)));
		//将此比例块突出显示
		chartData.add(new PieData("EMC","40%",40,(int)Color.rgb(60, 173, 213),true));
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
		          	List<Double> dataSeries= new LinkedList<Double>();	
		          	dataSeries.add(0d);       
		          	for(int i=0;i< chartData.size() ;i++)
		          	{
		          		Thread.sleep(100);
		          		LinkedList<PieData> animationData = new LinkedList<PieData>();
		        
		          		int sum = 0;
		          		for(int j=0;j<=i;j++)
		                  {            			            			
		          			animationData.add(chartData.get(j));
		          			sum += chartData.get(j).getPercentage();
		                  }   		          		
		          				          				          		
		          		animationData.add(new PieData("","", 100 - sum,(int)Color.argb(1, 0, 0, 0)));		          		
		          		chart.setDataSource(animationData);
		          		postInvalidate();            		
		          	          	
		          }
			  
          }
          catch(Exception e) {
              Thread.currentThread().interrupt();
          }       
		  
	}
	
}
