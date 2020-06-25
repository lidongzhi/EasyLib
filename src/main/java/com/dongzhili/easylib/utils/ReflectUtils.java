package com.dongzhili.easylib.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.util.Log;

import com.dongzhili.easylib.base.BaseToolLibConfig;

public final class ReflectUtils {

    private static final boolean DEBUG = BaseToolLibConfig.DEBUG;
    private static final String LOG_TAG = "ReflectUtils";

    private ReflectUtils() {
    }

    public static Method getMethod(Class<?> aClass, String methodname, Class<?>... aParamTypes) {
        try {
            Method method = aClass.getDeclaredMethod(methodname, aParamTypes);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "getMethod Exception", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "getMethod Error", e);
            }
        }
        return null;
    }

    public static Object invoke(Method aMethod, Object aObject, Object aDefaultValue, Object... aArgs) {
        try {
            return aMethod.invoke(aObject, aArgs);
        } catch (Exception e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "invoke Exception", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "invoke Error", e);
            }
        }
        return aDefaultValue;
    }

    public static Field getField(Class<?> aClass, String aFileldName) {
        try {
            Field field = aClass.getDeclaredField(aFileldName);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "getField Exception", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "getField Error", e);
            }
        }
        return null;
    }

    public static Object getFieldValue(Field aField, Object aObject, Object aDefaultValue) {
        try {
            return aField.get(aObject);
        } catch (Exception e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "getFieldValue Exception", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "getFieldValue Error", e);
            }
        }
        return aDefaultValue;
    }

    public static Object getFieldValue(Object aObject, String aFieldName, Object aDefaultValue) {
        Field field = getField(aObject.getClass(), aFieldName);
        if (field != null) {
            return getFieldValue(field, aObject, aDefaultValue);
        }
        return aDefaultValue;
    }

    public static boolean setFieldValue(Field aField, Object aObject, Object aValue) {
        try {
            aField.set(aObject, aValue);
            return true;
        } catch (Exception e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "setFieldValue Exception", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "setFieldValue Error", e);
            }
        }
        return false;
    }

    public static boolean setFieldValue(Object aObject, String aFieldName, Object aValue) {
        Field field = getField(aObject.getClass(), aFieldName);
        if (field != null) {
            return setFieldValue(field, aObject, aValue);
        }
        return false;
    }

    public static Object newInstance(Class<?> aClass, Class<?>[] aParamTypes, Object[] aArgs) {
        try {
            Constructor<?> constructor = aClass.getDeclaredConstructor(aParamTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(aArgs);
        } catch (Exception e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "newInstance Exception", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.w(LOG_TAG, "newInstance Error", e);
            }
        }
        return null;
    }

}
