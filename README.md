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

===

client sends orders as

order should have "email" of customer
order has many items
	Each item
		qty
		product ==> id


=====

orderDao.getById(1); ==> returns Proxy Order

orderDao.findById(1).get() ==> actual Order

=

Items of Order ==> EAGER or LAZY

===========================================================


Day 2

spring and Spring boot
@Component, @Respository, @Service, @Configuration, @Bean and @Autowired

@Profile, @Primary, @Qualifier, @ConditionalOnBean, @ConditionalOnMissingBean, @ConditionalOnProperty

application.properties

application_dev.properties ==> for "dev" profile
application_prod.properties for "prod" profile

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@Configuration

CommandLineRunner


ORM and JPA
PersistenceContext , EntityManager, EntityManagerFactory [ DataSource, JPAVendor Provider]

Spring Data JPA provides interfaces like CrudRepository, PageAndSortingRespository, JpaRepository

interface ProductDao extends JpaRespostory<Product, Integer> {

}

@Query
@Modifying

OneToMany
ManyToMany
ManyToMany
OneToOne

Cascade and Fetch.EAGER and Fetch.LAZY

========================

Day 2

1) @Query("from Employee") ==> JPQL

2) @Query(value="select * from employees", nativeQuery=true) ==> SQL valid


3) Criteria API ==> OOP way of building queuries


Criteria cr = session.createCriteria(Employee.class);
cr.add(Restrictions.eq("salary", 2000)); // Where clause
List results = cr.list();

4) JPA Specification ==> DDD

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
 …
}

class CustomerSpecification implements Specification {
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

	}
}

specificationExample.zip

======

entityGraphExample.zip


javax.persistence.fetchgraph


javax.persistence.loadgraph


The FetchType method defines two strategies for fetching data from the database:

javax.persistence.fetchgraph – Only the specified attributes are retrieved from the database.  
companyWithDepartmentsGraph => loads only company and "departments"; other FetchType is ignored

javax.persistence.loadgraph – In addition to the specified attributes, attributes statically configured as EAGER are also retrieved.

companyWithDepartmentsGraph ==> loads  company and "departments"; and also other associations which are EAGER.


===========================================

"companyWithDepartmentsGraph"

 select
        company0_.id as id1_2_0_,
        company0_.name as name2_2_0_,
        department1_.company_id as company_3_3_1_,
        department1_.id as id1_3_1_,
        department1_.id as id1_3_2_,
        department1_.company_id as company_3_3_2_,
        department1_.name as name2_3_2_ 
    from
        company company0_ 
    left outer join
        department department1_ 
            on company0_.id=department1_.company_id 
    where
        company0_.id=?


"companyWithDepartmentsAndEmployeesAndOfficesGraph"

     select
        company0_.id as id1_2_0_,
        company0_.name as name2_2_0_,
        department1_.company_id as company_3_3_1_,
        department1_.id as id1_3_1_,
        department1_.id as id1_3_2_,
        department1_.company_id as company_3_3_2_,
        department1_.name as name2_3_2_,
        employees2_.department_id as departme5_4_3_,
        employees2_.id as id1_4_3_,
        employees2_.id as id1_4_4_,
        employees2_.address_id as address_4_4_4_,
        employees2_.department_id as departme5_4_4_,
        employees2_.name as name2_4_4_,
        employees2_.surname as surname3_4_4_,
        offices3_.department_id as departme4_5_5_,
        offices3_.id as id1_5_5_,
        offices3_.id as id1_5_6_,
        offices3_.address_id as address_3_5_6_,
        offices3_.department_id as departme4_5_6_,
        offices3_.name as name2_5_6_ 
    from
        company company0_ 
    left outer join
        department department1_ 
            on company0_.id=department1_.company_id 
    left outer join
        employee employees2_ 
            on department1_.id=employees2_.department_id 
    left outer join
        office offices3_ 
            on department1_.id=offices3_.department_id 
    where
        company0_.id=?

=========================================================


"companyWithDepartmentsAndEmployeesGraph"
	select * from company c join on c.departments d join on d.employees

	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
	    private Set<Department> departments = new HashSet<>();

======================================================================================

Building RESTful Web services using Spring Boot

Spring Core
Spring ORM Module
Spring MVC module


REpresentational State Transfer

Resource ==> present on server [ database , file , printer ]

State of resource is Representation

State can be served in various formats [ xml, json, csv, ...] to clients 


SOAP is a protocol

REST and GraphQL are architectural pattern used over HTTP

1) Client - server 
	
2) Uniform URL
	

Plural nouns to identify the resources

http://server/api/products
http://server/api/orders
http://server/api/customers



HTTP methods to perform action ==> GET, POST, PUT and DELETE

CREATE ==> POST
READ ==> GET
UPDATE ==> PUT / PATCH
DELETE ==> DELETE

1) 
GET
http://localhost:8080/api/products
get all products

2) 

GET
http://localhost:8080/api/products/4
get product whose id is "4"

GET
http://localhost:8080/api/customers/anil@adobe.com/order
get orders of customer whose email is "anil@adobe.com"

PathParameter

3)
GET
http://localhost:8080/api/products?page=4&size=20
http://localhost:8080/api/products?lower=500&high=50000
to get subset [ filters]

QueryParameter ==> RequestParameter

4) 
POST
http://localhost:8080/api/products

payload contains new "product" data to be added to "products"


5)

PUT
http://localhost:8080/api/products/4

payload contains new "product" data to be modified in "products" where id is "4"



6)
DELETE

http://localhost:8080/api/products/4

delete a product whose id is '4'


============================================

GET and DELETE are IDEMPOTENT

POST and PUT are not IDEMPOTENT

=================================================


@Controller ==> for traditional web application development where methods return "views" ==> html / pdf/ image
	==> Server Side rendering

@RestController ==> representation of data in different formats to client; client will do "client - side redenring"
		Mobile Client, Web client , Stand alone



@RestController
@RequestMapping("api/employees")
public class EmployeeController {

	@GetMapping()
	m1() {

	}

	@PostMapping()
	m2() {

	}
}	



<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
</dependency>

Spring MVC module
Java <--> JSON HttpMessageConvertor is made availble using Jackson library
	1) jackson
	2) jettison
	3) GSON
	4) Moxy

=============

download POSTMAN

====================

POST http://localhost:8080/api/products

Headers
Accept : application/json
content-type: application/json


Body "raw"

{
    "name": "Pen",
    "price": 150.0,
    "quantity": 100
}


=======================================

PUT http://localhost:8080/api/products/4

Headers

Body "raw"

{
   "price": 34999.00
}


=====

POST http://localhost:8080/api/orders

Body:

{
	"customer" : {
		"email" : "peter@adobe.com"
	},
	"items": [
		{"qty" : 3, "product" : {"id": 2}},
		{"qty" : 1, "product" : {"id": 1}},
		{"qty" : 2, "product" : {"id": 3}}
	]
}


===

GET 
http://localhost:8080/api/orders


==========================================================================

AOP ==> Aspect Oriented Programming
 Cross cutting concerns which leads to code tangling and code scattering

* Aspect ==> a bit of code which is not part of main logic; but can be used along with main logic
 	Examples: Logging, Security, Profile, Transaction

 Tangling:
 	public void transferFunds(...) {
 		profile.start();
 		log.info("transfering funds");
 		if(ctx.getPrinciple().getRole("MANAGER")) {
 			Transaction tx = ...
 				withdraw(amt);
 				log.info("amount withdraw done")
 				deposit(amt);
 				log.info("amount deposit done")
 			tx.commit();
 		}
 		log.info("transfer completed")
 		profile.end(); // time to exceute the method
 	}

class TransactionAspect{

}

class LogAspect {

}

class ProfileAspect {

}

* JoinPoint

is a place in your code where aspect can be "weaved"

==> supports methods and exception as JoinPoint

* PointCut
	is selected JoinPoint

* Advice
	Before, After, AfterReturning, Around, AfterThrowing


====================================

execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)
          throws-pattern?)


@Transactional

applyTransaction(ProceedingJoinPoint jp) {
	try {
		Tx begin
		jp.proceed();
		Tx commit
	} catch(...) {
		tx.rollback();
	}
}

 
		
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>
		


-====

Spring AOP doesn't support Dynamic Pointcuts

=======================================================

https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html

==============================================

@ControllerAdvice 

Built-in Advice in Spring MVC to catch exceptions propgated from @Controller or @RestController




 MethodArgumentNotValidException: 

 Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.adobe.demo.entity.Product> com.adobe.demo.api.ProductController.addProduct(com.adobe.demo.entity.Product) with 2 errors: 
 	[Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; default message [name]]; default message [Name is required]] 

 	[Field error in object 'product' on field 'price': rejected value [-150.0]; codes [Min.product.price,Min.price,Min.double,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.price,price]; arguments []; default message [price],0]; default message [Price -150.0 should be more than 0]] ]


========

Changes:

OrderService ==> getProduct

Product.java ==> added validation rules

ProductController addProduct(... @Valid )

GlobalExceptionHandler

=======================================================================


Testing:
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
</dependency>

JUnit ==> Unit testing Framework / TestNG

Hamcrest ==> matchers for assertions

Json-path ==> to test json returned value

Mockito ==> Mocking library ==> EasyMock / JMock

=============


Document REST APis

RAML ==> yml

uri
	path:
	 method: get
	 	arguments:



OpenAPI / Swagger
 Api way of documentation







http://localhost:8080/v2/api-docs

http://localhost:8080/swagger-ui.html

@Api(value = "my product controller")
@ApiOperation(value = "get all products")

===============

GraphQL

like REST its architectural pattern


RESTful web services uses specs provided by Richardson
plural nouns
CRUD ==> GET, POST, PUT and DELETE


GraphQL ==> Facebook ==> 2012 ==> 2015

===========
==> Overfetching
https://jsonplaceholder.typicode.com/posts


n + 1 problem
==> underfetching
https://jsonplaceholder.typicode.com/comments?postId=1
https://jsonplaceholder.typicode.com/comments?ppostId=2
https://jsonplaceholder.typicode.com/comments?ppostId=3
https://jsonplaceholder.typicode.com/comments?ppostId=4


POST
http://localhost:8080/graphql

GraphQL ==> Query Language for fetching data over HTTP

GraphQL SDL ==> Schema Definition Language

schema {
	query: Query,
	mutation : Mutation,
	subscription: Subscription
}


Query is a special type to fetch data [ SELECT, GET]
Mutation is a special type to mutate the data [ INSERT, DeLETE or UPDATE]
Subscription is a type of subscribe for any change to a data where I need notification
		StockPrice

=====

User defined types

type Product {
	id:Int!,
	name: String,
	price: Float
}

! not null

* Scalar type
 ID, Int, String, Float, Boolean


* Object type

type Product {
	id:Int!,
	name: String,
	price: Float,
	supplier: Supplier
}


type Supplier {
	id: ID,
	name: String
}

* collection

type Order {

	items: [Item]
}

type item {
	id:Int
	product: Product,
	qty:Int
	amount:Float
}


Schema First approach .graphql or .graphqls ==> shared between parties


type Query {
	products: [Product]
	productById(id:Int) : Product
}

Resolver / DataFetchers

List<Product> products() {  // getProducts()
	jpa ...
}

Product productById(int id) {
	jpa ...
}


client:

query {
	products {
		name,
		price
	}
}


--

query {
	products {
		id,
		name,
		supplier {
			name
		}
	}
}

mutation {
	createProduct(,,,,) {
		id
	}
}

=====================================

graphql-java API
graphQL-java-kickstart API ==> integrate with Spring boot

https://howtodoinjava.com/lombok/lombok-eclipse-installation-examples/

=======================================================

Day 3

GraphQL ==> Query Language 

GraphQL SDL ==>

Query, Mutation and Subscription are special types

1) Schema first approach
2) code first approach

type TypeName {
	fieldName: fieldType
}

Scalar Built-in types: ID, Int, Float, Boolean, String

enum, Object, collection

type Product {
	supplier:Supplier
}

type Supplier {
	products: [Product]
}

schema {
	query: Query,
	mutation : Mutation,
	subscription: Subscription
}


query {
	products {
		id,
		name,
		supplier {
			name
		}
	}
}

mutation {
	createProduct(,,,,) {
		id
	}
}

============================================




schema.graphqls
type Query {
	helloWorld:String!
}

QueryResolver

@Component
public class HelloWorldQueryResolver implements GraphQLQueryResolver {
//	public String getHelloWorld() {
	public String helloWorld() {
		return "Hello World from GraphQL!!!";
	}
}

http://localhost:8080/graphiql

query {
  helloWorld
}

Response:

{
  "data": {
    "helloWorld": "Hello World from GraphQL!!!"
  }
}

===
type Query {
	helloWorld:String!
	greeting(firstName:String!, lastName:String): String!
}

public String getGreeting(String firstName, String lastName) {
		return String.format("Hello %s %s", firstName, lastName);
	}


query {
  greeting(firstName:"Banu", lastName : "Prakash")
}


Response:
{
  "data": {
    "greeting": "Hello Banu Prakash"
  }
}

====
type Query {
	helloWorld:String!
	greeting(firstName:String!, lastName:String): String!
	books:[Book]
}

# Book type

type Book {
 id:Int,
 title:String!,
 totalPages:Int,
 rating:Float,
 isbn:String
}


====

@Component
public class BookQueryResolver implements GraphQLQueryResolver {
	@Autowired
	private BookDao bookDao;
	
	public List<Book> getBooks() {
		return bookDao.findAll();
	}
}

===


query {
  books {
    id
    title
    rating
    isbn
  }
}

=======

http://localhost:8080/graphiql
http://localhost:8080/playground
http://localhost:8080/voyager

====
type Query {
	helloWorld:String!
	greeting(firstName:String!, lastName:String): String!
	books:[Book]
	bookById(id:Int):Book
}

Resolver method:
public Book getBookById(int id) {
		return bookDao.findById(id).get();
}


query {
   bookById(id:5) {
    title
  }
}


====

POST localhost:8080/graphql


curl --location --request POST 'localhost:8080/graphql' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"query {\r\n    helloWorld\r\n}","variables":{}}'

===================

Operation Name:

query FETCHBOOK_BYID {
   bookById(id:5) {
    title
  }
}

query GET_BOOKS  {
  books {
    title
    rating
  }
}

=========

Query Variables:

query FETCHBOOK_BYID($bid:Int) {
   bookById(id:$bid) {
    title
  }
}


Query Variable:

{
  "bid": 4
}

=======================

GraphQLHttpServlet
AbstractGraphQLHttpServlet
DataFetchingEnvironment
ExecutionResult

==================

Unit Testing GraphQL Resolvers

<dependency>
			<groupId>com.graphql-java-kickstart</groupId>
			<artifactId>graphql-spring-boot-starter-test</artifactId>
			<version>11.1.0</version>
			<scope>test</scope>
</dependency>


graphql.servlet.mapping=/graphql
graphql.servlet.enabled=true
graphql.servlet.corsEnabled=true
# GraphiQL
graphiql.mapping=/graphiql
graphiql.endpoint=/graphql
graphiql.enabled=true
graphiql.cdn.enabled=true
graphiql.cdn.version=0.11.11


======================
Field Resolvers:

@component
class BookFieldQueryResolver implements GraphQLResolver<Book> {

	double getRating() {
		return 9.9;
	}

	int totalPages() {
		return 100;
	}
}

======================


QueryREsolver:

@Component
public class BookQueryResolver implements GraphQLQueryResolver {


FieldResolver:

@Component
public class BookFieldQueryResolver implements GraphQLResolver<Book> {


}

===

changes:
1) schema.graphqls
2) Publihser.java
3) PublisherDao.java
3) Book.java ==> Association added
4) BookFieldQueryResolver.java

===================

query {
  publishers {
    name
    books {
      title
     }
  }
}


changes:
1) schema.graphqls
2) BookDao.java
3) publisher.java
4) Publisher resolvers


=============

Custom Scalar Type ==> Date

Book.java
@Column(name="published_date")
private Date publishedDate;


schema.graphqls

scalar Date


type Book {
 ...
 publishedDate: Date,
 ...
}


=============================


--> GraphQLQueryResolver ==> handle Query
--> GraphQLResolver<T> ==> handle DataFetcher on fields of a Type

Custom Scalar types

Bean with GraphQLScalarType bean having coercing implementation [ serialiaze, parseValue and parseLiteral]

===================

ExtendedScalars

scalar PositiveIntScalar;

type Product {
	id:PositiveIntScalar
}


@Bean
public GraphQLScalarType positiveIntScalar() {
	return ExtendedScalars.PositiveIntScalar;
}

=======================================================================

* Directives

Built-in directives
1) @skip
2) @include
3) @deprecated
4) @specifedBy ==> URL


directive @deprecated on FIELD_DEFINITION

totalPages:Int  @deprecated(reason:"prefer using pages"),

client gets deprecated warning

==

query GET_BOOKS($admin:Boolean!) {
  books {
     title
     rating @include(if : $admin)
     totalPages
  }
}

Query Variable
{
  "admin": false
}


==================================

Custom Directive
@uppercase

===================================


schema {
	query: Query,
	mutation: Mutation
}


==============================



type Mutation {
	createAuthor(author:AuthorInput): Int
}

# AuthorDTO
input AuthorInput {
	firstName:String,
	lastName:String,
	middleName:String
}

type Author {
	id:Int,
	firstName:String,
	lastName:String,
	middleName:String
}

@Component
public class AuthorMutationResolver implements GraphQLMutationResolver {
	@Autowired
	private AuthorDao authorDao;
	
	public Integer createAuthor(Author author) {
			return authorDao.save(author).getId();
	}
}

=====================================

Validation
Exception Handling

query {
   bookById(id:5999) {
    title
    rating
    publishedDate
  }
}


ExecutionResult:

{
  "errors": [
    {
      "message": "No value present",
      "locations": [
        {
          "line": 2,
          "column": 4
        }
      ],
      "path": [
        "bookById"
      ],
      "extensions": {
        "type": "NoSuchElementException",
        "classification": "DataFetchingException"
      }
    }
  ],
  "data": {
    "bookById": null
  }
}

==========================================

public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {
	List<GraphQLError> processErrors( list of GraphQLError) {

	}
}


=============================================================


Asynchronous resolvers

	Company
		departments
		employees


	Thread ==> Request

	CompanyFieldResolver implements GraphQLResolver<Company> {

		List<Department> getDepartment() {
			DAO call or API call
		}

		List<Employee> getEmployees() {
			DAO call or API call
		}
	}

Asynchronous resolvers returns Promise  / Future / CompletableFuture CompleationStage



===============

DataFetcherResult ==> Partial Data

=============

* Pagination
1) offset based

books(start:Int, size:Int) :[Book]

DAO 

Pageable page = PageRequest.of(start, size);

bookDao.findAll(page).getContent();

2) Facebook Relay [ https://relay.dev/graphql/connections.htm#sec-Connection-Types]
   Cursor based
   GraphQL Cursor Connections Specification

   edges {
        cursor
        node {
          id
          name
        }
      }
      pageInfo {
        hasNextPage
        hasPreviosPage
      }


    a) pageInfo
    b) edge
    		1) cursor ==> cursor value is stored in DatafetchingEnvironment
    		2) node
    				type


====

partialInfoById(id:Int):Book 
	booksByPage(first:Int, after:String) : BookConnection
}

type BookConnection {
	edges: [BookEdge]
	pageInfo: PageInfo
}

type BookEdge {
  cursor:String,
  node:Book
}

type PageInfo {
 hasPreviousPage:Boolean!
 hasNextPage:Boolean!
}


===========================


query {
  booksByPage(first:5) {
    edges {
      cursor
      node {
        title
        rating
      }
    }
    pageInfo {
      hasPreviousPage
      hasNextPage
    }
  }
}
==

{
  "data": {
    "booksByPage": {
      "edges": [
        {
          "cursor": "c2ltcGxlLWN1cnNvcjA=",
          "node": {
            "title": "LEAN SOFTWARE DEVELOPMENT: AN AGILE TOOLKIT",
            "rating": 4.17
          }
        },
        {
          "cursor": "c2ltcGxlLWN1cnNvcjE=",
          "node": {
            "title": "FACING THE INTELLIGENCE EXPLOSION",
            "rating": 3.87
          }
        },
        {
          "cursor": "c2ltcGxlLWN1cnNvcjI=",
          "node": {
            "title": "SCALA IN ACTION",
            "rating": 3.74
          }
        },
        {
          "cursor": "c2ltcGxlLWN1cnNvcjM=",
          "node": {
            "title": "PATTERNS OF SOFTWARE: TALES FROM THE SOFTWARE COMMUNITY",
            "rating": 3.84
          }
        },
        {
          "cursor": "c2ltcGxlLWN1cnNvcjQ=",
          "node": {
            "title": "ANATOMY OF LISP",
            "rating": 4.43
          }
        }
      ],
      "pageInfo": {
        "hasPreviousPage": false,
        "hasNextPage": true
      }
    }
  }
}


====

query {
  booksByPage(first:5, after: "c2ltcGxlLWN1cnNvcjI=") {
    edges {
      cursor 
      node {
        title
        rating
      }
    }
    pageInfo {
      hasPreviousPage
      hasNextPage
    }
  }
}


================================================


Day 4

GraphQL Schemas are files in classpath "resources" folder with an extension of ".graphql" or ".graphqls"

schema ==> Query, Mutation and Subscription

Scalar Type 
Custom Scalar Types ==> Coercing
ExtendedScalar ==> @Bean 
Directive ==> SchemaDirectiveWiring

GraphQLQueryResolver and GraphQLResolver<T> [ field resolver]

GraphQLMutationResolver

javax.validation.constraints for Input validations

GraphQLError ==> exception implements GraphQLError

Async Resolvers ==> Good to use in GraphQLResolver<T> in scenarios wherein a type has "N" number of associations
	, then each associated data has to fetched from differnt Services/database
	Pool of threads ==> use them for each of the assocaiton to be fetched ==> CompletableFuture
	max of "N" or "M" time to return the data

	MakeMyTrip aggregator

	List<Hotel> hotels() ==> CompletableFuture<List<Hotel>>

	List<Flight> fights() CompletableFuture<List<Flight>>


DataFetchingEnvironment

Relay Connection Specification ==> Facebook for Pagination

Pagination ==> offset based ==> Pageable page = PageRequest.of(start, count);

	1 2  3 4 5 6 7 8 9 10

	"3" records from backend


Relay Connection Specification ==> data is fetched into GraphQLServer store it in the form of Graph data container and for each node
provide a "Cursor"

Backward pagination

booksByPage(first:Int, last:Int, after:String, before:String) : BookConnection


public Connection<Book> booksByPage(int first, String after, int last, String before, DataFetchingEnvironment env) {
		return new SimpleListConnection<>(bookDao.findAll()).get(env);
	}

query {
  booksByPage(last:5, before :"c2ltcGxlLWN1cnNvcjM=") {
    edges {
      cursor
      node {
        title
        rating
      }
    }
    pageInfo {
      hasPreviousPage
      hasNextPage
    }
  }
}

React --> relay client

Apollo Client ==> builds on top this

=================

Type Definition Factory

	booksByPage(first:Int, last:Int, after:String, before:String) : BookConnection @connection(for:"Book")
}

# type BookConnection {
#	edges: [BookEdge]
#	pageInfo: PageInfo
# }

# type BookEdge {
#  cursor:String,
#  node:Book
# }

# type PageInfo {
# hasPreviousPage:Boolean!
# hasNextPage:Boolean!
# }

======================

GraphQL Client Fragment:

query {
  books {
   ...BookInfo
  }
}


fragment BookInfo on Book {
    title
    rating
    totalPages
}


==================

Union type

type Product {
	id:Int,
	name:String,
	price:Float
}

type Tv {
    id:Int,
	name:String,
	price:Float,
	screenType:String
}


type Mobile {
    id:Int,
	name:String,
	price:Float,
	connectivity:String
}

union ProductType = Product | Tv | Mobile

extend type Query {
 products:[ProductType],
}


========


@Bean
public SchemaParserDictionary getSchemaParserDictionary() {
		return new SchemaParserDictionary().add(Mobile.class).add(Tv.class).add(Product.class);
}

=========

query {
  products {
    __typename
    
    ... on Mobile {
      name
      connectivity
    }
    
    ... on Tv {
      name
      price
      screenType
    }
  }
}


===============================================

Query, Mutation and Subscription

Subscription:

type Subscription {
 authors:Author!
}

type Author {
	id: Int,
	firstName: String,
	lastName: String,
	middleName :String
}



===

Observer:

subscription {
  authors {
     firstName
     lastName
  }
}

====

DashBoard has to be updated whenever StoreInsights, CustomerInsight, StockPrice

============================



































