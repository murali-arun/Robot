# Spider
It can be used to make peddle robot to walk
It is just small application created to learn Executor Framework and WatcherService.
This application can be launched by :

              java -jar spider-0.0.1-SNAPSHOT.jar legs distance
      
Each leg move in a different thread.
They send trigger in a sequence. 
Dynamically can change the number of legs to increase the speed.
 
 
 Sample output:
Feb 14, 2018 3:29:43 PM com.spider.impl.SpiderImpl message
INFO: The Spider moved with leg 1
Feb 14, 2018 3:29:44 PM com.spider.impl.SpiderImpl message
INFO: The Spider moved with leg 2
Feb 14, 2018 3:29:45 PM com.spider.impl.SpiderImpl message
INFO: The Spider moved with leg 3
Feb 14, 2018 3:29:45 PM com.spider.impl.SpiderImpl message
INFO: The Spider moved with leg 1
Feb 14, 2018 3:29:46 PM com.spider.impl.SpiderImpl message
INFO: The Spider moved with leg 2
Feb 14, 2018 3:29:47 PM com.spider.start.SpiderMain main
INFO: The number of steps walked by spider is 5
 
 
 
 
 
 Known bugs:
 Able to change the legs only once from the config file.

