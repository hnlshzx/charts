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
package org.xclcharts.chart;

import java.util.List;

import org.xclcharts.common.MathHelper;
import org.xclcharts.renderer.CirChart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * @ClassName PieChart
 * @Description  饼图基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class PieChart extends CirChart{
	
	private static final String TAG = "PieChart";

	//数据源
	private List<PieData> mDataset;

	public PieChart()
	{
		super();
	}


	/**
	 * 设置图表的数据源
	 * @param piedata 来源数据集
	 */
	public void setDataSource(List<PieData> piedata)
	{
		this.mDataset = piedata;
	}
	
	/**
	 * 返回数据轴的数据源
	 * @return 数据源
	 */
	public List<PieData> getDataSource()
	{
		return mDataset;
	}
		
	/**
	 * 绘制指定角度扇区
	 * @param paintArc 画笔
	 * @param arcRF0   范围
	 * @param cData  数据集
	 * @param cirX   中心点X坐标
	 * @param cirY   中心点Y坐标
	 * @param radius  半径
	 * @param offsetAgent 偏移角度
	 * @param curretAgent 当前绘制角度
	 * @throws Exception  例外处理
	 */
	protected void drawSlice(Canvas canvas, Paint paintArc,RectF arcRF0,
							PieData cData,
							final float cirX,
							final float cirY,
							final float radius,
							final float offsetAgent,
							final float curretAgent) throws Exception
	{
		try{
		
			//在饼图中显示所占比例  
        	canvas.drawArc(arcRF0, offsetAgent, curretAgent, true, paintArc);
         
            //标签
        	renderLabel(canvas,cData.getLabel(),cirX, cirY,
	        			radius,offsetAgent,curretAgent);          
		}catch( Exception e){
			throw e;
		}
	}
		
	
	/**
	 * 绘制指定角度扇区
	 * @param paintArc 画笔
	 * @param cData 数据集
	 * @param cirX  中心点X坐标
	 * @param cirY  中心点Y坐标
	 * @param radius 半径
	 * @param offsetAgent 偏移角度
	 * @param curretAgent 当前绘制角度
	 * @throws Exception  例外处理
	 */
	protected void drawSelectedSlice(Canvas canvas, Paint paintArc,
									PieData cData,
									final float cirX,
									final float cirY,
									final float radius,
									final float offsetAgent,
									final float curretAgent) throws Exception
	{
		try{
			//偏移圆心点位置(默认偏移半径的1/10)
	    	float newRadius = radius /10;
	    	 //计算百分比标签
	    	MathHelper.getInstance().calcArcEndPointXY(cirX,cirY,
	    							newRadius,offsetAgent + curretAgent/2); 	
	        
	        float arcLeft = MathHelper.getInstance().getPosX() - radius;  
	        float arcTop  = MathHelper.getInstance().getPosY() - radius ;  
	        float arcRight = MathHelper.getInstance().getPosX() + radius ;  
	        float arcBottom = MathHelper.getInstance().getPosY() + radius ;  
	        RectF arcRF1 = new RectF(arcLeft ,arcTop,arcRight,arcBottom);   
	        
	        //在饼图中显示所占比例  
	        canvas.drawArc(arcRF1, offsetAgent, curretAgent, true, paintArc);
	        
	        //标签
	        renderLabel(canvas,cData.getLabel(),MathHelper.getInstance().getPosX(), 
	        									MathHelper.getInstance().getPosY(),
	        			radius,offsetAgent,curretAgent);	   
	        
		}catch( Exception e){
			 throw e;
		}
	}
	
	/**
	 * 绘制图
	 */
	protected void renderPlot(Canvas canvas)
	{
		try{	
			if(null == mDataset)return;
			//中心点坐标
			float cirX = plotArea.getCenterX();
		    float cirY = plotArea.getCenterY();		     
	        float radius = getRadius();
	              
	        //确定去饼图范围
	        float arcLeft = cirX - radius;  
	        float arcTop  = cirY - radius ;  
	        float arcRight = cirX + radius ;  
	        float arcBottom = cirY + radius ;  
	        RectF arcRF0 = new RectF(arcLeft ,arcTop,arcRight,arcBottom);   
	        	     
		     
	        //画笔初始化
			Paint paintArc = new Paint();  
			paintArc.setAntiAlias(true);	
			
			//用于存放当前百分比的圆心角度
	        float currentAgent = 0.0f;		
	        //float totalAgent = 0.0f;
			
			for(PieData cData : mDataset)
			{
				paintArc.setColor(cData.getSliceColor());				
				currentAgent = cData.getSliceAgent();		
				
			    if(cData.getSelected()) //指定突出哪个块
	            {			    	            		            	
	            	drawSelectedSlice(canvas,paintArc,cData,
	            			cirX,cirY,radius,
	            			mOffsetAgent,currentAgent);			    		            		            		            
	            }else{
	            	drawSlice(canvas,paintArc,arcRF0,cData,
	            			cirX,cirY,radius,
	            			mOffsetAgent,currentAgent);	            	
	            }
	          //下次的起始角度  
	            mOffsetAgent += currentAgent;  
			}					
			
			//图KEY
			plotKey.renderPieKey(canvas,this.mDataset);
		
		 }catch( Exception e){
			 Log.e(TAG,e.toString());
		 }
		
	}
	
	/**
	 * 检验传入参数,累加不能超过360度
	 * @return 是否通过效验
	 */
	protected boolean checkInput()
	{
		float totalAgent = 0.0f;	
		if(null == mDataset)return false;
		
		for(PieData cData : mDataset)
		{
			totalAgent += cData.getSliceAgent();
			if( totalAgent > 360)
			{
				//圆心角总计大于360度
				Log.e(TAG,"传入参数不合理，圆心角总计大于360度. 现有圆心角合计:"
							+Float.toString(totalAgent));
				return false;
			}
		}
		return true;
	}

	
	@Override
	protected boolean postRender(Canvas canvas) throws Exception 
	{	
		try {
			super.postRender(canvas);
			
			//检查值是否合理
	        if(false == checkInput())return false;
			
			//绘制图表
			renderPlot(canvas);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
}
