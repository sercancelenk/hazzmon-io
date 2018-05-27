# Hazelcast Keys Monitor & Management 
A keys monitoring and management library for Java projects, app only needs one-line-code to setup.

- 1.1 Core Initial commit. 
- 1.1 UI Initial commit.

# System Requirements

1. Spring Framework Architecture
2. Hazelcast +3.7.9

# Getting started

You may choose how to assemble them as you like.

```maven
    <dependency>
	<groupId>byzas.libs</groupId>
	<artifactId>hazzmon-core</artifactId>
	<version>1.1</version>
    </dependency>
    
    <dependency>
	<groupId>byzas.libs</groupId>
	<artifactId>hazzmon-ui</artifactId>
	<version>1.1</version>
    </dependency>
```

# Usage

From Source:
```shell
1.  git clone https://github.com/sercancelenk/hazzmon-io
2.  cd hazzmon-io
3.  cd hazzmon-core
4.  mvn clean install [-DskipTests]
5.  cd ../hazzmon-ui
6.  mvn -Pui-build clean process-resources [-DskipTests] && mvn install [-DskipTests]
7.  Add library to your classpath.
8.  Add @EnableHazzmonListener annotation to your configuration.
9.  Then go to /hazzmon-ui.html page.
10. And see monitoring ui.
```
# How does it work?

1. Hazzmon-core library setup hazzmon-api endpoints for your usage.
2. Hazzmon-ui library setup ui.

# Screenshots

![image](https://user-images.githubusercontent.com/289513/40587365-e84867ae-61d6-11e8-9667-6d16e7569b0b.png)

![image](https://user-images.githubusercontent.com/289513/40587392-1f47e50e-61d7-11e8-8413-23cb6a2dcad4.png)

![image](https://user-images.githubusercontent.com/289513/40587399-30bb62f2-61d7-11e8-8838-100803d7f2c2.png)

![image](https://user-images.githubusercontent.com/289513/40587402-36046aec-61d7-11e8-99ef-d79851c02a92.png)

![image](https://user-images.githubusercontent.com/289513/40587405-3b5bf762-61d7-11e8-910e-222e681d332e.png)


# Donation

If you find this repository helpful, you may make a donation to me via email.
![email]sercancelenk@gmail.com

# Contributors

This library is initially created and maintained by [sercancelenk](https://github.com/sercancelenk)

Special thanks to [Caner Kurtul]

# Change Log

Not yet.

# Contribute

If you would like to contribute code to Hazzmon-io you can do so through GitHub by forking the repository and sending a pull request.

# License

    Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
