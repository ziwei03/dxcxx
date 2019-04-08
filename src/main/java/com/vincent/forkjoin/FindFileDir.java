package com.vincent.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FindFileDir extends RecursiveAction {

    private File path ;

    public FindFileDir(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        List<FindFileDir> subList = new ArrayList<>();
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    if(file.getAbsolutePath().endsWith("avi")) {
                        System.out.println("找到文件：" + file.getAbsolutePath());
                    }
                } else {
                    subList.add(new FindFileDir(file));
                }
            }
        }
        if (subList.size() > 0) {
            for(FindFileDir findFileDir : invokeAll(subList)) {
                findFileDir.join();
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("G:\\");
        FindFileDir findFileDir = new FindFileDir(file);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(findFileDir);
        findFileDir.join();

    }
}
