2018-11-19 ：目前还在开发阶段
# 简介
HyggeCache 是一个基于 Java 的缓存系统封装，提供统一的 API 和注解来简化缓存的使用。同类事物中已有较为优秀的实现，如
- [Spring Cache](https://github.com/spring-projects/spring-framework/tree/master/spring-context/src/main/java/org/springframework/cache)
- [Jetcache](https://github.com/alibaba/jetcache)
- [AutoLoadCache](https://github.com/qiujiayu/AutoLoadCache)
- ……

上述项目并未对如 ``ArrayList<HashMap<String, User>>`` 的复杂 json 对象提供良好的缓存实现(2018年11月19日)，本项目是在上述优秀实现的基础上进行了一定借鉴,并完善了复杂 Json 对象的缓存功能

- 易于扩展自定义序列化代理、自定义缓存落地执行对象
- 缓存所使用的 key 策略可配置，最小隔离粒度为不同函数方法可应用不同配置
- 缓存所使用的序列化代理可自由指定，最小隔离粒度为不同函数方法可应用不同序列化代理(默认实现了 Jacson、FastJson)
- 缓存的序列化类型简单时无需额外配置，复杂序列化类型如 ``ArrayList<HashMap<String, User>>`` 需要按一定规则声明复杂序列化类型，并为目标方法指定序列化类型

# 要求
- jdk 1.8
- Spring Boot 2.0.0.RELEASE+
- Spring Framework 5.0.0.RELEASE+

# 快速开始

## 全局配置
本项目存在 3 种配置
- 标注在方法上的配置
- 标注在类上的配置
- 全局配置

这三种配置存在优先级，当多个配置存在相同配置属性时，优先级高的会覆盖优先级低的，他们间的优先级为： 
标注在方法上的配置>标注在类上的配置>全局配置


```
# 全局缓存 Key 默认的前缀
hyggecache.default.prefix=test
# 全局缓存最长有效时间默认值(毫秒)
hyggecache.default.expireInMillis=3600000
# 是否在缓存中标识查询结果不存在默认值(true:进行 null 查询结果标识)
hyggecache.default.cacheNullValue=true
# 缓存 null 标识的最长有效时间默认值(毫秒)
hyggecache.default.nullValueExpireInMillis=5000
```

## 特殊序列化类型的声明
BeanName 可任意，当 BeanName 为 ``JACSON_DEFAULT_NAME_CUSTOM`` 或者 ``FASTJSON_DEFAULT_NAME_CUSTOM`` 为替换默认的 ``TypeInfoKeeper`` 对象，否则会存在多个 ``TypeInfoKeeper`` 实例，序列化代理需要额外指定自身所依赖的具体 ``TypeInfoKeeper`` 对象

```
    @Bean(TypeInfoKeeper.JACSON_DEFAULT_NAME_CUSTOM)
    public TypeInfoKeeper typeInfoKeeper() {
        TypeInfoKeeper<TypeReference> typeInfoKeeper = new TypeInfoKeeper(SerializerPolicyEnum.JACKSON);
        typeInfoKeeper.saveTypeReference("customType", new TypeInfo<ArrayList<HashMap<String, Cat>>>() {
        });
        ……
        return typeInfoKeeper;
    }
```


## 为目标方法执行缓存逻辑

``key`` 内容为 [SpEL](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/expressions.html)
```
@Service("CatServiceImpl")
@CachedConfig(prefix = "cat:")
public class CatServiceImpl implements CatService {
    @Override
    @Cacheable(serializeTypeInfoKey = "customType", key = "#size+':customType'")
    public ArrayList<HashMap<String, Cat>> customType(自定义的入参) {
         // 查询逻辑
        return 查询结果;
    }

    @Override
    @Cacheable(key = "#catId")
    public Cat getCatById(String catId) {
         // 查询逻辑
        return 查询结果;
    }

    @Override
    @CacheInvalidate(key = "#catId",cacheNullValue = "false")
    public Boolean removeCat(String catId) {
         // 删除逻辑
        return 删除结果;
    }

    @Override
    @CacheUpdate(key = "#catId")
    public Cat updateCatById(Cat cat) {
         // 修改逻辑
        return 修改结果;
    }
}
```

同样，你也可以从接口上进行注册

```
@CachedConfig(prefix = "u:")
public interface UserService {
    @Cacheable(key = "#id")
    User getUserById(int id);

    @CacheInvalidate(key = "#user.id")
    void saveUser(User user);

    @CacheUpdate(key = "#user.id")
    void updateUser(User user);
}
```
