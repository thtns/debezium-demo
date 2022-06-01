# debezium-demo


1. 修改application.yml中
 ~~~
   debezium:
    includeDb: car  //库名
    includeTable: car.biz_card  //库名加表名
    ip: localhost //数据库地址
    port: 55001   //数据库ip
~~~

2. 创建service实现类实现ProcessDataStrategy接口，参照BizCardStrategyImpl实现自己的业务逻辑。
