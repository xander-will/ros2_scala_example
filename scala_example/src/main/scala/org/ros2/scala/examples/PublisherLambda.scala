package org.ros2.rcljava.scala.examples;

import java.util.concurrent.TimeUnit;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.concurrent.Callback;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.publisher.Publisher;
import org.ros2.rcljava.timer.WallTimer;

class PublisherLambda extends BaseComposableNode ("minimal_publisher") {
    var count = 0
    val publisher = node.createPublisher[std_msgs.msg.String](classOf[std_msgs.msg.String], "topic")

    val timerCallback = new Callback() {
        override def call() = {
            val message = new std_msgs.msg.String()
            message.setData(s"Hello, world! $count")
            count += 1
            println(s"Publishing: [${message.getData()}]")
            publisher.publish(message)
        }
    }
    
    val timer = node.createWallTimer(500, TimeUnit.MILLISECONDS, timerCallback)
}

object PublisherLambda {
    def main(args: Array[String]) =
        RCLJava.rclJavaInit()   // I get an error about invalid context when this is only called once
        RCLJava.rclJavaInit()   // hence why I call it twice :shrug:

        RCLJava.spin(new PublisherLambda()) 
}