package gr.gousiosg.javacg.dyn;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * Created by caipeichao on 16/12/10.
 */
public class LogFile {
    private static final LogFile INSTANCE = new LogFile();

    public static void log(JSONObject json) {
        INSTANCE.internalLog(json);
    }

    private final BufferedWriter writer;

    public LogFile() {
        // 初始化日志文件
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("calltrace.json");
        } catch (FileNotFoundException e) {
        }
        this.writer = new BufferedWriter(new OutputStreamWriter(fout, Charset.forName("utf8")));

        // 退出时关闭日志文件
        this.closeWhenExit();
    }

    public void internalLog(JSONObject json) {
        try {
            String x = json.toJSONString();
            this.writer.write(x);
            this.writer.newLine();
        } catch (IOException e) {
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
        }
    }

    private void closeWhenExit() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                close();
            }
        });
    }

}
