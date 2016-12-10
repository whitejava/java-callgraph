package gr.gousiosg.javacg.dyn;

import com.alibaba.fastjson.JSONObject;

import java.util.Stack;

/**
 * Created by caipeichao on 16/12/10.
 */
public class CallTrace {

    private static ThreadLocal<CallTrace> threadLocal = new ThreadLocal<CallTrace>() {
        @Override
        public CallTrace initialValue() {
            return new CallTrace();
        }
    };

    private Stack<JSONObject> stack = new Stack<JSONObject>();

    public static void push(String className, String method) {
        threadLocal.get().localPush(className, method);
    }

    public static void pop() {
        threadLocal.get().localPop();
    }

    private void localPush(String className, String method) {
        JSONObject json = new JSONObject();
        json.put("@timestamp", System.currentTimeMillis());
        json.put("thread", Thread.currentThread().getId());
        json.put("class", className);
        json.put("method", method);
        json.put("direction", "in");
        json.put("depth", stack.size());
        stack.push(json);
        LogFile.log(json);
    }

    private void localPop() {
        JSONObject json = stack.pop();
        json.put("@timestamp", System.currentTimeMillis());
        json.put("direction", "out");
        LogFile.log(json);
    }
}
