package com.example.lwq.demotest;

import android.text.TextUtils;
import com.google.gson.Gson;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * @author lwq
 * @date 2018-11-07 11:53
 * introduction:
 */
public class JSEngine {
    private Class clazz;
    private String allFunctions ="";//js方法语句

    public JSEngine(){
        this.clazz = JSEngine.class;
        allFunctions = String.format(getAllFunctions(), clazz.getName());//生成js语法
    }

    class TestObject{
        private String name;
        private String address;

        TestObject(String name ,String address){
            this.name = name;
            this.address = address;
        }
    }

    /**
     * 本地方法 - 返回本地类对象
     * @param keyStr
     * @return
     */
    @JSAnnotation(returnObject = true)
    public String getObjectValue(Object keyStr){
        System.out.println("JSEngine output - getObjectValue : " + keyStr.toString());
        return new Gson().toJson(new TestObject("小明","广州"));
    }

    /**
     * 本地java方法
     * @param keyStr
     * @param o
     */
    @JSAnnotation
    public void setValue(Object keyStr, Object o) {
        System.out.println("JSEngine output - setValue : " + keyStr.toString() + " ------> " + o.toString());
    }

    /**
     * 本地java
     * @param keyStr
     * @return
     */
    @JSAnnotation
    public String getValue(String keyStr) {
        System.out.println("JSEngine output - getValue : " + keyStr.toString() );
        return "获取到值了";
    }


    /**
     * 执行JS
     * @param js  js执行代码 eg: "var v1 = getValue('Ta');setValue(‘key’，v1);"
     */
    public void runScript(String js){
        String runJSStr = allFunctions + "\n" + js;//运行js = allFunctions + js
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(this, scope));//配置属性 javaContext:当前类JSEngine的上下文
            ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(clazz.getClassLoader(), scope));//配置属性 javaLoader:当前类的JSEngine的类加载器

            rhino.evaluateString(scope, runJSStr, clazz.getSimpleName(), 1, null);
        } finally {
            org.mozilla.javascript.Context.exit();
        }
    }

    /**
     * 通过注解自动生成js方法语句
     */
    private String getAllFunctions(){
        String funcStr =  " var ScriptAPI = java.lang.Class.forName(\"%s\", true, javaLoader);\n" ;
        Class cls = this.getClass();
        for (Method method: cls.getDeclaredMethods()){
            JSAnnotation an = method.getAnnotation(JSAnnotation.class);
            if (an == null ) {continue;}
            String functionName = method.getName();

            String paramsTypeString  ="";//获取function的参数类型
            String paramsNameString = "";//获取function的参数名称
            String paramsNameInvokeString = "";
            Class [] parmTypeArray = method.getParameterTypes();
            if (parmTypeArray != null && parmTypeArray.length > 0){
                String[] parmStrArray = new String[parmTypeArray.length];
                String[] parmNameArray = new String[parmTypeArray.length];
                for (int i=0;i < parmTypeArray.length; i++){
                    parmStrArray[i] = parmTypeArray[i].getName();
                    parmNameArray[i] = "param" + i ;
                }
                paramsTypeString = String.format(",[%s]", TextUtils.join(",",parmStrArray));
                paramsNameString = TextUtils.join(",",parmNameArray);
                paramsNameInvokeString = "," + paramsNameString;
            }

            Class returnType = method.getReturnType();
            String returnStr = returnType.getSimpleName().equals("void") ? "" : "return";//是否有返回值

            String methodStr = String.format(" var method_%s = ScriptAPI.getMethod(\"%s\"%s);\n",functionName,functionName,paramsTypeString);
            String functionStr = "";
            if (an.returnObject()){//返回对象
                functionStr = String.format(
                    " function %s(%s){\n" +
                        "    var retStr = method_%s.invoke(javaContext%s);\n" +
                        "    var ret = {} ;\n" +
                        "    eval('ret='+retStr);\n" +
                        "    return ret;\n" +
                        " }\n"  ,functionName,paramsNameString,functionName,paramsNameInvokeString );
            }else {//非返回对象
                functionStr = String.format(
                    " function %s(%s){\n" +
                        "    %s method_%s.invoke(javaContext%s);\n" +
                        " }\n",functionName,paramsNameString,returnStr,functionName,paramsNameInvokeString );
            }
            funcStr = funcStr + methodStr + functionStr;
        }
        return funcStr;
    }

    /**
     * 注解
     */
    @Target(value = ElementType.METHOD)
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface JSAnnotation {
        boolean returnObject() default false;//是否返回对象，默认为false 不返回
    }
}
