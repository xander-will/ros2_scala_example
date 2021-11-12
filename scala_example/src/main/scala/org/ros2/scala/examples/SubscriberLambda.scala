package org.ros2.scala.examples;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.consumers.Consumer;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.subscription.Subscription;

class SubscriberLambda extends BaseComposableNode ("minimal_subscriber") {
    val sub_cb = new Consumer[std_msgs.msg.String]() { 
        override def accept(s: std_msgs.msg.String) = 
            println(s"I heard: [${s.getData()}]") 
    }

    val subscription = node.createSubscription[std_msgs.msg.String](classOf[std_msgs.msg.String], "topic", sub_cb)
}

object SubscriberLambda {
    def main(args: Array[String]) =
        RCLJava.rclJavaInit()   // I get an error about invalid context when this is only called once
        RCLJava.rclJavaInit()   // hence why I call it twice :shrug:

        RCLJava.spin(new SubscriberLambda()) 
}