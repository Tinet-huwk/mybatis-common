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


import com.venky.tinet.common.mybatis.annotation.MyColumn;
import com.venky.tinet.common.mybatis.annotation.MyId;
import com.venky.tinet.common.mybatis.domain.AbstractToString;
import com.venky.tinet.common.mybatis.exception.BaseServiceNotFoundException;
import com.venky.tinet.common.mybatis.service.BaseService;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Set;

/**
 * 抽象的一对一一对多的上下文关系
 * set方法使用protected的访问类别，只能在子类中使用
 *
 * @author 赵伟伟(wolfking)
 * Created on 2018/5/24 16:18
 * Email is zhao.weiwei@jyall.com
 * Copyright is 金色家园网络科技有限公司
 */
public abstract class AbstractContext extends AbstractToString {

    /**
     * 自己的clazz
     */
    private Class<?> ownClazz;
    /**
     * 多的 实体类的class
     */
    private Class<?> otherClass;
    /**
     * 自己的属性
     */
    private Field ownField;
    /**
     * 另一个实体的属性
     */
    private Field otherField;
    /**
     * 数组或者Collection的字段，或者一对一实体的类型
     */
    private Field relationField;
    /**
     * 关联的另外一个实体操作的Service
     */
    private BaseService baseService;

    public Class<?> getOtherClass() {
        return otherClass;
    }

    protected void setOtherClass(Class<?> otherClass) {
        this.otherClass = otherClass;
    }

    public Class<?> getOwnClazz() {
        return ownClazz;
    }

    protected void setOwnClazz(Class<?> ownClazz) {
        this.ownClazz = ownClazz;
    }

    public Field getOwnField() {
        return ownField;
    }

    protected void setOwnField(Field ownField) {
        ownField.setAccessible(true);
        this.ownField = ownField;
    }

    public Field getOtherField() {
        return otherField;
    }

    protected void setOtherField(Field otherField) {
        otherField.setAccessible(true);
        this.otherField = otherField;
    }

    public Field getRelationField() {
        return relationField;
    }

    protected void setRelationField(Field relationField) {
        relationField.setAccessible(true);
        this.relationField = relationField;
    }

    public BaseService getBaseService() {
        return baseService;
    }

    protected void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    /**
     * 获取自己的关联字段
     *
     * @param clazz
     * @param refrenceOwnFieldName
     * @return
     */
    @SuppressWarnings("all")
    protected static Field getOwnField(Class<?> clazz, String refrenceOwnFieldName) {
        Set<Field> refrenceOwnFieldSet = ReflectionUtils.getAllFields(clazz,
                ReflectionUtils.withName(refrenceOwnFieldName), ReflectionUtils.withAnnotation(MyColumn.class));
        refrenceOwnFieldSet = refrenceOwnFieldSet.isEmpty() ? ReflectionUtils.getAllFields(clazz,
                ReflectionUtils.withName(refrenceOwnFieldName), ReflectionUtils.withAnnotation(MyId.class)) : refrenceOwnFieldSet;
        return refrenceOwnFieldSet.isEmpty() ? null : refrenceOwnFieldSet.iterator().next();
    }

    /**
     * 获取关联的类别
     *
     * @param field 关联的字段
     * @return
     */
    protected static Class<?> getOtherClass(Field field) {
        return Collection.class.isAssignableFrom(field.getType()) ?
                Class.class.cast(ParameterizedType.class.cast(field.getGenericType()).getActualTypeArguments()[0]) :
                field.getType().isArray() ? field.getType().getComponentType() : field.getType();
    }

    /**
     * 组装一对一一对多的关系
     *
     * @param obj
     */
    protected abstract void assembly(Object obj);

    /**
     * 分析获取关联的BaseService
     */
    public void analysisBaseService() {
        baseService = MybatisContext.getBaseServiceByEntityClass(otherClass);
        if (baseService == null) {
            throw new BaseServiceNotFoundException(otherClass.getSimpleName() + "'s baseService not found");
        }
    }
}
