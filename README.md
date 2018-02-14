# Spider<br/>
It can be used to make peddle robot to walk<br/>
It is just small application created to learn Executor Framework and WatcherService.<br/>
This application can be launched by :<br/>

              java -jar spider-0.0.1-SNAPSHOT.jar legs distance
      
Each leg move in a different thread.<br/>
They send trigger in a sequence. <br/>
Dynamically can change the number of legs to increase the speed.<br/>
 
 
 Sample output:
Feb 14, 2018 3:29:43 PM com.spider.impl.SpiderImpl message<br/>
INFO: The Spider moved with leg 1<br/>
Feb 14, 2018 3:29:44 PM com.spider.impl.SpiderImpl message<br/>
INFO: The Spider moved with leg 2<br/>
Feb 14, 2018 3:29:45 PM com.spider.impl.SpiderImpl message<br/>
INFO: The Spider moved with leg 3<br/>
Feb 14, 2018 3:29:45 PM com.spider.impl.SpiderImpl message<br/>
INFO: The Spider moved with leg 1<br/>
Feb 14, 2018 3:29:46 PM com.spider.impl.SpiderImpl message<br/>
INFO: The Spider moved with leg 2<br/>
Feb 14, 2018 3:29:47 PM com.spider.start.SpiderMain main<br/>
INFO: The number of steps walked by spider is 5<br/>
 
 
 
 
 
 Known bugs:<br/>
 Able to change the legs only once from the config file.<br/>

