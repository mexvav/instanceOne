package core.utils;

import com.google.common.collect.Maps;

import java.util.LinkedHashSet;
import java.util.Map;

public class ClassUtils {

    /**
     * Get class hierarchy with rate
     * rate 0 - source class
     * rate 1 - interfaces source class
     * @param nextClass - class for hierarchy
     * @return Map class/rate
     */
    public static Map<Class,Integer> getHierarchyClass(Class nextClass){
        Map<Class,Integer> hierarchyClass = Maps.newHashMap();
        int rate = 0;
        while(!nextClass.equals(Object.class)){
            hierarchyClass.put(nextClass, rate++);
            Class<?>[] interfaces = nextClass.getInterfaces();
            if(interfaces.length > 0){
                rate++;
                for(Class interfaceTemp : interfaces){
                    hierarchyClass.put(interfaceTemp, rate);
                }
            }
            nextClass = nextClass.getSuperclass();
        }
        return hierarchyClass;
    }

    /*
    public <C>LinkedHashSet<C> sortClassesByHierarchy(Class){

    }*/
}
