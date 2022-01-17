# Building RESTful and GraphQL web services


Banuprakash C

Full Stack Architect,

Co-founder Lucida Technologies Pvt Ltd.,

Corporate Trainer,

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/Spring_REST_GraphQL

https://notepad.ltd/egrnhgxh

====

Softwares Required:
1) Java 8

2) Eclipse IDE for Enterprise Java Developers: 
	https://www.eclipse.org/downloads/packages/release/2020-03/m1/eclipse-ide-enterprise-java-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -it local-mysql /bin/bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

============================

Spring and Spring Boot
JPA
RESTful Web services
GraphQL
Security

==============================

Spring and Spring Boot

Spring Framework for building enterprise applications
It provides a lightweight container [ Core Module of Spring helps mange lifecycle of objects and Dependency Injection]

UI ==> Service ==> DAO ===> Database

UI <-- service <-- DAO <-- database


EAI ==> Enterprise application Integration

=========================


Meta data ==> XML or Annotation

public interface ProductDao {
	void addProduct();
}


public class ProductDaoJdbcImpl implements ProductDao {
	...
}

public class ProductDaoMongoImpl implements ProductDao {
	...
}

public class MyService {
	ProductDao productDao;

	public void setDao(ProductDao dao) {
		this.productDao = dao;
	} 


	doTask() {
		this.productDao.addProduct();
	}
}

beans.xml
<bean id="jdbcImpl" class ="pkg.ProductDaoJdbcImpl" scope="prototype"/>
<bean id="mongoImpl" class ="pkg.ProductDaoMongoImpl" />

<bean id="service" class="pkg.MyService">
	<property name="dao" ref="jdbcImpl" />
</bean>


<bean id="service2" class="pkg.OtherService">
	<property name="dao" ref="jdbcImpl" />
</bean>



BeanFactory and ApplicationContext

ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

ctx.getBean("service"); // get bean from container


scope of bean can be "singleton", "prototype", "request" or "session"


==============================================================================



Annotation
----------

1) @Component
2) @Repository
3) @Service
4) @Configuration
5) @Controller
6) @RestController


Spring creates instances of class which has any of these at classlevel



public interface ProductDao {
	void addProduct();
}

https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml

@Repository
public class ProductDaoJdbcImpl implements ProductDao {    // productDaoJdbcImpl
	...
}


@Service
public class MyService {      // myService

	@Autowired
	ProductDao productDao;

 
	doTask() {
		this.productDao.addProduct();
	}
}


@Component("sample") // sample


try {

} catch(SQLException ex) {
	if(ex.getErrorCode() == 1062) {
		UniqueConstraint ...
	}
}

===

wiring beans is done using @Autowired or @Inject

Proxy Bytecode instrutementation ==> CGLib, ByteBuddy, JavaAssist

======================================================================================


Spring Boot
	==> Lots of configuration comes out of the box
	==> Highly opiniated framework / library on top of Spring Framework
		1) If we start building web applications Tomcat embedded webserver is configured
		2) If RDBMS is used ==> HikariCP database connection pool is created
		3) If ORM is used ==> Hibernate as JPA Vendor
	==> way you write web and standalone is same

Eclipse for JEE with STS eclipse plugin


New --> Spring Starter Project

else

Eclipse Marketplace ==> Search STS and install ==> Spring tools 4.3.9...


=======================

 
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
	}

}



SpringApplication.run() same as new AnnotationConfigApplicationContext ==> start a spring container



@SpringBootApplication
	1) @ComponentScan("com.example.demo")
		scans for above mentioned 6 annoatiponos in mentioned package and sub-package and creates instance of those classes

	2) @EnableAutoConfiguration
		==> Embeddedtomcat Container
		==> HikariCP if DB is used
		==> HibernateJPA VEndor if ORM is used

	3) @Configuration

==========
@Autowired
private EmployeeDao employeeDao;


@Autowired
public MyService(EmployeeDao dao) {

}


@Autowired
public void setX(EmployeeDao dao) {

}

=========================

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		
		MyService service = ctx.getBean("myService", MyService.class);
		service.doTask();
		
		System.out.println("********");
		
		String[] names = ctx.getBeanDefinitionNames();
		for(String name : names) {
			System.out.println(name);
		}
	}

}

========
To resolve multiple beans of a giver interface issue:

1) @Primary
2) @Qualifier

	Example:
	@Autowired
	@Qualifier("employeeDaoMongoImpl")
	private EmployeeDao employeeDao;

3) @Profile
	
	testing , development, 

	3.1) Command Line arguments
		--spring.profiles.active=dev
		--spring.profiles.active=prod

	Run As --> Run Configuration
		Program arguments

	3.2) system properties

	3.3) application.properties
			spring.profiles.active=prod


===============================

