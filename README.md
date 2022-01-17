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


4) 
application.properties
dao=db

@ConditionalOnProperty(name = "dao", havingValue = "mongo")


5) 
@ConditionalOnMissingBean("abean")

==========================================================
singleton, prototype, request and session

@Scope("request")

===========

Spring creates instances of bean using default constructor and having any of the above mentioned annotations

==> class doesn't have default constructor
==> class is provided by 3rd party library

How to make objects of these class managed by spring container?

Factory method


@Configuration
public class MyConfig {

	@Bean
	DataSource dataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
			
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		return cpds;
	}
}





=====================================================================


ODM ==> MongoDB

ORM ==> Object Relational Mapping
Library for persisting data into RDBMS
	layer on top of JDBC


@Configuration
public class MyConfig {

	@Bean
	DataSource dataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
			
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		return cpds;
	}

	@Bean
	EntityManagerFactory emf(DataSource ds) {
		LocalContainerEntityManagerFactory emf = new LCEMFB...();
		emf.setDataSource(ds);
		emf.setPackagesToScan("com.adobe.prj.entity"); // where are my @Entity class
		emf.setJpaVendor(new HibernateJpaVendor());
		...

		return emf;
	}
}

	

@Repository
public class ProductDaoJdbcImpl implements ProductDao {    // productDaoJdbcImpl
	@PersistenceContext
	EntityManager em;

	public void addProduct(Product p) {
		em.save(p);
	}

}


================================

Spring Data JPA simplifies using Spring and JPA ==> no need to create repository classes just create interfaces;
implementation classes are gernated by byte code instrumentation libraries like CGLib, JavaAssist and ByteByte

JPA ==> Java Persistence API ==> specification for ORM

=================================================


application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/ADOBE_JAVA_GRAPHQL?createDatabaseIfNotExist=true

spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

spring.datasource.username=root

spring.datasource.password=Welcome123


spring.jpa.hibernate.ddl-auto=update
==> update [ create tables if not exist; use if exists; alter if required]
==> verify [ check if mapping is done properly to existing tables; if proper use it; else throw exception]
==> create [ drop and create every time application executes ]

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


em.save(p); // generate SQL for MySQL8


======================
 
 OpenSessionInView Pattern ==> web application

 request ==> HibernateSession / EntityManager  / Connection is made available in "request"

 only once response is commited ==> connection is lost

 spring.jpa.open-in-view=true for OpenSessionInView pattern

 ==========

 int executeUpdate() ==> INSERT, DELETE and UPDATE

 ResultSet exceuteQuery() ==> SELECT

 ================================================

 Transactions

 Programmatic Transaction 
 using JDBC

 @Override
	public void addProduct(Product p) throws DaoException {
		String SQL = "INSERT INTO products (id, name, price, category) VALUES (0, ?, ?, ?)";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USER, PWD);
			con.setAutoCommit(false); // begin TX
				PreparedStatement ps = con.prepareStatement(SQL);
				ps.setString(1, p.getName());
				ps.setDouble(2, p.getPrice());
				ps.setString(3, p.getCategory());
				ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new DaoException("unable to add product", e);
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

===


 @Override
	public void addProduct(Product p) throws DaoException {
		
		
		try {
			Transaction tx = em.beginTransaction();// begin TX
				PreparedStatement ps = con.prepareStatement(SQL);
				ps.setString(1, p.getName());
				ps.setDouble(2, p.getPrice());
				ps.setString(3, p.getCategory());
				ps.executeUpdate();
			tx.commit();
		} catch (SQLException e) {
			tx.rollback();
			throw new DaoException("unable to add product", e);
		} finally {
			 
		}
	}
===

Declarative Transaction support is enabled by default

@EnableTransactionManagment is enabled by default


Atomic opertion:

@Transactional
public void updateProduct(int id, double price) {
		productDao.updateProductPrice(id, price);
}

when method is called Tx begins
when method completes Tx Commits
any Runtimeexception thrown inside method not handled tx rollback


======================

@Transactional by default is using Transaction.REQUIRED

@Transactional
m1() {
   m2();
   m3();
}

@Transactional(TxType.REQUIRES_NEW)
m2() {

}

@Transactional
m3() {

}

===============================================================


Mapping Associations
1) onetoone
2) onetomany
3) manytoone
4) manytomany


Without Cascade
class Order 
	@OneToMany
	@JoinColumn(name="order_fk")   // FK
	private List<Item> items = new ArrayList<>();

Assume 1 order has 3 items

to persist

em.save(order);
em.save(i1);
em.save(i2);
em.save(i3);


to delete

em.delete(order);
em.delete(i1);
em.delete(i2);
em.delete(i3);

OrderDao and ItemDao

==

With Cascade:
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="order_fk")   // FK
	private List<Item> items = new ArrayList<>();

We don;t need ItemDao; Just OrderDao is sufficient

to persist
em.save(order); ==> take care of persiting items also
to delete
em.delete(order) ==> deletes items of order also

================


Lazy fetching

@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name="order_fk")   // FK
private List<Item> items = new ArrayList<>();

orderDao.findById(20);

select orders record whose id is 20; items are not fetched

need to make a seperate call to get items;


orderDao.findAll(); ==> 40 orders

loop == thro 40 orders
itemDao.getByOrder(oid)

============

Eager Fetching

@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
@JoinColumn(name="order_fk")   // FK
private List<Item> items = new ArrayList<>();


getting orders will pull items belonging to order also ==> EAGER

===========

Default

ManyToOne is EAGER fetching

OneToMany is LAZY loading

======================

 Bi-directional

 public class Order {
	 @ManyToOne
	@JoinColumn(name="customer_fk")   // FK
	private Customer customer; // order belongs to a Customer
	

@Entity
@Table(name="customers")
public class Customer {
 	@OneToMany(mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();

===================


Many to many is rarity

Project and Employee looks like many-to-many 
Project can have many employees
Employee can work in many projects

projects
pid | name | client | start_date | end_date


employee
eid | name | start_date | end_date | role


employee_project
pid | eid


What if I need to place role of employee in project and duration in project
role | start_date and end_date


class EmployeeProject{
	..
	Date startDate;
	Date endDate;
	String role
}


employee_project
pid | eid | start_date| end_date | role

Employeee ---> EmployeeProject ---> Project

=================
ManyToMany
 
class Movie {
	movieID
	...

	@ManyToMany()
	@JoinTable(name ="movie_actor",
	joinColumns = @JoinColumn(name="mid"),
	inverseJoinColumns = @JoinColumn(name ="aid"))
	List<Actor> actors = ...
}


class Actor {
	actorId
	name;
	@ManyToMany(mappedBy = "actors")
	List<Movie> movies = ...
}

movie
movie_id  name

actors
actor_id name

movie_actor
mid | aid
