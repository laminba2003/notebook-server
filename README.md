# notebook-server

Oracle Labs Spring Challenge

## Description
 
Interactive notebooks are experiencing a rise in popularity. Notebooks offer an environment for Data scientists to comfortably share research, collaborate with others and explore and visualize data. The data usually comes from executable code that can be written in the client (e.g. Python, SQL) and is sent to the server for execution. Popular notebook technologies which this approach are [Apache Zeppelin](https://zeppelin.apache.org/) and [Jupyter Notebooks](http://jupyter.org/).

## Requirements

* Java 8+
* Java IDE (Eclipse, IntelliJ IDEA, NetBeans..)
* Python installed on your computer

## Execution

* Import the project as Maven project
* Run the _com.oracle.notebook.Application_ class as Java Application

## Usage

with Postman, make a post request to the following endpoints with the code to be executed

#### http://localhost:8080/execute

```json
 {
  "code" : "%python print('hello world')"
 }
```

#### http://localhost:8080/execute/{sessionId}

```json
 {
  "code" : "%python print('hello world')"
 }
```