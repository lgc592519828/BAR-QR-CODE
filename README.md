# BAR-QR-CODE
**生成一维条码、二维码工具**

* 2019.03.28新增：Jbarcode CSDN bolg实现及讲解，并为提供添加备注的解决方案

* 2019.03.27新增：barcode4j CSDN blog实现及讲解

* 2019.03.25新增：提供生成一维条码工具类，基于Jbarcode开源库

* 2019.03.23新增：提供生成一维条码工具类，基于barcode4j开源类库



---
+ Maven pom.xml
```
<dependency>  
   <groupId>net.sf.barcode4j</groupId>  
   <artifactId>barcode4j</artifactId>  
   <version>2.1</version>  
</dependency>
```
+ 由于没有找到相关Jbarcode pom配置, 需要手动加进仓库。  
这里提供jar
[jbarcode-0.2.8.jar](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/jbarcode-0.2.8.jar)
---
**效果图**   

  * barcode4j：`bean.doQuietZone(true); UnitConv.in2mm(3.0f / dpi)` 三张效果不同因为这两个属性设定有关   
  
![barcode4j_01](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/src/main/java/cn/gcheng/images/barcode4j_01.png)   
![barcode4j_02](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/src/main/java/cn/gcheng/images/barcode4j_02.png)   
![barcode4j_03](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/src/main/java/cn/gcheng/images/barcode4j_03.png)   

  
  * Jbarcode： 是否添加备注的两版，单行/多行显示备注信息
  
![Jbarcode_01](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/src/main/java/cn/gcheng/images/Jbarcode_01.png)   
![Jbarcode_03](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/src/main/java/cn/gcheng/images/Jbarcode_03.png)   
![Jbarcode_02](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/src/main/java/cn/gcheng/images/Jbarcode_02.png) 


  
---
+ 提供链接：  

1. barcode4j官方文档 : [http://barcode4j.sourceforge.net/trunk/javadocs/index.html](http://barcode4j.sourceforge.net/trunk/javadocs/index.html)

2. 基于barcode4j生成条码工具blog：[https://blog.csdn.net/lgc592519828/article/details/88805976](https://blog.csdn.net/lgc592519828/article/details/88805976)

3. 基于Jbarcode生成条码工具blog：[https://blog.csdn.net/lgc592519828/article/details/88874274](https://blog.csdn.net/lgc592519828/article/details/88874274)

4. 一维条码类型(根据需求选择合适项目的就好)
![barcodeType](https://github.com/lgc592519828/BAR-QR-CODE/blob/master/src/main/java/cn/gcheng/images/barcodeType.png)
