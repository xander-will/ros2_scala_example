# ros2_scala_example

This is an example of how use Scala with ROS2, based on [RCLJava](https://github.com/ros2-java/ros2_java). The following was tested on Ubuntu 20.04 using the ROS2 Foxy distribution. 

### Install dependencies

1. Install [ROS2](https://docs.ros.org/en/foxy/Installation/Ubuntu-Install-Debians.html) (this example is for Foxy).

1. Install Java and a JDK.
    sudo apt install default-jdk

1. Install Gradle. Make sure you check the [gradle releases page](https://gradle.org/releases/) to see if a newer version is available.
    VERSION=7.3
    wget https://services.gradle.org/distributions/gradle-${VERSION}-bin.zip -P /tmp
    sudo unzip -d /opt/gradle /tmp/gradle-${VERSION}-bin.zip

1. Set up Gradle environment variables. Open a file with the following name:
    sudo nano /etc/profile.d/gradle.sh

Then copy-and-paste the following
    export GRADLE_HOME=/opt/gradle/latest
    export PATH=${GRADLE_HOME}/bin:${PATH}

Save and close the file. Then make the script executable and source the script.
    sudo chmod +x /etc/profile.d/gradle.sh

1. Install Scala.
    sudo apt-get install scala

1. Install build tools.
    sudo apt install curl python3-colcon-common-extensions python3-pip python3-vcstool

1. Install Gradle extensions for colcon.
    python3 -m pip install -U git+https://github.com/colcon/colcon-gradle
    python3 -m pip install --no-deps -U git+https://github.com/colcon/colcon-ros-gradle

### Building ROS2 Java
1. Source your ROS2 installation, for example:
    source /opt/ros/foxy/setup.bash

1. Source the Gradle environment.
    source /etc/profile.d/gradle.sh

1. Download the ROS2 Java repositories into a workspace.
    mkdir -p ros2_java_ws/src
    cd ros2_java_ws
    curl -skL https://raw.githubusercontent.com/ros2-java/ros2_java/main/ros2_java_desktop.repos | vcs import src

1. Install ROS dependencies (on Linux). Note that you may need ot skip other keys other than listed below
    rosdep install --from-paths src -y -i --skip-keys "ament_tools" --skip-keys "ament_pep8"

1. Build desktop packages.
    colcon build --symlink-install

### Building ROS2 Scala Examples
1. Add the Scala libraries to Java's class path.
    CLASSPATH=/usr/share/scala-2.11/lib/*:$CLASSPATH
    export CLASSPATH

1. Build the Scala example package.
    colcon build --packages-select scala_example

1. Source the setup files.
    . install/setup.bash

1. Run the code through java (unsure of how to expose the entry point to ROS2 as of now). If you open a second terminal to run both the publisher and subscriber at once, make sure you source the ROS2 setup script, the gradle environment script, and the local setup files, as well as readding the Scala lib to CLASSPATH.
    java org.ros2.rcljava.scala.examples.PublisherLambda
    java org.ros2.rcljava.scala.examples.SubscriberLambda