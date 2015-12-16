package cn.edu.cuit.monitorpc;

import cn.edu.cuit.monitorpc.utils.QuartzManager;

// some info about wireless-tools, a tool which can run on Linux
// http://lianxingfeng.com/%E6%9F%A5%E8%AF%A2%E9%99%84%E8%BF%91%E6%97%A0%E7%BA%BF%E8%B7%AF%E7%94%B1%E5%99%A8%E4%BF%A1%E9%81%93-%E5%B7%A5%E4%BD%9C%E9%A2%91%E7%8E%87/
// http://www.hpl.hp.com/personal/Jean_Tourrilhes/Linux/Tools.html#docu
// http://www.cnblogs.com/gunl/archive/2010/08/19/1803594.html
public class Main {

    public static void main(String[] args) {
        String job_name = "myjob";
        // job class name
        String job = "cn.edu.cuit.monitorpc.CrontabJob";
        // output interval
//        int outputInterval = Integer.parseInt(args[0]);
        int outputInterval =1;
                String jobPlan = "0 0/" + outputInterval + " * * * ?";
        // String jobPlan = "0/40 * * * * ?";
        System.out.println("Monitor is running! Output every "
                + outputInterval + " minutes...");
        System.out.println("Job's plan: " + jobPlan);
        // start job
        QuartzManager.addJob(job_name, job, jobPlan);

    }

}
