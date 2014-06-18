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
 * @version v0.1
 */
package org.xclcharts.chart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.xclcharts.renderer.LnChart;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.line.PlotDot;
import org.xclcharts.renderer.line.PlotLine;

import android.graphics.Paint.Align;

/**
 * @ClassName SplineChart
 * @Description  曲线图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  * MODIFIED    YYYY-MM-DD   REASON
 */
public class SplineChart extends LnChart{
	
	//数据源
	private List<SplineData> mDataset;
	
	//标签轴的最大，最小值
	private float mLablesAxisMax = 0.0f;
	private float mLablesAxisMin = 0.0f;
		
	public SplineChart()
	{
		super();
		initChart();
	}
	
	private void initChart()
	{
		labelsAxis.setAxisHorizontalTickAlign(Align.CENTER);
		dataAxis.setAxisHorizontalTickAlign(Align.LEFT);
	}
	
	/**
	 * 标签轴的数据源
	 * @param labels 标签集
	 */
	public void setLabels(List<String> labels)
	{
		labelsAxis.setDataBuilding(labels);
	}
	
	/**
	 *  设置数据轴的数据源
	 * @param dataSeries 数据序列
	 */
	public void setDataSource(List<SplineData> dataSeries)
	{
		this.mDataset = dataSeries;		
	}	
	
	/**
	 *  显示数据的数据轴最大值
	 * @param value 数据轴最大值
	 */
	public void setLabelsAxisMax(float value)
	{
		mLablesAxisMax = value;
	}	
	
	/**
	 * 设置标签轴最小值
	 * @param value 最小值
	 */
	public void setLabelsAxisMin(float value)
	{
		mLablesAxisMin = value;
	}	
	
	/**
	 * 绘制线
	 * @param bd	数据集
	 * @param type	处理类型号
	 */
	private void renderLine(SplineData bd,String type)
	{
		float initX =  plotArea.getPlotLeft();
        float initY =  plotArea.getPlotBottom();
		float lineStartX = initX;
        float lineStartY = initY;
        float lineEndX = 0.0f;
        float lineEndY = 0.0f;        
    	
    	float axisScreenWidth = getAxisScreenWidth(); 
    	float axisScreenHeight = getAxisScreenHeight();
		float axisDataHeight = (float) dataAxis.getAxisRange(); //getAxisDataRange();		
		
		//得到标签对应的值数据集		
		LinkedHashMap<Double,Double> chartValues = bd.getLineDataSet();		
															
	    //画出数据集对应的线条				
		int j = 0;	
		Iterator iter = chartValues.entrySet().iterator();
		while(iter.hasNext()){
			    Entry  entry=(Entry)iter.next();
			
			    Double xValue =(Double) entry.getKey();
			    Double yValue =(Double) entry.getValue();			
								
			    //对应的Y坐标
            	float YvaluePostion = (float) Math.round( 
						axisScreenHeight * ( (yValue - dataAxis.getAxisMin() ) / axisDataHeight)) ;  
            	
            	//对应的X坐标	                	
            	float XvaluePostion = (float) Math.round( 
            			axisScreenWidth * ( (xValue - mLablesAxisMin ) / (mLablesAxisMax - mLablesAxisMin))) ;  
            
            	if(j == 0 )
				{	                		
            		lineStartX = initX + XvaluePostion;
					lineStartY = initY - YvaluePostion;
					
					lineEndX = lineStartX ;
					lineEndY = lineStartY;														
				}else{
					lineEndX =  initX + XvaluePostion;  
					lineEndY = initY - YvaluePostion;
				}
            	            	
            	PlotLine pLine = bd.getPlotLine();             
            	if(type.equalsIgnoreCase("LINE"))
            	{
            		mCanvas.drawLine( lineStartX ,lineStartY ,lineEndX ,lineEndY,pLine.getLinePaint());
            	}else if(type.equalsIgnoreCase("DOT2LABEL")){
            		
            		if(!pLine.getDotStyle().equals(XEnum.DotStyle.HIDE))
                	{
                		float rendEndX = lineEndX;                		
                		PlotDot pDot = pLine.getPlotDot();	                
                		rendEndX  = lineEndX  + pDot.getDotRadius();               		
            			
                		renderDot(pDot, 
                				lineStartX ,lineStartY ,
                				lineEndX ,lineEndY,
                				pLine.getDotPaint()); //标识图形            			                	
            			lineEndX = rendEndX;
                	}
            		
            		if(bd.getLineLabelVisible())
                	{
                		//fromatter
            			mCanvas.drawText("("+Double.toString(xValue)+","+ Double.toString(yValue) +")",
    							lineEndX, lineEndY,  pLine.getDotLabelPaint());
                	}
            	}else{
            		return ;
            	}                  					
				lineStartX = lineEndX;
				lineStartY = lineEndY;

				j++;	              								
		}							
		
	}
	
	/**
	 * 绘制图
	 */
	private void renderVerticalPlot()
	{
		//检查是否有设置标签轴的最大最小值		
		if(mLablesAxisMax == mLablesAxisMin && 0 == mLablesAxisMax) return ;
						
		renderVerticalDataAxis();
		renderVerticalLabelsAxis();		
		
		
		//开始处 X 轴 即标签轴              
		List<LnData> lstKey = new ArrayList<LnData>();		
		for(int i=0;i<mDataset.size();i++)
		{										
			renderLine( mDataset.get(i),"LINE");
			renderLine( mDataset.get(i),"DOT2LABEL");	
			lstKey.add(mDataset.get(i));
		}	
		renderKey(lstKey);
	}
	
	
	public boolean render() throws Exception {
		// TODO Auto-generated method stub
	
		try {
			super.render();
						
			//绘制图表
			renderVerticalPlot();
			
		}catch( Exception e){
			 throw e;
		}
		return true;
	}
	
}
