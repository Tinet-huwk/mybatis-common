/********************************************************
 ***                     _ooOoo_                       ***
 ***                    o8888888o                      ***
 ***                    88" . "88                      ***
 ***                    (| -_- |)                      ***
 ***                    O\  =  /O                      ***
 ***                 ____/`---'\____                   ***
 ***               .'  \\|     |//  `.                 ***
 ***              /  \\|||  :  |||//  \                ***
 ***             /  _||||| -:- |||||-  \               ***
 ***             |   | \\\  -  /// |   |               ***
 ***             | \_|  ''\---/''  |   |               ***
 ***             \  .-\__  `-`  ___/-. /               ***
 ***           ___`. .'  /--.--\  `. . __              ***
 ***        ."" '<  `.___\_<|>_/___.'  >'"".           ***
 ***       | | :  `- \`.;`\ _ /`;.`/ - ` : | |         ***
 ***       \  \ `-.   \_ __\ /__ _/   .-` /  /         ***
 ***  ======`-.____`-.___\_____/___.-`____.-'======    ***
 ***                     `=---='                       ***
 ***   .............................................   ***
 ***         佛祖镇楼                  BUG辟易         ***
 ***   佛曰:                                           ***
 ***           写字楼里写字间，写字间里程序员；        ***
 ***           程序人员写程序，又拿程序换酒钱。        ***
 ***           酒醒只在网上坐，酒醉还来网下眠；        ***
 ***           酒醉酒醒日复日，网上网下年复年。        ***
 ***           但愿老死电脑间，不愿鞠躬老板前；        ***
 ***           奔驰宝马贵者趣，公交自行程序员。        ***
 ***           别人笑我忒疯癫，我笑自己命太贱；        ***
 ***           不见满街漂亮妹，哪个归得程序员？        ***
 *********************************************************
 ***               江城子 . 程序员之歌                 ***
 ***           十年生死两茫茫，写程序，到天亮。        ***
 ***               千行代码，Bug何处藏。               ***
 ***           纵使上线又怎样，朝令改，夕断肠。        ***
 ***           领导每天新想法，天天改，日日忙。        ***
 ***               相顾无言，惟有泪千行。              ***
 ***           每晚灯火阑珊处，夜难寐，加班狂。        ***
 *********************************************************
 ***      .--,       .--,                              ***
 ***      ( (  \.---./  ) )                            ***
 ***       '.__/o   o\__.'                             ***
 ***          {=  ^  =}              高山仰止,         ***
 ***           >  -  <               景行行止.         ***
 ***          /       \              虽不能至,         ***
 ***         //       \\             心向往之。        ***
 ***        //|   .   |\\                              ***
 ***        "'\       /'"_.-~^`'-.                     ***
 ***           \  _  /--'         `                    ***
 ***         ___)( )(___                               ***
 ***        (((__) (__)))                              ***
 ********************************************************/
package com.venky.tinet.common.mybatis.context;

import com.google.common.collect.Lists;
import com.venky.tinet.common.mybatis.annotation.MyColumn;
import com.venky.tinet.common.mybatis.annotation.MyMany;
import com.venky.tinet.common.mybatis.exception.One2ManyException;
import com.venky.tinet.common.mybatis.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 一对多关系的上下文
 *
 * @author 赵伟伟(wolfking)
 * Created on 2018/5/24 13:41
 * Email is zhao.weiwei@jyall.com
 * Copyright is 金色家园网络科技有限公司
 */
public class ManyContext extends AbstractContext {

    /**
     * slf4j日志
     */
    private static Logger logger = LoggerFactory.getLogger(ManyContext.class);

    /**
     * 私有构造函数
     */
    private ManyContext() {

    }

    @SuppressWarnings("all")
    public static List<ManyContext> analysisMetedata(Class<?> clazz) throws Exception {
        List<ManyContext> contextList = Lists.newArrayList();
        /*循环构建所有的一对多的上下文*/
        for (Field field : ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotation(MyMany.class))) {
            /*一对多的关系必须是数组或者Collection的子类*/
            if (Collection.class.isAssignableFrom(field.getType()) || field.getType().isArray()) {
                MyMany many = field.getAnnotation(MyMany.class);
                /*获取一对多的另一个实体的类型。如果是list从泛型获取，数组从getComponentType获取*/
                Class<?> manyClass = getOtherClass(field);
                String refrenceOwnFieldName = many.refrenceOwnField();
                String refrenceOtherFieldName = many.refrenceOtherField();
                /*获取一对多自己的关联的字段*/
                Field refrenceOwnField = getOwnField(clazz, refrenceOwnFieldName);
                if (refrenceOwnField != null) {
                    /*获取一对多另一个实体的关联字段*/
                    Set<Field> refrenceOtherFieldSet = ReflectionUtils.getAllFields(manyClass,
                            ReflectionUtils.withName(refrenceOtherFieldName), ReflectionUtils.withAnnotation(MyColumn.class));
                    Field refrenceOtherField = refrenceOtherFieldSet.isEmpty() ? null : refrenceOtherFieldSet.iterator().next();
                    if (refrenceOtherField != null) {
                        /*组装一对多的上下文*/
                        ManyContext manyContext = new ManyContext();
                        manyContext.setOwnClazz(clazz);
                        manyContext.setOwnField(refrenceOwnField);
                        manyContext.setOtherField(refrenceOtherField);
                        manyContext.setOtherClass(manyClass);
                        manyContext.setRelationField(field);
                        contextList.add(manyContext);
                    } else {
                        /*其他实体的的字段找不到*/
                        logger.error("[{}] @MyMany refrence other field [{}] error,it with not @MyColumn or @MyId ",
                                clazz.getName(), refrenceOtherFieldName);
                        throw new One2ManyException(String.format(
                                "[%s] @MyMany refrence other field [%s] error,it with not @MyColumn or @MyId", clazz.getName(), refrenceOtherFieldName));
                    }
                } else {
                    /*映射的自己的字段找不到*/
                    logger.error("[{}] @MyMany refrence own field [{}] error,it with not @MyColumn or @MyId ", clazz.getName(), refrenceOwnFieldName);
                    throw new One2ManyException(String.format(
                            "[%s] @MyMany refrence own field [%s] error,it with not @MyColumn or @MyId", clazz.getName(), refrenceOwnFieldName));
                }
            } else {
                /*一对多的关系的实体的字段类别不是数组或者集合*/
                logger.error("[{}] @MyMany field [{}] type error [{}],it will be collection or Array", clazz.getName(), field.getName(), field.getType());
                throw new One2ManyException(String.format(
                        "[%s] @MyMany field [%s] type error [%s],it will be collection or Array", clazz.getName(), field.getName(), field.getType()));
            }
        }
        return contextList;
    }


    @Override
    @SuppressWarnings("all")
    public void assembly(Object t) {
        try {
            Field refrenceOwnField = getOwnField();
            Field refrenceOtherField = getOtherField();
            Object refrenceOwnFieldValue = refrenceOwnField.get(t);
            Class<?> manyClass = getOtherClass();
            Field field = getRelationField();
            if (refrenceOwnFieldValue != null && StringUtils.isNotEmpty(String.valueOf(refrenceOwnFieldValue))) {
                Object refrence = getOtherClass().newInstance();
                refrenceOtherField.set(refrence, refrenceOwnFieldValue);
                /*执行查询赋值*/
                BaseService baseService = MybatisContext.getBaseServiceByEntityClass(manyClass);
                if (baseService != null) {
                    List list = baseService.seleteAccuracy(refrence);
                    if (!CollectionUtils.isEmpty(list)) {
                        if (field.getType().isArray()) {
                            Object array = Array.newInstance(manyClass, list.size());
                            for (int i = 0; i < list.size(); i++) {
                                Array.set(array, i, list.get(i));
                            }
                            field.set(t, array);
                        } else if (field.getType().equals(Collection.class) || field.getType().equals(List.class)) {
                            field.set(t, list);
                        } else if (field.getType().equals(Set.class)) {
                            field.set(t, new HashSet<>(list));
                        } else if (!field.getType().isInterface()) {
                            Collection collection = Collection.class.cast(field.getType().newInstance());
                            collection.addAll(list);
                            field.set(t, collection);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
