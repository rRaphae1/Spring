# Spring框架学习

Spring两个核心部分：IOC与AOP

IOC：控制反转，创建对象交由Spring进行管理

AOP：面向切面编程，不修改代码而完成功能增强

## 一. IOC容器

### 1.IOC的原理

#### (1).IOC底层原理

译名：控制反转，降低耦合度用的。包括两部分**对象创建和对象之间的调用**。

IOC底层原理使用了**xml解析、工厂设计模式以及反射**，直接使用Dao层进行对象创建会有**很高的耦合度**，而在中间使用工厂模式来进行对象创建并返回就可以降低Dao层与Service之间的耦合度。**但是工厂还是有耦合度，单纯只是降低耦合度，而非完全消除耦合度**。

#### (2).IOC过程

第一步 xml配置文件，配置创建的对象。

```xml
<bean id=" " class=" "></bean>
```

第二步 service类与dao类，创建工厂类

``` java
class UserFactory{
    public static UserDao getDao(){
        String classValue=class//这个属性值;
        Class clazz=Class.forName(classValue);
        return (UserDao)clazz.newInstance();
    }
}
```

#### (3).IOC接口

IOC思想基于**IOC容器完成，IOC容器底层就是对象工厂**，Spring提供的IOC容器实现方式：**BeanFactory**，以及**ApplicationContext**，前者在Spring自己的源码中用，一般不提供给开发用，后者供开发人员使用，后者是前者的子接口。

BeanFactory是加载配置文件时不创建对象，而在获取或者使用时才创建，所谓的**懒加载**，而ApplicationContext是在加载配置文件时直接把配置文件中的对象创建，有多少创建多少。

在实际中，用**ApplicaitonContext在实际中用好点，因为Spring是web框架，在服务器启动时创建对象，而不是使用时创建，就能提高性能**。

ApplicationContext的接口实现类有**ClassPathXmlApplicationContext**与**FileSystemXmlApplicationContext**两种。

**ClassPathXmlApplicationContext**就直接把在src下的相对路径写出来就可以创建对象。

**FileSystemXmlApplicationContext**要填写xml文件的绝对路径，从根目录开始。

### 2.Bean管理（XML方式）

**Bean管理=Spring创建对象+Spring注入属性**，注入属性是指向数据成员中设值。

#### (1).两种Bean

##### 	(I) FactoryBean

spring配置文件中定义的Bean类型与返回的类型可以不一样。

第一步：创建一个类，让这个类作为工厂Bean。

第二步：实现接口的方法，方法中可以定义返回Bean的类型，这个类实现FactoryBean，并实现getObject、getObjectType、isSingleton，返回的类型在这个类中做定义。

##### 	(II) 普通Bean

spring配置文件中定义的Bean类型就是返回的类型

#### (2).Bean作用域

spring中设置创建Bean实例是单实例还是多实例。

Spring中，默认情况下，创建Bean实例是单实例对象。即对象创建多次，地址相同，即为单实例对象。

##### 设置多实例对象

使用spring配置文件bean标签中scope属性来设置是单实例还是多实例。

scope：默认singleton，单实例，prototype，多实例对象。

singleton与prototype区别：表示实例对象不同；如果是singleton，加载Spring配置文件就创建单实例对象，如果是prototype，不是在加载Spring配置文件创建实例对象，而是在getBean获取实例时，创建多实例对象

#### (3).Bean生命周期

**生命周期：**从对象创建到销毁的过程。

##### **Bean生命周期**

（1）构造器创造Bean实例（无参构造）。

（2）为Bean属性设置值以及引用其他Bean（调用set方法）。

（3）调用Bean的初始化方法（需进行配置）

在类中定义一个初始化执行的方法。

Spring配置文件中：

```xml
<bean id=" " class=" " init-method=initMethod_name >

</bean>
```

（4）使用Bean（Bean实例已经获取）

（5）关闭容器时，Bean销毁方法（需配置销毁方法）

在类中定义一个销毁的方法。

Spring配置文件中：

```xml
<bean id=" " class=" " init-method=initMethod_name destory-method=DestoryMethod_name >

</bean>
```

调用ApplicationContext的close方法，而这个方法是ClassPathXmlApplicaitonContext这个子接口的方法，因此会自动出现一个强制类型转换。

##### Bean的后置处理器，此时生命周期有7步

（1）构造器创造Bean实例（无参构造）。

（2）为Bean属性设置值以及引用其他Bean（调用set方法）。

初始化前：把Bean实例传递给Bean后置处理器的方法postProcessBeforeInitialization。

（3）调用Bean的初始化方法（需进行配置）

初始化后：把Bean实例传递给Bean后置处理器的方法postProcessAfterInitialization。

（4）使用Bean（Bean实例已经获取）

（5）关闭容器时，Bean销毁方法（需配置销毁方法）

**后置处理器**

I. 创建类方法，实现BeanPostProcessor，创建类后置处理器。

II. Spring配置文件中配置后置处理器这个Bean。后置处理器会对当前配置文件中的所有Bean都添加后置处理器的处理。

#### (4).基于xml配置文件实现

在Spring的配置文件中使用Bean标签，在里面添加相应属性就可实现对象创建。

**id属性是对象的标识，name属性和id一样效果**

**class属性是类的包类路径**

创建对象时，默认使用了无参构造，如果定义了有参构造，但是没有显示声明无参构造，会报错。

---

##### xml注入属性

>**DI（Dependence Insert）：依赖注入，就是注入属性，DI是IOC的一种实现方式，是一种具体体现。**
>
>第一种方式是使用set方法注入。
>
>*创建相应的set方法*
>
>**直接alt+enter就有选项，或者alt+Ins**
>
>*在Spring配置文件中配置对象以及注入属性，使用property标签*
>
>```xml
><bean >
>	<property name="属性名称，在类里面定义的" value="要注入的值"></property>
></bean>
>```
>
>**_此种方法是对属性进行了字符串的拼接，利用反射调用了Set方法，然后返回一个单例对象，好像是这样_** 
>
>*如果没有set方法会无法注入*
>
>---
>
>第二种方式是使用有参构造注入。
>
>*定义有参数的构造方法*
>
>*在Spring的配置文件中进行配置，（刚写时，因为没写完，还在检查无参构造来创建，但是没有无参构造时，会报错，写完就好了）对于属性，使用**constructor-arg**标签进行属性设置。*
>
>```xml
><constructor-arg name=property_name value=property_value></constructor-arg>
><constructor-arg index=property_index value=property_value></constructor-arg>
>```
>
>***
>
>**p名称空间注入**
>
>在配置文件中添加p名称空间：
>
>```xml
>xmlns:p="http://www.springframework.org/schema/p"
>```
>
>bean标签中属性注入：
>
>```xml
><bean id=" " class=" " p:indexName_1:" " p:indexName_2:" "></bean>
>```
>
>***
>
>**xml注入其他类型属性**
>
>**字面量**：属性设定固定值，可能遇到空值或者特殊符号的情况
>
>```xml
><!--空值-->
><property name=property_name> 
>	</null>
></property>
>```
>
>```xml
><!--属性值中包含特殊符号-->
><!--1.可以转义-->
><!--2.用CDATA-->
><property name=property_name>
>	<value>
>    	<![CDATA[property_value]]>
>    </value>
></property>
>```
>
>***
>
>**外部Bean**
>
>Service调用Dao，该过程叫做引入外部Bean。
>
>1. 定义Dao接口，Daoimpl类实现Dao
>
>2. spring配置文件中进行配置
>
>在service中创建Dao属性，并生成set方法。
>
>配置文件中进行配置
>
>```xml
><!--创建Service对象-->
><bean id="xxService" class=" ">
>	<property name="xxDao" ref="xxDaoImpl"></property>
></bean>
><!--创建Dao对象-->
><bean id="xxDaoImpl" class=" "></bean>
>```
>
>外部Bean用到ref属性
>
>***
>
>**内部Bean**
>
>（1）一对多关系
>
>（2）在实体类中表示一对多关系，一个类中有另一个类的对象属性。
>
>（3）配置文件完成配置
>
>```xml
><bean id=Object_name class=" ">
>	<!--普通属性-->
>    <property name=property_name value=property_value></property>
>    <!--对象类型属性-->
>    <property name=ObjectProperty_name>
>        <!--下面的bean的id要与定义的类对象属性的对象名一致-->
>    	<bean id=Object_name class=" ">
>        	<property name=Obeject_Property value=property_value></property>
>        </bean>
>    </property>
></bean>
>```
>
>***
>
>**级联赋值**
>
>类对象属性设置同时向其中类对象的各个属性进行设置。
>
>（1）第一种写法
>
>```xml
><bean id=Object_name class=" ">
>	<!--普通属性-->
>    <property name=property_name value=property_value></property>
>     <!--级联赋值-->
>    <property name=ObjectProperty_name ref=bean_id></property>
></bean>
>
><bean id=bean_id class=" ">
>	<property name=Property_name value=Property_value></property>
></bean>
>```
>
>（2）第二种写法
>
>```xml
><bean id=Object_name class=" ">
>	<!--普通属性-->
>    <property name=property_name value=property_value></property>
>     <!--级联赋值-->
>    <property name=ObjectProperty_name ref=bean_id></property>
>    <!--下面这种方法中需要先生成类对象属性的get方法-->
>    <property name=ObjectProperty_name.property_name value=property_value></property>
></bean>
>
><bean id=bean_id class=" "></bean>
>```

***

##### **xml注入集合属性**

> （1）创建类，定义数组、list、map、set类型属性，并定义set方法
>
> （2）配置文件中进行配置
>
> ```xml
><bean id=" " class=" ">
> 	<!--数组-->
>  <property name=array_name>
>  	<array>
>         	<value></value>
>             <value></value>
>             	.......
>         </array>
>     </property>
>     <!--list-->
>     <property name=list_name>
>     	<list>
>         	<value></value>
>             <value></value>
>             	.......
>         </list>
>     </property>
>     <!--map-->
>     <property name=map_name>
>     	<map>
>         	<entry key=" " value=" "></entry>
>             <entry key=" " value=" "></entry>
>                 .......
>         </map>
>     </property>
>     <!--set-->
>       <property name=set_name>
>     	<set>
>         	<value></value>
>             <value></value>
>             	.......
>         </set>
>     </property>
>    </bean>
>    ```
> 
> ***
>
> **集合中设置对象属性**
>
> （1）类以及其中属性的set方法
>
> （2）一个对象属性集合，如一个list
>
> 配置文件中：
>
> ```xml
><bean id=" " class=" ">
> 	<property name=Property_name>
>  	<list>
>      	<ref bean=id1></ref>
>             <ref bean=id2></ref>
>             	........
>             <ref bean=idn></ref>
>         </list>
>     </property>
>    </bean>
>    <bean id=id1 class=" ">
> 	<property name=property_name value=property_value></property>
> </bean>
> <bean id=id2 class=" ">
> 	<property name=property_name value=property_value></property>
> </bean>
> ......
> <bean id=idn class=" ">
> 	<property name=property_name value=property_value></property>
> </bean>
> ```
> 
> ***
>
> **集合提取出来作为公共部分**
>
> （1）在spring配置文件中引入一个util名称空间
>
> ```xml
><!--增加-->
> xmlns:util="http://www.springframework.org/schema/util"
> <!--修改-->
> xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
> <!--修改为-->
>  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
> http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd"
>  ```
> 
> （2）使用util标签完成集合注入提取
>
> ``` xml
><!--以list-->为例
> <util:list id=list_id>
>  <value></value>
>  <value></value>
>     <value></value>
>     	......
>    </util:list>
>    
> <bean id=" " class=" ">
> 	<property name=property_name ref=list_id></property>
> </bean>
> ```

#### (5). xml自动装配

根据指定的装配规则（属性名称或属性类型），Spring自动将匹配的属性值进行注入。

创建两个类，一个类的成员是另一个类的对象。

在Spring配置文件中创建两个类的Bean，在bean标签中属性autowire，byName（基于名称）或者byType（基于类型）

byName：注入bean的id要与类属性名称一致。

```xml
<bean id="xxid2" class=" " autowire="byType"></bean>

<bean id="xxid1" class=" "></bean>
```

使用byType有一个问题，如果使用一个类创建两个Bean，就不知道用的哪一个Bean了，会报错，这种只有用byName。

####  <span id = "datasource">(6).引入外部属性文件</span>

1.直接配置数据库信息

（1）配置德鲁伊连接池

（2）引入德鲁伊连接池依赖jar包

```xml
<bean id="DruidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
	<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
	<property name="url" value="jdbc:mysql://localhost:3306/DataBase_name"></property>
    <property name="username" value=DataBase_username></property>
    <property name="password" value=DataBase_password></property>
</bean>
```

可以写在外部文件中。

2.使用外部文件配置数据库连接池

（1）创建外部属性文件，properties格式文件，数据库信息

```properties
prop.driverClass=com.alibaba.druid.pool.DruidDataSource
prop.url=jdbc:mysql://localhost:3306/DataBase_name
prop.username=DataBase_username
prop.password=DataBase_password //为了防止同样的名字造成冲突，使用一个前缀prop.进行区分
```

（2）properties属性文件引入Spring配置文件中

引入context名称空间：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:util="http://www.springframework.org/schema/util"
       
        xmlns:context="http://www.springframework.org/schema/context"
       
       xsi:schemaLocation="http://www.springframework.org/schema/beans 			http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
                           http://www.springframework.org/schema/beans 			http://www.springframework.org/schema/beans/spring-beans.xsd">
```

### <span id="create">3.Bean管理（注解方式）</span>

#### (1)何为注解

注解：代码特殊标记，格式为：@注解名称(属性名称=属性值, ...... )

注解可以作用在：类、方法、属性。

使用注解的目的：简化xml配置。

#### (2)Bean管理

##### 	I.创建对象

(1)@Component

(2)@Service

(3)@Controller

(4)@Repository

上述四个注解，功能一样，都可用于创建bean实例，用于不同层以区分，但是层次混用是可以的。

##### 	II.实现创建对象

1.引入AOP依赖，但是IDEA创建Spring项目自带有Spring的Release版本依赖包，里面就有AOP依赖，因此无需管。

2.开启组件扫描，来扫描注解

（1）要XML文件引入名称空间

```xml
<!--要加的内容-->
xmlns:context="http://www.springframework.org/schema/context"
<!--要部分加的内容-->
<!--在xsi:schemaLocation加上内容，如下-->
xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--添加后-->
xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
```

（2）在XML文件中开启组件扫描

```xml
<context:component-scan base-package="com.XX.XX"></context:component-scan>
```

如果要扫多个包，可以在base-package中用","隔开，也可以指定为共同的上层目录。

(3)开启组件扫描的细节问题

可以配置扫描的类，不扫描的类。

```xml
<!--表示不使用默认filter，而是我们自己配置的-->
<context:component-scan base-package="com.XX.XX" use-default-filters="false">
	<!--到包里找带controller的注解的类-->
    <context:include-filter type="annotation"
                        expression="org.springframework.stereotype.Controller"/>
</context:component-scan>

<context:component-scan base-package="com.XX.XX">
	<!--设置Controller注解不扫描-->
    <context:exclude-filter type="annotation"
                        expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```



3.创建类，在类上添加创建对象的注解

```java
@Component(value = "managerService")  //等价与<bean id="managerService" class="com.***">
//value可以不写，默认值为类名称，首字母小写。
```

4.使用类

```java
context.getBean("定义的value值", 类名.class);
//上述在括号后写.var就变成下面:
类名 定义的value值作为变量名=context.getBean("定义的value值", 类名.class);
```

##### 	III.基于注解方式实现属性注入

（1）@Autowired：表示根据属性类型自动装配

第一步 把service和dao对象创建，在service和dao类添加创建对象注解

```java
//UserImpl上
@Repository
//Service上
@Service
//上述注解可混用，添加位置确定就可以
```

第二步 在service注入dao对象，在service类添加dao类型属性，在属性上面使用注解

```java
//Service中
//不用添加set方法了
@Autowired  //根据类型进行注入
private UserDao userDao;
```

第三步 直接在测试类中根据getBean获取定义好的Bean，运行代码即可

（2）@Qualifier：表示根据属性名称进行注入

这个@Qualifier的使用要与@Autowired一起使用

```java
//在@Autowired下面
@Qualifier(value="名称")//运用于一个dao多个实现类,如果名称并没有对应的，会报错
```

（3）@Resource：可以根据类型注入，也可以根据名称注入

```java
//根据类型实现属性注入
@Resource
//根据名称注入
@Resource(name="名称")
```

Resource是javax（java扩展包中的），因此更推荐使用上面两个。

（4）@Value：注入普通类型属性 

```java
@Value(value="abc")
private String name;//这样name的值就变成了abc，而不用在set方法中进行设置
```

#### (3)完全注解开发

(1)创建配置类。用于替代XML配置文件

创建一个config包，在config包里加一个config类。

在类中用一个注解@Configuration，标示为配置类。在后面再加上一个@ComponentScan，定义扫描的包

```java
@Configuration
@ComponentScan(basePackages={"com.XX"})
public class SpringConfig{
    
}
```

(2)编写测试类

```java
//现在不用配置文件，不用加载配置文件了，不然会报错
@Test
public void test(){
    ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
   //后面是一样的
}
```

***

## 二、AOP



### 1.什么是AOP

译作：面向切面编程，**可对业务逻辑的各个部分进行隔离**，使业务逻辑各部分之间的**耦合度**降低，提高程序可重用性，同时提升开发效率。

不修改源代码，添加新功能，再配置进主干即可，不想要了，从主干去掉即可。

### 2.AOP底层原理

#### (1).AOP底层使用动态

##### 	(I)JDK动态代理

第一种：有接口情况，使用的是JDK动态代理

```java
interface UserDao{
    public void login();
}
class UserDaoImpl implements UserDao{
    public void login(){
        //实现过程
    }
}
//JDK动态代理
//创建UserDao接口实现类代理对象，该对象不是new出来的。通过代理对象进行类中方法增强。
```

##### 	(II)CGLIB动态代理

第二种：没有接口的情况，使用CGLIB动态代理

```java
class User{
    public void add(){
        //类中原有逻辑
    }
}
//原始方式：通过子类增强
public Person extends User{
    public void add(){
        super.add();
        //增强逻辑
    }
}
//CGLIB动态代理
//创建当前类子类的代理对象，不是new出来的。通过子类代理对象进行类中方法增强。
```

#### (2)以JDK动态代理为例

使用JDK动态代理，使用Proxy类里面的方法来创建代理对象(java.lang.reflect.Proxy包)

调用newProxyInstance方法，三个参数如下：

ClassLoader，类加载器

Class<?>[]，增强方法所在的类，这个类实现的接口，可支持多个接口

InvocationHandler，实现这个接口，创建代理对象，写增强的部分。

***

JDK动态代理代码

(1)创建接口，定义方法

```java
public interface UserDao {
    public void call();
    public int add(int a, int b);
}

```

(2)创建接口实现类，实现方法

```java
public class UserDaoImpl implements UserDao {
    @Override
    public void call() {
        System.out.println("call!");
    }
    @Override
    public int add(int a, int b) {
        return a+b;
    }
}
```

(3)使用Proxy类创建接口代理对象

> 1.创建接口代理类，该类实现InvocationHandler
>
> ```java
> class UserDaoProxy implements InvocationHandler{
>     //1.把代理对象所代理的类传递过来
>     //通过有参构造传递,为了能够更通用，使用了Object
>     private Object obj;
>     public UserDaoProxy(Object obj){
>         this.obj=obj;
>     }
>     @Override
>     //对象创建就会调用该方法，在这里面写增强逻辑
>     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
>         //方法之前执行
>         System.out.println("previous!!"+method.getName()+Arrays.toString(args));
>         //被增强的方法执行
>         Object res=method.invoke(obj,args);
>         //方法之后执行
>         System.out.println("after!!"+obj);
>         return res;
>     }
> }
> ```
>
> JDK动态代理效果
>
> ```java
> public class JDKProxy {
>     public static void main(String[] args) {
>         //创建接口实现类的代理对象
>         Class[] interfaces={UserDao.class};
>         UserDaoImpl userDao=new UserDaoImpl();
>         UserDao dao = (UserDao)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
>         System.out.println(dao.add(1,2));
>     }
> }
> ```

### 3.AOP中一些相关术语

#### (1).连接点

类里面可以被增强的方法就叫连接点。可以增强，不一定被增强。

#### (2).切入点

实际被增强的方法就叫连接点。

#### (3).通知（增强）

实际增强的逻辑部分就叫通知（增强）。

通知有多种类型：

##### 	(I).前置通知

该通知在被增强的方法前执行。

##### 	(II).后置通知

该通知在被增强的方法后执行。

##### 	(III).环绕通知

该通知在被增强的方法前、后均执行。如果中间出现了异常，异常后面的环绕通知部分不会执行。

##### 	(IV).异常通知

当被增强的方法出现异常时会执行的通知。

##### 	(V).最终通知

被增强的方法出现异常时，后置通知不会执行，但是最终通知会执行。最终通知会在后置通知前执行，会在异常通知前执行。

#### (4).切面

是一个动作，把通知应用到切入点的过程叫做切面。

### 4.AOP中的一些操作（准备）

Spring中实现AOP中的操作，一般基于AspectJ。

#### (1).什么是AspectJ

AspectJ不是Spring的组成部分，而是一个单独的AOP框架，一般把AspectJ和Spring一起使用，进行AOP操作。

#### (2).基于AspectJ实现AOP操作

##### 	(I).基于xml配置文件方式实现

##### 	(II).基于注解方式实现（一般使用，特别方便）

#### (3).切入点表达式

##### 	(I)切入点表达式的作用

知道进行的增强的方法是哪一个类中的哪一个方法。

##### (II).表达是语法结构

```java
execution([权限修饰符][参数返回类型][类全路径][方法名称]([参数列表]))
//eg1.对com.framework.spring5.Dao.UserDao中的add方法进行增强
execution(* com.framework.spring5.Dao.UserDao.add(..))//*表示对所有修饰符，返回类型可以省略不写
//eg2.对com.framework.spring5.Dao.UserDao中的所有方法进行增强
execution(* com.framework.spring5.Dao.UserDao.*(..))//第二个*表示对所有方法
//eg3.对com.framework.spring5.Dao中所有类，每个类中所有方法都进行增强
execution(* com.framework.spring5.Dao.*.*(..))
```

### 5.基于AspectJ注解方式实现AOP操作

#### (1).创建类，在类里面定义方法

#### (2).创建增强类，在类里面编写增强逻辑

在增强类里面创建方法，让不同的方法能够代表不同的通知类型。

#### (3).进行通知的配置

##### 	(I).Spring配置文件中开启注解扫描

也可以用配置类。

```xml
<!--xml文件中,<beans xxx></beans>中-->
xmlns:aop="http://www.springframework.org/schema/aop"
<!--xsl:schemaLocation中添加-->
http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
```

```xml
<context:component-scan base-package="com.xx.xx"></context:component-scan>
```

##### 	(II).使用注解创建类以及增强类对象

两个类上都添加@Component注解，四个注解可以混用。

##### 	(III).在增强类上添加注解@Aspect

##### 	(IV).在Spring配置文件中开启生成代理对象

```xml
<!--xml中-->
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
<!--开启后，找类上有AspectJ注解就创建代理对象-->
```

如果需要使用完全注解开发，则在config类中添加上以下注解

```java
@EnableAspectJAutoProxy(proxyTargetClass = true)
```



#### (4).配置不同类型的通知

在增强类里面，添加对应的通知类型注解，并用切入点表达式进行配置.

```java
@Before(value="execution(* com.framework.spring5.Dao.UserDao.add(..))")//前置通知
public void before(){
    //前置通知的增强逻辑
}

//方法执行之后执行
@After(value="execution(* com.framework.spring5.Dao.UserDao.add(..))")//最终通知
public void after(){
    //最终通知的增强逻辑
}

//方法返回值之后执行
@AfterReturning(value="execution(* com.framework.spring5.Dao.UserDao.add(..))")//后置通知
public void afterreturning(){
    //后置通知增强逻辑
}

@AfterThrowing(value="execution(* com.framework.spring5.Dao.UserDao.add(..))")//异常通知
public void afterthrowing(){
    //异常通知增强逻辑
}
@Around(value="execution(* com.framework.spring5.Dao.UserDao.add(..))")//环绕通知
public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
    //环绕之前
    //环绕通知的增强逻辑
    proceedingJoinPoint.proceed();
	//环绕之后
}
```

#### (5).相同的切入点的抽取

切入点表达式相同的通知

```java
//在增强类中进行相同切入点的抽取
@Pointcut(value="相同的切入点表达式，比如'execution(* com.framework.spring5.Dao.UserDao.add(..))'")
public void pointdemo(){
    
}
//抽取成功后，在其他的增强方法中，就不使用原来的增强方式，而是：
@Before(value="pointdemo()")//前置通知
public void before(){
    //前置通知的增强逻辑
}
```

#### (6).增强优先级

有多个增强类对同一个方法进行增强，可以设置增强优先级。

(I).在增强类上添加注解@Order(数字类型值)，数字类型值越小，优先级越高。

### 6. 基于AspectJ配置文件方式实现AOP操作

#### (1).创建两个类，增强类和被增强的类，创建方法

先创建被增强类和增强类proxy。

#### (2).在Spring配置文件中创建两个类对象

在配置文件中配置了Bean。

```xml
<bean id="xx" class="com.xx.xx"></bean>
<bean id="xxProxy" class="com.xx.xxProxy"></bean>
```

#### (3).在Spring配置文件中配置切入点

```xml
<!--配置aop增强-->
<aop:config>
    <!--切入点-->
	<aop:pointcut id="pointid" expression="execution(* com.xx.xx.xx.classname.methodname(..))"/>
    <!--切面-->
    <aop:aspect ref="xxProxy">
    	<!--增强作用在具体的方法上-->
        <aop:before method="xxProxy的方法名" pointcut-ref="pointid"/>
    </aop:aspect>
</aop:config>
```

### *7.完全注解开发AOP*

只创建配置类，不用创建xml配置文件

```java
//在config类中加上注解@EnableAspectJAutoProxy
//后方(proxyTargetClass= true)不写也默认为true，但是@EnableAspectJAutoProxy不写则默认为false
@Configuration
@ComponentScan(basePackages = {"com.framework.spring5"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
```

## 三、JdbcTemplate(JDBC模板)

### 1.什么是JdbcTemplate

Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库的操作。

### 2. 准备工作

#### (1).引入相关jar包依赖

 mysql依赖以及德鲁伊连接池两个jar包

#### (2).Spring配置文件中配置数据库连接池

具体操作见[一.2.(6).1.(2)](#datasource)

#### (3).配置JdbcTemplate对象，注入DataSource

```xml
<!--xml文件中-->
<bean id ="xx" class="org.springframework.jdbc.core.JdbcTemplate">
	<!--注入DataSource,ref中的DruidDataSource见上方数据库连接池配置-->
    <property name="datasource" ref="DruidDataSource"
</bean>
```

#### (4).创建Service类，创建Dao类，在Dao中注入JdbcTemplate对象

在Dao的实现类以及Service类上加上@Component等四个注解中任意一个。

在Service中注入Dao，使用@Autowired.

在Dao的实现类中注入JdbcTemplate对象，使用@Autowired。[具体操作](#create)





