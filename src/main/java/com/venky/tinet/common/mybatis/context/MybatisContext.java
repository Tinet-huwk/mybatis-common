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


import com.venky.tinet.common.mybatis.service.BaseService;
import com.venky.tinet.common.mybatis.service.IBaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * mybatis的spring上下文
 * 主要提供逻辑删除的一些属性和AbstractDomain的配置
 *
 * @author 赵伟伟(wolfking)
 * Created on 2018/5/21 13:02
 * Email is zhao.weiwei@jyall.com
 * Copyright is 金色家园网络科技有限公司
 */
@Component
public class MybatisContext implements ApplicationContextAware, EnvironmentAware {

    /**
     * slf4j日志
     */
    private static Logger logger = LoggerFactory.getLogger(MybatisContext.class);

    /**
     * 获取用户ID的service
     */
    private static IBaseUserService baseUserService;

    /**
     * 逻辑删除的标志位
     */
    private static boolean LOGIC_DELETE = false;
    /**
     * spring的上下文
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MybatisContext.applicationContext = applicationContext;
        try {
            baseUserService = applicationContext.getBean(IBaseUserService.class);
        } catch (Exception e) {
            logger.warn("get userIdService error", e);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        /*设置mybatis的逻辑删除的标志位*/
        Binder relaxedPropertyResolver = Binder.get(environment);
        try {
            LOGIC_DELETE = relaxedPropertyResolver.bind("mybatis.logic-delete", Boolean.class).get();
        } catch (Exception e) {
            LOGIC_DELETE = false;
            logger.warn("get logic-delete flag error ", e);
        }
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static String getUserId() {
        return baseUserService != null ? baseUserService.getUserId() : "";
    }

    /**
     * 是否是逻辑删除
     *
     * @return
     */
    public static boolean isLogicDelete() {
        return LOGIC_DELETE;
    }

    /**
     * 获取实例对应的Service
     *
     * @param entityClass
     * @return
     */
    public static BaseService getBaseServiceByEntityClass(Class<?> entityClass) {
        Map<String, BaseService> serviceMap = applicationContext.getBeansOfType(BaseService.class);
        for (BaseService baseService : serviceMap.values()) {
            if (entityClass.equals(baseService.getEntityClass())) {
                return baseService;
            }
        }
        return null;
    }
}
