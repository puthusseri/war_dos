# Local setup
Tested in MAC M1

Step 1: Download or clone the source code from GitHub to the local machine

Step 2: Install JDK 17 
```wget https://download.oracle.com/java/18/archive/jdk-18.0.2.1_linux-x64_bin.deb```
```sudo dpkg -i jdk-18.0.2.1_linux-x64_bin.deb```

Step 4: Install Apache Maven - https://maven.apache.org/install.htm
`sudo apt update
sudo apt-get install maven
mvn --version
`

Step 5:  ```mvn clean install```

Step 6:  ```mvn spring-boot:run```

Step 7: From the browser call the endpoint http://localhost:8080

Step 8: Admin Login User Id: ```admin`` & Password: ```admin```

