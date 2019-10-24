package com.its.test.rules;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.context.FelContext;

/**
 * description: Java表达式引擎fel/Aviator/JEXL/groovy/expression4j/java脚本引擎的性能对比
 * company: tzz
 * @author: tzz
 * date: 2019/08/13 20:31
 */
public class ExpressionTest {

    private int count = 100000;
    private String expression = "a*b*c";
    private Map<String, Object> env = new HashMap<String, Object>(16);
    
    @Before
    public void initMap() {
        env.put("a", 3600);
        env.put("b", 14);
        env.put("c", 5);
    }

    /** javax的编译执行，效率比解释执行略高？为什么才略高？？ */
    @Test
    public void testCompiledJsScript() throws Throwable {
        System.out.println("testCompiledJsScript");
        long start = System.currentTimeMillis();
        ScriptEngine se = new ScriptEngineManager().getEngineByName("js");
        Compilable ce = (Compilable)se;
        CompiledScript cs = ce.compile(expression);
        Bindings bindings = se.createBindings();
        for (String key : env.keySet()) {
            bindings.put(key, env.get(key));
        }
        Object eval = null;
        for (int i = 0; i < count; i++) {
            eval = cs.eval(bindings);
        }
        System.out.println(eval);
        System.out.println(System.currentTimeMillis() - start);
    }

    /** javax script解释执行 */
    @Test
    public void testJsScript() throws Throwable {
        System.out.println("testJsScript");
        long start = System.currentTimeMillis();
        ScriptEngine se = new ScriptEngineManager().getEngineByName("js");
        Bindings bindings = se.createBindings();
        for (String key : env.keySet()) {
            bindings.put(key, env.get(key));
        }
        Object eval = null;
        for (int i = 0; i < count; i++) {
            eval = se.eval(expression, bindings);
        }
        System.out.println(eval);
        System.out.println(System.currentTimeMillis() - start);
    }

    /** groovy的编译执行 */
    @Test
    public void testGroovy() {
//        ScriptEngineManager factory = new ScriptEngineManager();
//        // 这里的ScriptEngine和GroovyScriptEngine是自己编写的类，不是原生的
//        ScriptEngine engine = factory.getEngineByName("groovy");  
//        Map<String, Object> paramMap = new HashMap<String, Object>(16);
//        paramMap.put("param", 5);
//        // ScriptEngine首次执行会缓存编译后的脚本，这里故意先执行一次便于缓存
//        engine.eval("3600*34*param", paramMap);
//
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            engine.eval("3600*34*param", paramMap);
//        }
//        System.out.println(System.currentTimeMillis() - start);
    }

    /** Expression4J的表达式引擎，这里是通过函数的方式，有点特别 */
    @Test
    public void testExpression4j() throws Throwable {
//        Expression expression = ExpressionFactory.createExpression("f(a,b,c)=a*b*c");
//        System.out.println("Expression name: " + expression.getName());
//
//        System.out.println("Expression parameters: " + expression.getParameters());
//
//        MathematicalElement elementa = NumberFactory.createReal(3600);
//        MathematicalElement elementb = NumberFactory.createReal(34);
//        MathematicalElement elementc = NumberFactory.createReal(5);
//        Parameters parameters = ExpressionFactory.createParameters();
//        parameters.addParameter("a", elementa);
//        parameters.addParameter("b", elementb);
//        parameters.addParameter("c", elementc);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            expression.evaluate(parameters);
//        }
//        System.out.println(System.currentTimeMillis() - start);
    }

    /** fel的表达式引擎（静态参数，同上面） */
    @Test
    public void felTest() {
        System.out.println("felTest");
        long start = System.currentTimeMillis();
        FelEngine e = FelEngine.instance;
        FelContext ctx = e.getContext();
        for (String key : env.keySet()) {
            ctx.set(key, env.get(key));
        }
        com.greenpineyu.fel.Expression exp = e.compile(expression, ctx);
        Object eval = null;
        for (int i = 0; i < count; i++) {
            eval = exp.eval(ctx);
        }
        System.out.println(eval);
        System.out.println(System.currentTimeMillis() - start);
    }

    /** fel表达式引擎（动态参数，这里动态参数的产生和变量改变都会消耗时间，因此这个测试时间不准确，只是验证对于动态参数的支持） */
    @Test
    public void felDynaTest() {
        System.out.println("felDynaTest");
        long start = System.currentTimeMillis();
        FelEngine e = FelEngine.instance;
        final FelContext ctx = e.getContext();
        for (String key : env.keySet()) {
            ctx.set(key, env.get(key));
        }
        com.greenpineyu.fel.Expression exp = e.compile(expression, ctx);
        Object eval = null;
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            ctx.set("a", r.nextInt(10000));
            ctx.set("b", r.nextInt(100));
            ctx.set("c", r.nextInt(100));
            eval = exp.eval(ctx);
        }
        System.out.println(eval);
        System.out.println(System.currentTimeMillis() - start);
    }

    /** Java Expression Language (JEXL) Java表达式语言 */
    @Test
    public void jexlTest() {
        System.out.println("jexlTest");
        long start = System.currentTimeMillis();
        Object eval = null;
        JexlEngine jexl = new JexlEngine();
        Expression e = jexl.createExpression(expression);
        JexlContext jc = new MapContext();
        for (int i = 0; i < count; i++) {
            for (String key : env.keySet()) {
                jc.set(key, env.get(key));
            }
            eval = e.evaluate(jc);
        }
        System.out.println(eval);
        System.out.println(System.currentTimeMillis() - start);
    }
    
    /** Aviator是一个高性能、轻量级的 java 语言实现的表达式求值引擎 */
    @Test
    public void aviatorTest() {
        System.out.println("aviatorTest");
        long start = System.currentTimeMillis();
        Object eval = null;
        for (int i = 0; i < count; i++) {
            eval = AviatorEvaluator.execute(expression, env);
        }
        System.out.println(eval);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) throws Throwable {
        ExpressionTest et = new ExpressionTest();
        // 执行100W次的测试
        et.count = 1000000;
        et.env.put("a", 3600);
        et.env.put("b", 14);
        et.env.put("c", 5);
        et.testCompiledJsScript();
        et.testJsScript();
        // et.testExpression4j();
        // et.testGroovy();
        et.felTest();
        et.jexlTest();
        et.aviatorTest();
    }

}

 