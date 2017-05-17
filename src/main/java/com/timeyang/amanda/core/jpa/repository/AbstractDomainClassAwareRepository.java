package com.timeyang.amanda.core.jpa.repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 持有领域类的类型信息
 *
 * @author chaokunyang
 * @create 2017-04-17
 */
public abstract class AbstractDomainClassAwareRepository<T> {

    protected final Class<T> domainClass;

    @SuppressWarnings("unchecked")
    protected AbstractDomainClassAwareRepository() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        while (!(genericSuperclass instanceof ParameterizedType)) {
            if(!(genericSuperclass instanceof Class))
                //  If this Class represents either the Object class, an interface, a primitive type, or void, then null is returned.
                throw new IllegalStateException("Unable to determine type arguments, " +
                        "because generic superclass is neither parameterized type nor class.");
            if(genericSuperclass == AbstractDomainClassAwareRepository.class)
                // that is to say, don't specify type when extends AbstractDomainClassAwareRepository
                throw new IllegalStateException("Unable to determine type arguments, " +
                        "because no paramiterized generic superclass found.");

            genericSuperclass = ((Class) genericSuperclass).getGenericSuperclass();
        }

        ParameterizedType type = (ParameterizedType) genericSuperclass;
        Type[] arguments = type.getActualTypeArguments();
        this.domainClass = (Class<T>) arguments[0];
    }

}
