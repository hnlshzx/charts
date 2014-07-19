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
package org.xclcharts.common;

import java.math.BigDecimal;


/**
 * @ClassName MathHelper
 * @Description  集中了 图形相关的一些用于计算的小函数
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class MathHelper {

	private static MathHelper instance = null;
	
	//Position位置
	private float posX = 0.0f;
	private float posY = 0.0f;
	
	//除法运算精度
	private static final int DEFAULT_DIV_SCALE = 10;
		
	public MathHelper()
	{	
	}
	
	public static synchronized MathHelper getInstance()
	{
		 if(instance == null)
		 {
			 instance = new MathHelper();
		 }
		 return instance;
	}
	
	//依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标	
	public void calcArcEndPointXY(float cirX, float cirY, float mRadius, float cirAngle)
	{	
		
		if(Float.compare(cirAngle, 0.0f) == -1 || Float.compare(cirAngle, 0.0f) == 0 )
			posX = posY = 0.0f;
			
		//将角度转换为弧度		
	    float arcAngle = (float) (Math.PI *  div(cirAngle , 180.0f));
	    if( Float.compare(arcAngle,0.0f) == -1)posX = posY = 0.0f;	
	    
	    if (Float.compare(cirAngle , 90.0f) == -1)
	    {	    		    
	    	posX = add(cirX , (float)Math.cos(arcAngle) * mRadius);
	        posY = add(cirY , (float)Math.sin(arcAngle) * mRadius) ;
	    }
	    else if (Float.compare(cirAngle,00.0f) == 0)
	    {
	        posX = cirX;
	        posY = add(cirY , mRadius);
	    }
	    else if (Float.compare(cirAngle,90.0f) == 1 &&
	    		 Float.compare(cirAngle,180.0f) == -1)
	    {
	    	arcAngle = (float) (Math.PI * (sub(180f , cirAngle)) / 180.0f);	 	    		    		    		    
	        posX = sub(cirX , (float) (Math.cos(arcAngle) * mRadius));
	        posY = add(cirY , (float) (Math.sin(arcAngle) * mRadius));
	    }
	    else if (Float.compare(cirAngle,180.0f) == 0)
	    {
	        posX = (float) (cirX - mRadius);
	        posY = cirY;
	    }
	    else if (Float.compare(cirAngle,180.0f) == 1 &&
	    		 Float.compare(cirAngle,270.0f) == -1) 
	    {
	    	arcAngle = (float) (Math.PI * ( sub(cirAngle , 180.0f)) / 180.0f);
	        posX = sub(cirX , (float) (Math.cos(arcAngle) * mRadius));
	        posY = sub(cirY , (float) (Math.sin(arcAngle) * mRadius));
	    }
	    else if (Float.compare(cirAngle,270.0f) == 0)
	    {
	        posX = cirX;
	        posY = sub (cirY , mRadius);
	    }
	    else
	    {
	    	arcAngle = (float) (Math.PI * ( sub(360.0f , cirAngle )) / 180.0f);
	        posX = add(cirX , (float) (Math.cos(arcAngle) * mRadius)) ;
	        posY = sub(cirY , (float) (Math.sin(arcAngle) * mRadius));
	    }
				
	}

	public float getPosX() {
		return posX;
	}
		
	public float getPosY() {
		return posY;
	}	
			
	
	
	 
	 
	/**
	 * 加法运算
	 * @param v1
	 * @param v2
	 * @return
	 */
	 public float add(float v1, float v2) 
	 {
		  BigDecimal b1 = new BigDecimal(Float.toString(v1));
		  BigDecimal b2 = new BigDecimal(Float.toString(v2));
		  return b1.add(b2).floatValue();
	 }
		 
	 /**
	  * 减法运算
	  * @param v1
	  * @param v2
	  * @return 运算结果
	  */
	 public float sub(float v1, float v2) 
	 {
		 BigDecimal b1 = new BigDecimal(Float.toString(v1));
		 BigDecimal b2 = new BigDecimal(Float.toString(v2));
		 return b1.subtract(b2).floatValue();
	 }
		 
	 /**
	  * 乘法运算
	  * @param v1
	  * @param v2
	  * @return 运算结果
	  */
	 public float mul(float v1, float v2) 
	 {
		  BigDecimal b1 = new BigDecimal(Float.toString(v1));
		  BigDecimal b2 = new BigDecimal(Float.toString(v2));
		  return b1.multiply(b2).floatValue();
	 }
		 
	 /**
	  * 除法运算,当除不尽时，精确到小数点后10位
	  * @param v1
	  * @param v2
	  * @return 运算结果
	  */
	 public float div(float v1, float v2)
	 {
		  return div(v1, v2, DEFAULT_DIV_SCALE);
	 }
		 
	 /**
	  * 除法运算,当除不尽时，精确到小数点后scale位
	  * @param v1
	  * @param v2
	  * @param scale
	  * @return 运算结果
	  */
	 public float div(float v1, float v2, int scale) 
	 {
		  if (scale < 0) 
		   throw new IllegalArgumentException("The scale must be a positive integer or zero");
		
		  BigDecimal b1 = new BigDecimal(Float.toString(v1));
		  BigDecimal b2 = new BigDecimal(Float.toString(v2));
		  return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	 }
		 
	 /**
	  * 四舍五入到小数点后scale位
	  * @param v
	  * @param scale
	  * @return 
	  */
	 public float round(float v, int scale) 
	 {
		  if (scale < 0) 
			  throw new IllegalArgumentException("The scale must be a positive integer or zero");
		
		  BigDecimal b = new BigDecimal(Float.toString(v));
		  BigDecimal one = new BigDecimal("1");
		  return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	 }
	
	 /**
	  * float(4字节)转double(8字节),防止 补位误差.
	  * @param f
	  * @return
	  */
	public double ftod(float f)
	{
		//BigDecimal b = new BigDecimal(String.valueOf(f));
		//double d = b.doubleValue();		
		Float t = new Float(f);
		return t.doubleValue();
	}
	
	/**
	 * double转float
	 * @param d
	 * @return
	 */
	public float dtof(double d)
	{
		//BigDecimal b = new BigDecimal(String.valueOf(d));
		//float f = b.floatValue();		
		Double bd = new Double(d);	
		return bd.floatValue();
	}
	
	 public double add(double v1, double v2) 
	 {
		  BigDecimal b1 = new BigDecimal(Double.toString(v1));
		  BigDecimal b2 = new BigDecimal(Double.toString(v2));
		  return b1.add(b2).doubleValue();
	 }
	 
	 public double sub(double v1, double v2) 
	 {
		 BigDecimal b1 = new BigDecimal(Double.toString(v1));
		 BigDecimal b2 = new BigDecimal(Double.toString(v2));
		 return b1.subtract(b2).doubleValue();
	 }
	 
	 public double div(double v1, double v2) 
	 {
		 return div(v1,v2,DEFAULT_DIV_SCALE);
	 }
	 
	 public double div(double v1, double v2, int scale) 
	 {
		  if (scale < 0) 
		   throw new IllegalArgumentException("The scale must be a positive integer or zero");
		
		  BigDecimal b1 = new BigDecimal(Double.toString(v1));
		  BigDecimal b2 = new BigDecimal(Double.toString(v2));
		  return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	 }
	 
	 
	
}
